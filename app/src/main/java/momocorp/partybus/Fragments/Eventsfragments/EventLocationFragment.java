package momocorp.partybus.Fragments.Eventsfragments;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
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
 *
 */
public class EventLocationFragment extends Fragment implements OnMapReadyCallback {


    private String mParam1;
    private String mParam2;
    MapView mapView;
    GoogleMap googleMap;
    ArrayList<EventInformation> events;
    GoogleApiClient googleApiClient;
    CustomGoogleApiClient customGoogleApiClient;
    SeekBar seekBar;
    private EventFragment.EventFragmentListener mListener;
    private Context mContext;



    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();

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
        }

        customGoogleApiClient = new CustomGoogleApiClient(getActivity());
        googleApiClient = new GoogleApiClient.Builder(getActivity()).
                addOnConnectionFailedListener(customGoogleApiClient)
                .addConnectionCallbacks(customGoogleApiClient).addApi(LocationServices.API).build();
        googleApiClient.connect();
        customGoogleApiClient.setGoogleApiClient(googleApiClient);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_maps, container, false);
        mapView = (MapView) view.findViewById(R.id.event_map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

//        seekBar = (SeekBar) view.findViewById(R.id.seek_bar);
//        seekBar.setMax(2000);
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                Location location = customGoogleApiClient.getLastLocation();
//                if (location!=null) {
//                       EventMapMethods mapMethods = new
//                            EventMapMethods(location.getLatitude(), location.getLongitude(), progress, getActivity(), googleMap);
//                    mapMethods.setCircle(location.getLatitude(), location.getLongitude(), progress);
//                }
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                // TODO: 10/23/2016 get progress of seekbar and push to firebase for geolocation
//                try {
//                    new GetLocations().execute(customGoogleApiClient, seekBar.getProgress(), googleMap).get();
//                    Log.i("Events", "Events check");
//
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });

        // TODO: 12/11/2016 create the same search feature but control the search radius behind the scenes

        return view;

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
        googleApiClient.disconnect();
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
        this.googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                // TODO: 12/12/2016 find new places
                new GetLocations().execute(customGoogleApiClient, googleMap);
                Toast.makeText(getActivity(), "Hey i'm here", Toast.LENGTH_SHORT).show();
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


                if (googleApiClient == null){
                    googleApiClient = new GoogleApiClient.Builder(getActivity())
                            .addConnectionCallbacks(customGoogleApiClient).
                                    addOnConnectionFailedListener(customGoogleApiClient)
                            .addApi(LocationServices.API).build();
                }

                googleApiClient.connect();
                customGoogleApiClient.setGoogleApiClient(googleApiClient);


            }
        }


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
