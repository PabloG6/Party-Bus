package momocorp.partybus.Fragments.Eventsfragments;

import android.Manifest;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;

import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;

import momocorp.partybus.Fragments.Eventsfragments.FragmentInterfaces.FragmentInterface;
import momocorp.partybus.misc.ID;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


import momocorp.partybus.CustomObjects.EventInformation;
import momocorp.partybus.Fragments.Eventsfragments.MapMethods.CustomGoogleApiClient;
import momocorp.partybus.Fragments.Eventsfragments.MapMethods.GetLocations;
import momocorp.partybus.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentInterface} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventLocationFragment extends Fragment implements OnMapReadyCallback,
        GetLocations.GetLocationsListener, RoutingListener {

    MapView mapView;
    GoogleMap googleMap;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    HashMap<String, PolylineOptions> routes = new HashMap<>();
    CustomGoogleApiClient customGoogleApiClient;
    Polyline visibleLine;
    ImageButton next_event_button;
    ImageButton previous_event_button;
    int routeCount = 0;
    EventInformation eventInformation;
    ArrayList<EventInformation> events = new ArrayList<>();


    public static EventLocationFragment newInstance(CustomGoogleApiClient customGoogleApiClient) {
        EventLocationFragment fragment = new EventLocationFragment();
        Bundle args = new Bundle();

        args.putParcelable(ID.CUSTOMCLIENT.name(), customGoogleApiClient);
        fragment.setArguments(args);

        return fragment;
    }

    public EventLocationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            customGoogleApiClient = getArguments().getParcelable(ID.CUSTOMCLIENT.name());
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_maps, container, false);
        next_event_button = (ImageButton) view.findViewById(R.id.next_event);
        previous_event_button = (ImageButton) view.findViewById(R.id.previous_event);
        next_event_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                focusPoint(true);
            }
        });

        previous_event_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                focusPoint(false);

            }
        });
        mapView = (MapView) view.findViewById(R.id.event_map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


        // TODO: 12/11/2016 create the same search feature but control the search radius behind the scenes

        return view;

    }

    private void focusPoint(boolean forward) {
        //change the focus point by counting through the array list and executing a route
        //how the fuck do i do this
        Location myLocation = customGoogleApiClient.getLastLocation();


        if (events.size()!=0){
            eventInformation = events.get(routeCount);

            LatLng eventLat = new LatLng(eventInformation.getLatitude(), eventInformation.getLongitude());
            LatLng myLat = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
            String pushId = eventInformation.getPushID();
            if(routes.get(pushId)!=null){
                PolylineOptions polylineOptions = routes.get(pushId);
                if (visibleLine!=null && visibleLine.isVisible()){
                    visibleLine.remove();
                    visibleLine = googleMap.addPolyline(polylineOptions);
                }
            } else {
                Routing routing = new Routing.Builder().waypoints(myLat, eventLat).
                        withListener(this).travelMode(AbstractRouting.TravelMode.DRIVING).build();
                routing.execute();
            }

        }

       try {
           if (forward)
               routeCount = (routeCount+1) %events.size();
           else
               routeCount = Math.abs((routeCount -1) % events.size());

       } catch (ArithmeticException ex){
           ex.printStackTrace();
       }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();

    }


    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        mapView.onSaveInstanceState(bundle);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;
        int padding = (int) getActivity().getResources().getDimension(R.dimen.map_margin);

        this.googleMap.setPadding(padding, padding, padding, padding);


        this.googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                // TODO: 12/12/2016 find new places
                reference.child("events").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        GetLocations getLocations = new GetLocations(customGoogleApiClient, googleMap);
                        getLocations.setListener(EventLocationFragment.this);
                        getLocations.setDataSnapShot(dataSnapshot);
                        getLocations.execute();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                return false;
            }
        });
        requestPermissions(googleMap);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    public void requestPermissions(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            googleMap.setMyLocationEnabled(true);

        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    getResources().getInteger(R.integer.location));
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == getResources().getInteger(R.integer.location)) {
            if (permissions.length != 0 &&
                    permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // FIXME: 10/23/2016 this is stupid why do i have to check twice what the fuck
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.

                }
                googleMap.setMyLocationEnabled(true);



            }
        }


    }



    @Override
    public void setLocations(EventInformation event) {
        /**
         * find
         */
        this.events.add(event);

    }

    @Override
    public void startRoutes(ArrayList<EventInformation> events) {
        /*get initial location*/
        // TODO: 12/19/2016 order by closest place
        // TODO: 12/19/2016 check how to find first value in hashmap

        if(events.size()!=0) {

            eventInformation = events.get(0);
            LatLng event = new LatLng(eventInformation.getLatitude(), eventInformation.getLongitude());
            Location myLoc = customGoogleApiClient.getLastLocation();
            LatLng myLat = new LatLng(myLoc.getLatitude(), myLoc.getLongitude());

            Routing routing = new Routing.Builder().
                    travelMode(AbstractRouting.TravelMode.DRIVING).
                    key(getResources().
                            getString(R.string.google_api_key)).withListener(this)
                    .waypoints(myLat, event).build();
            routing.execute();
            // TODO: 12/19/2016 make own routing library or modify this guy's
        } else {
            // TODO: 12/19/2016 figure out something to tell the user if there are no locations
            Toast.makeText(getActivity(), "No events near your area", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRoutingFailure(RouteException e) {
        Log.i("Routing failure", "failure: "+e.getMessage());
    }

    @Override
    public void onRoutingStart() {
        // TODO: 12/19/2016 set up progress dialog
        Log.i("Routing start", "start");

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> arrayList, int i) {
        Route route = arrayList.get(i);
        PolylineOptions polylineOptions = new PolylineOptions();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            polylineOptions.color(getResources().getColor(R.color.blue_300, null));
        } else {
            polylineOptions.color(getResources().getColor(R.color.blue_300));
        }
        polylineOptions.addAll(route.getPoints());
        routes.put(eventInformation.getPushID(), polylineOptions);

       if(visibleLine!=null && visibleLine.isVisible())
           visibleLine.remove();
       visibleLine= googleMap.addPolyline(polylineOptions);

    }


    @Override
    public void onRoutingCancelled() {
        Log.i("Routing cancelled", "cancelled");
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */


}
