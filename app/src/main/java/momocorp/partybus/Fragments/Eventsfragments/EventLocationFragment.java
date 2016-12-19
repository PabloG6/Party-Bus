package momocorp.partybus.Fragments.Eventsfragments;

import android.Manifest;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ActivityCompat;
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

import java.util.ArrayList;
import java.util.HashMap;


import momocorp.partybus.CustomObjects.EventInformation;
import momocorp.partybus.Fragments.Eventsfragments.MapMethods.CustomGoogleApiClient;
import momocorp.partybus.Fragments.Eventsfragments.MapMethods.GetLocations;
import momocorp.partybus.R;
import momocorp.partybus.misc.ColorEnum;

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

    ArrayList<PolylineOptions> routes = new ArrayList<>();
    CustomGoogleApiClient customGoogleApiClient;

    private EventFragment.EventFragmentListener mListener;
    private Context mContext;
    Polyline line;
    ImageButton next_event_button;
    ImageButton previous_event_button;
    public ArrayList<EventInformation> events;
    RoutingListener routingListener = new RoutingListener() {
        @Override
        public void onRoutingFailure(RouteException e) {

        }

        @Override
        public void onRoutingStart() {

        }

        @Override
        public void onRoutingSuccess(ArrayList<Route> arrayList, int i) {

        }

        @Override
        public void onRoutingCancelled() {

        }
    };

    public static EventLocationFragment newInstance(CustomGoogleApiClient customGoogleApiClient) {
        EventLocationFragment fragment = new EventLocationFragment();
        Bundle args = new Bundle();
        args.putParcelable("CustomGoogleApiClient", customGoogleApiClient);
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
            customGoogleApiClient = getArguments().getParcelable("CustomGoogleApiClient");
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
                focusPoint();
            }
        });

        previous_event_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                focusPoint();
            }
        });
        mapView = (MapView) view.findViewById(R.id.event_map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


        // TODO: 12/11/2016 create the same search feature but control the search radius behind the scenes

        return view;

    }

    private void focusPoint() {
        //change the focus point by counting through the array list and executing a route
        //how the fuck do i do this lmao
        
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EventFragment.EventFragmentListener) {
            mListener = (EventFragment.EventFragmentListener) context;
            mContext = context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement EventLocationInterface");
        }
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
        mListener = null;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;
        int padding = (int) getActivity().getResources().getDimension(R.dimen.map_margin);

        this.googleMap.setPadding(padding, padding, padding, padding);
        final GetLocations getLocations = new GetLocations(routingListener);
        getLocations.setListener(this);

        this.googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                // TODO: 12/12/2016 find new places
                if (getLocations.getStatus() == AsyncTask.Status.FINISHED)
                    getLocations.execute(customGoogleApiClient, googleMap);

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
    public void setLocations(HashMap<String, EventInformation> events) {
        // TODO: 12/18/2016 implement this instead
    }

    @Override
    public void setLocations(ArrayList<EventInformation> events) {
        this.events = events;

       EventInformation eventInformation = events.get(0);
            LatLng myLocation = new LatLng(customGoogleApiClient.getLastLocation().
                    getLatitude(), customGoogleApiClient.getLastLocation().getLongitude());
            LatLng eventLocation = new LatLng(eventInformation.getLatitude(),
                    eventInformation.getLongitude());
            Routing routing = new Routing.Builder().
                    travelMode(AbstractRouting.TravelMode.WALKING).withListener(this)
                    .waypoints(myLocation, eventLocation).build();
            routing.execute();


        }

    }

    @Override
    public void onRoutingFailure(RouteException e) {

    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> arrayList, int i) {

    }

    @Override
    public void onRoutingCancelled() {

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
