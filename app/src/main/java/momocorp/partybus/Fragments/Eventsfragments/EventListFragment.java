package momocorp.partybus.Fragments.Eventsfragments;


import android.annotation.TargetApi;
import android.content.Context;

import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import momocorp.partybus.Adapters.EventListAdapter;
import momocorp.partybus.CustomObjects.EventInformation;
import momocorp.partybus.Fragments.Eventsfragments.MapMethods.CustomGoogleApiClient;
import momocorp.partybus.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventListFragmentListener} interface
 * to handle interaction events.
 * Use the {@link EventListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventListFragment extends Fragment {

    private CustomGoogleApiClient customGoogleApiClient;

    public EventListFragment() {
        // Required empty public constructor
    }



    public static EventListFragment newInstance(CustomGoogleApiClient customGoogleApiClient) {
        EventListFragment fragment = new EventListFragment();
        Bundle args = new Bundle();
        args.putParcelable("CustomGoogleApiClient", customGoogleApiClient);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.customGoogleApiClient = getArguments().getParcelable("CustomGoogleApiClient");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        RecyclerView eventListRecycler = (RecyclerView) view.findViewById(R.id.event_list_recycler);
        EventListAdapter eventListAdapter =
                new EventListAdapter(new ArrayList<EventInformation>(), customGoogleApiClient, getActivity());
        eventListRecycler.setAdapter(eventListAdapter);
        eventListRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    public boolean onChangeEvent(boolean value) {
        return !value;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

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
    public interface EventListFragmentListener {

    }
}
