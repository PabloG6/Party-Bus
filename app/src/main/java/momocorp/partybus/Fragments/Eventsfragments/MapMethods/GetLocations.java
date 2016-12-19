package momocorp.partybus.Fragments.Eventsfragments.MapMethods;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import momocorp.partybus.CustomObjects.EventInformation;

/**
 * Created by Pablo on 11/27/2016.
 */
public class GetLocations extends AsyncTask<Object, EventInformation, ArrayList<EventInformation>> {
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    ArrayList<EventInformation> eventList = new ArrayList<>();
    CustomGoogleApiClient apiClient;
    int radius;
    Location location;
    float[] results = new float[2];
    ArrayList<Route> routingList;
    private GoogleMap googleMap;
    RoutingListener routingListener;
GetLocationsListener listener;
    public GetLocations(RoutingListener routingListener){
        this.routingListener = routingListener;
        
    }
    

    @Override
    protected ArrayList<EventInformation> doInBackground(Object... params) {

        apiClient = (CustomGoogleApiClient) params[0];
        radius = 3000;
        this.googleMap = (GoogleMap) params[1];
        location = apiClient.getLastLocation();
        reference.child("events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapShot :
                        dataSnapshot.getChildren()) {
                        Log.i("Locations snapShot", "snapshots");
                   EventInformation event = snapShot.getValue(EventInformation.class);

                    Location.distanceBetween(location.getLatitude(), location.getLongitude(), event.getLatitude(), event.getLongitude(), results);
                    if(radius > results[0]) {

                        eventList.add(event);
                        publishProgress(event);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return eventList;


    }

    @Override
    protected void onPostExecute(ArrayList<EventInformation> eventInformation) {
        super.onPostExecute(eventInformation);
        // TODO: 12/17/2016 find a way to remove old markers from eventList


    }

    @Override
    protected void onProgressUpdate(EventInformation... eventInformation) {
        super.onProgressUpdate(eventInformation);
        EventInformation event = eventInformation[0];
        // TODO: 12/18/2016 change to hashmap with key value pairs
        LatLng eventLatLng = new LatLng(event.getLatitude(), event.getLongitude());
        LatLng myLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        googleMap.addMarker(new MarkerOptions().
                position(myLatLng).
                title(event.getTitle()));

//        Routing routing = new Routing.Builder().
//                withListener(routingListener).
//                waypoints(myLatLng,  eventLatLng).
//                travelMode(AbstractRouting.TravelMode.DRIVING).build();

//        try {
//            //get shortest route from series of routes and add to the map
//
//           Route route = routing.execute().get().get(0);
//            routingList.add(route);
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//    }

    }

    public void setListener(GetLocationsListener listener) {
        this.listener = listener;
    }

    public interface GetLocationsListener{
        // TODO: 12/18/2016 remove the arrayList and replace with hashmap
        void setLocations(HashMap<String, EventInformation> events);
        void setLocations(ArrayList<EventInformation> events);

    }
}

