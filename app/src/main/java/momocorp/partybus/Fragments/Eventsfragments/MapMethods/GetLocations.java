package momocorp.partybus.Fragments.Eventsfragments.MapMethods;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

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

import momocorp.partybus.CustomObjects.EventInformation;

/**
 * Created by Pablo on 11/27/2016.
 */
public class GetLocations extends AsyncTask<Object, Void, ArrayList<EventInformation>> {
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    ArrayList<EventInformation> eventList = new ArrayList<>();
    CustomGoogleApiClient apiClient;
    int radius;
    float[] results = new float[2];
    private GoogleMap googleMap;



    @Override
    protected ArrayList<EventInformation> doInBackground(Object... params) {
        apiClient = (CustomGoogleApiClient) params[0];
        radius = 3000;
        this.googleMap = (GoogleMap) params[1];
        final Location location = apiClient.getLastLocation();
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
        for (EventInformation event :
                eventList) {
            googleMap.addMarker(new MarkerOptions().
                    position(new LatLng(event.getLatitude(), event.getLongitude())).
                    title(event.getTitle()));

        }

    }


}

