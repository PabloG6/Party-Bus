package momocorp.partybus.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import momocorp.partybus.CustomObjects.EventInformation;
import momocorp.partybus.EventViewLayout;
import momocorp.partybus.Fragments.Eventsfragments.EventListFragment;
import momocorp.partybus.Fragments.Eventsfragments.MapMethods.CustomGoogleApiClient;
import momocorp.partybus.R;
import momocorp.partybus.misc.ColorEnum;

/**
 * Created by Pablo on 12/15/2016.
 */
public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder>
        implements OnMapReadyCallback, RoutingListener  {
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
    RouteLocations locations = new RouteLocations() {
        @Override
        public void startRouting() {
            if (events!=null) {
                for (EventInformation event :
                        eventInfoStack) {
                    LatLng latLng = new LatLng(event.getLatitude(), event.getLongitude());
                    Location myLoc = customGoogleApiClient.getLastKnownLocation();
                    if(myLoc!=null) {
                        LatLng myLatLng = new LatLng(myLoc.getLatitude(), myLoc.getLongitude());
                        Routing.Builder builder = new Routing.Builder().
                                travelMode(AbstractRouting.TravelMode.DRIVING).
                                waypoints(myLatLng, latLng).withListener(EventListAdapter.this);
                        builder.build();

                    }


                }
            }
        }
    };



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
        updateStuff();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.eventListFragment.orientMap(googleMap, this.locations);

    }

    @Override
    public void onRoutingFailure(RouteException e) {

    }

    @Override
    public void onRoutingStart() {
        //let progress bar be visible
    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> arrayList, int i) {

    }

    @Override
    public void onRoutingCancelled() {

    }

    /**
     * used to build eventlistadapter
     */
    public static class Builder {
        private Stack<EventInformation> eventsInfoStack;
        private CustomGoogleApiClient customGoogleApiClient;
        private Context context;
        private LoadingCacheListener listener;
        private EventListFragment fragment;

        public Builder() {
            //access builder class not method
        }

        public Builder setEvents(Stack<EventInformation> events) {
            this.eventsInfoStack = events;
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
            return new EventListAdapter(eventsInfoStack, customGoogleApiClient, context, listener, fragment);
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

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new EventViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {





    }

    @Override
    public int getItemCount() {
        // TODO: 12/25/2016 remove update stuff
        return 20;
    }

    public void updateStuff() {
        DatabaseReference dataBaseRef = FirebaseDatabase.getInstance().getReference("events");
        if (lastString != null) {

            dataBaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    EventLoader eventLoader = new EventLoader(EventListAdapter.this.eventInfoStack, EventListAdapter.this.customGoogleApiClient, dataSnapshot);
                    eventLoader.execute();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }


            });
        } else {
            dataBaseRef.startAt(lastString).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    EventLoader eventLoader = new
                            EventLoader(EventListAdapter.this.eventInfoStack, EventListAdapter.this.customGoogleApiClient, dataSnapshot);
                    eventLoader.execute();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

    public interface RouteLocations {
        void startRouting();
    }
    static class EventViewHolder extends RecyclerView.ViewHolder {
        ImageButton shareButton;
        ImageButton commentButton;
        TextView numberAttending;
        ImageButton isAttending;
        MapView mapView;
        TextView title;
        TextView circularTitle;
        EventViewHolder(View itemView) {
            super(itemView);
            shareButton = (ImageButton) itemView.findViewById(R.id.share_social);
            commentButton = (ImageButton) itemView.findViewById(R.id.comment_social);
            mapView = (MapView) itemView.findViewById(R.id.event_location);
            isAttending = (ImageButton) itemView.findViewById(R.id.attending_social);
            title = (TextView) itemView.findViewById(R.id.title_of_event);
            numberAttending = (TextView) itemView.findViewById(R.id.number_attending);
            circularTitle = (TextView) itemView.findViewById(R.id.title_first_letter);
            mapView.onCreate(null);
            mapView.onStart();
            mapView.onResume();


        }

        EventViewHolder(EventViewLayout layout) {
            super(layout);

        }


    }


    private class EventLoader extends AsyncTask<Object, Boolean, Stack<EventInformation>> {

        Stack<EventInformation> events;
        int count = 0;
        Location location;
        DataSnapshot dataSnapshot;


        EventLoader(Stack<EventInformation> events,
                    CustomGoogleApiClient customGoogleApiClient, DataSnapshot dataSnapshot) {
            this.events = events;

            if (customGoogleApiClient.getLastLocation() == null) {
                location = customGoogleApiClient.getLastKnownLocation();
            } else {
                location = customGoogleApiClient.getLastLocation();
            }
            this.dataSnapshot = dataSnapshot;
        }

        @Override
        protected Stack<EventInformation> doInBackground(Object... objects) {
            Log.i("async", "async task started");

            float[] results = new float[3];
            int radius = 8040;
            for (DataSnapshot snapshot :
                    dataSnapshot.getChildren()) {
                EventInformation event = snapshot.getValue(EventInformation.class);
                // TODO: 12/17/2016 make sure to wait on the location and make sure it's not null
                if (location != null)
                    Location.distanceBetween(location.getLatitude(), location.getLongitude(), event.getLatitude(), event.getLongitude(), results);
                else if (radius > results[0] && eventMap.get(snapshot.getKey()) == null) {
                    events.add(event);
                    count++;

                    eventMap.put(snapshot.getKey(), event);
                    publishProgress(true);

                } else {
                    Log.i("async", "event not added");
                }
                lastString = snapshot.getKey();

            }


            return events;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Boolean... values) {
            super.onProgressUpdate(values);
            if (values.length > 0 && values[0]) {

                notifyItemInserted(events.size() - 1);

            }


        }


        @Override
        protected void onPostExecute(Stack<EventInformation> eventInformation) {
            super.onPostExecute(eventInformation);
            listener.loadingFinished();
            Log.i("async", "eventInformation: " + eventInformation.size());
        }
    }


}




