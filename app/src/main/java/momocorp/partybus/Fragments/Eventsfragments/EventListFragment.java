package momocorp.partybus.Fragments.Eventsfragments;

import android.Manifest;
import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v13.app.FragmentCompat;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Stack;

import momocorp.partybus.Adapters.EventListAdapter;
import momocorp.partybus.Adapters.LoadingCacheListener;
import momocorp.partybus.CustomObjects.EventInformation;
import momocorp.partybus.Fragments.Eventsfragments.MapMethods.CustomGoogleApiClient;
import momocorp.partybus.R;
import momocorp.partybus.misc.ID;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventListFragmentListener} interface
 * to handle interaction events.
 * Use the {@link EventListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventListFragment extends Fragment implements LoadingCacheListener {
    FirebaseRecyclerAdapter<EventInformation, EventListAdapter.EventViewHolder> adapter;
    private CustomGoogleApiClient customGoogleApiClient;
    LinearLayoutManager linearLayoutManager;
    Stack<EventInformation> events;
    private EventListFragmentListener eventListListener;
    SwipeRefreshLayout refreshLayout;
    RecyclerView listRecyclerView;
    ImageView loading;
    private static final int REQUEST_LOCATION = 494929;
    private GoogleMap googleMap;
    private EventListAdapter.RouteLocations startRouting;


    public EventListFragment() {
        // Required empty public constructor
    }


    public static EventListFragment newInstance(CustomGoogleApiClient customGoogleApiClient) {
        EventListFragment fragment = new EventListFragment();
        Bundle args = new Bundle();
        args.putParcelable(ID.CUSTOMCLIENT.name(), customGoogleApiClient);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.customGoogleApiClient = getArguments().getParcelable(ID.CUSTOMCLIENT.name());


        }


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @SuppressLint("SecurityException")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                googleMap.setMyLocationEnabled(true);
                startRouting.startRouting();

            }
        }
    }

    public void orientMap(GoogleMap googleMap, EventListAdapter.RouteLocations startRouting) {
        this.googleMap = googleMap;
        this.startRouting = startRouting;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: change request code
            FragmentCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            return;
        }
        googleMap.setMyLocationEnabled(true);
        startRouting.startRouting();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        listRecyclerView = (RecyclerView) view.findViewById(R.id.event_list_recycler);
        EventListAdapter.Builder builder = new EventListAdapter.Builder()
                .setApiClient(customGoogleApiClient).setContext(getActivity()).setEvents(events).
                        setLoadingListener(this).setFragment(this);
        final EventListAdapter eventListAdapter = builder.build();

        listRecyclerView.setAdapter(eventListAdapter);
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(500);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        listRecyclerView.setItemAnimator(animator);
        DividerItemDecoration itemDecoration =
                new DividerItemDecoration(listRecyclerView.getContext(), linearLayoutManager.getOrientation());

        listRecyclerView.addItemDecoration(itemDecoration);
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("events");
        listRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new FirebaseRecyclerAdapter<EventInformation, EventListAdapter.EventViewHolder>(EventInformation.class, R.layout.event_card_layout, EventListAdapter.EventViewHolder.class, dataRef) {
            @Override
            protected void populateViewHolder(EventListAdapter.EventViewHolder viewHolder, EventInformation model, int position) {
                viewHolder.setLocation(model.getAddress());
                viewHolder.setEventTitle(model.getTitle());
                viewHolder.setPrice(model.getPrice());

            }
        };


        listRecyclerView.setAdapter(adapter);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("event list", "on attach called");
        try {
            eventListListener = (EventListFragmentListener) context;
        } catch (RuntimeException ex) {
            throw new RuntimeException("Must implement EventFragmentListener");
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i("event list", "on attach called");
        try {
            eventListListener = (EventListFragmentListener) activity;
        } catch (RuntimeException ex) {
            throw new RuntimeException("Must implement EventFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
    @Override
    public boolean loadingStart() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

        }
        return false;
    }

    @Override
    public boolean loading() {
        //start trigger which starts minimum four second animation
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean loadingFinished() {

//        refreshLayout.setRefreshing(false);
        listRecyclerView.scrollToPosition(events.size() - 1);
        //make sure at least four seconds have passed before the animation is complete
        return false;
    }

    public interface EventListFragmentListener {
        void addEvent();
    }
}
