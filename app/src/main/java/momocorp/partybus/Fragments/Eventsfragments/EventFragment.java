package momocorp.partybus.Fragments.Eventsfragments;

import android.content.Context;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import momocorp.partybus.Adapters.EventFragmentAdapter;
import momocorp.partybus.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventFragmentListener} interface
 * to handle interaction events.
 * Use the {@link EventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventFragment extends Fragment implements OnMapReadyCallback {
    ViewPager eventPager;
    private String mParam1;
    private String mParam2;
    String event_map_fragment, event_list_fragment;
    private EventFragmentListener mListener;
    EventListFragment eventListFragment;
    EventLocationFragment eventMapFragment;
    private boolean eventMapShow = true;

    public EventFragment() {
        // Required empty public constructor
    }


    public static EventFragment newInstance() {
        EventFragment fragment = new EventFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event, container, false);


//
////        EventFragmentAdapter eventFragmentAdapter = new EventFragmentAdapter(getFragmentManager());
//
//        eventPager = (ViewPager) view.findViewById(R.id.event_pager);
//        eventPager.setAdapter(eventFragmentAdapter);


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EventFragmentListener) {
            mListener = (EventFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement EventListFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
    }


    // FIXME: 12/12/2016 fix this convoluted mess of fragment communication
    public void updateCamera(LatLng latLng) {
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface EventFragmentListener {
        void onFragmentInteraction();

    }
}
