package momocorp.partybus.Adapters;

import android.content.Context;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import de.hdodenhof.circleimageview.CircleImageView;
import momocorp.partybus.CustomObjects.EventInformation;
import momocorp.partybus.Fragments.Eventsfragments.EventListFragment;
import momocorp.partybus.Fragments.Eventsfragments.MapMethods.CustomGoogleApiClient;
import momocorp.partybus.R;

/**
 * Created by Pablo on 12/15/2016.
 */
public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder> {
    // TODO: 12/16/2016 create asynchronous method which downloads sixteen events and attaches them to array list
    private ArrayList<EventInformation> events;
    private Stack<EventInformation> eventInfoStack;
    /**
     * @param lastString  returns the key for the last loaded event in firebase
     */
    private CustomGoogleApiClient customGoogleApiClient;
    private Context context;
    private LoadingCacheListener listener;
    private static String lastString;
    private HashMap<String, EventInformation> eventMap = new HashMap<>();
    private final static int TEXT = 0;
    private final static int EVENT = 1;
    private GoogleMap googleMap;
    MarkerOptions shownLocation;
    EventListFragment eventListFragment;


    /**
     * @param events                the list of events within 8040 metres (5 miles) of the user
     * @param customGoogleApiClient used to access the users most recent location
     * @param listener              used to start loading animation and stop loading animations.
     */
    private EventListAdapter(Stack<EventInformation> events,
                             CustomGoogleApiClient customGoogleApiClient, Context context,
                             LoadingCacheListener listener, EventListFragment eventListFragment) {
        this.eventInfoStack = events;
        this.customGoogleApiClient = customGoogleApiClient;
        // populate view for the first time
        this.context = context;
        this.listener = listener;
        this.eventListFragment = eventListFragment;

    }

    public EventListAdapter() {

    }

    /**
     * used to build eventlistadapter
     */
    public static class Builder {

        private CustomGoogleApiClient customGoogleApiClient;
        private Context context;
        private LoadingCacheListener listener;
        private EventListFragment fragment;

        public Builder() {
            //access builder class not method
        }

        public Builder setEvents(Stack<EventInformation> events) {

            return this;
        }

        public Builder setApiClient(CustomGoogleApiClient customGoogleApiClient) {
            this.customGoogleApiClient = customGoogleApiClient;
            return this;
        }


        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setLoadingListener(LoadingCacheListener listener) {
            this.listener = listener;

            return this;
        }

        public EventListAdapter build() {
            return new EventListAdapter(null, customGoogleApiClient, context, listener, fragment);
        }


        public EventListAdapter.Builder setFragment(EventListFragment fragment) {
            this.fragment = fragment;
            return this;
        }
    }


    @Override
    public int getItemViewType(int position) {
        // TODO: 12/19/2016 load more values here
        return (position == eventInfoStack.size()) ? TEXT : EVENT;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        // TODO: 12/17/2016 return loading view if at final point of recyclerview

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card_layout, parent, false);
        return new EventViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {

//        holder.mapView.getMapAsync(this);
//        holder.recyclerView.setAdapter(new AttendanceAdapter());


    }

    @Override
    public int getItemCount() {
        // TODO: 12/25/2016 remove update stuff
        return 20;
    }


    public interface RouteLocations {
        void startRouting();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {

        //        MapView mapView;
//        RecyclerView recyclerView;
        private final TextView eventTitle;
        private final TextView price;
        private final TextView location;
//        private final CircleImageView imageLocation;

        EventViewHolder(View itemView) {
            super(itemView);
            eventTitle = (TextView) itemView.findViewById(R.id.event_title);
            price = (TextView) itemView.findViewById(R.id.price);
            location = (TextView) itemView.findViewById(R.id.location);
//            imageLocation = (CircleImageView) itemView.findViewById(R.id.event_picture);

        }

        public void setEventTitle(String text) {
            eventTitle.setText(text);
        }


        public void setPrice(String text) {
            price.setText(text);
        }

        public void setLocation(String text) {
            location.setText(text);
        }

        public void setImage() {

        }


    }


}




