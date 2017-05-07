package momocorp.partybus.Fragments.Eventsfragments.MapMethods;


import android.location.Location;
import android.os.AsyncTask;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.MarkerOptions;

import com.google.firebase.database.DataSnapshot;


import java.util.ArrayList;
import java.util.LinkedHashMap;


import momocorp.partybus.CustomObjects.EventInformation;

/**
 * Created by Pablo on 11/27/2016.
 */
public class GetLocations extends AsyncTask<Object, EventInformation, ArrayList<EventInformation>> {
    ArrayList<EventInformation> eventList = new ArrayList<>();
    CustomGoogleApiClient apiClient;
    int radius;
    Location location;
    float[] results = new float[2];
    private GoogleMap googleMap;

    DataSnapshot dataSnapshot;
    /** TODO: 12/18/2016 turn into a builder class
     *    @link http://www.javaworld.com/article/2074938/core-java/too-many-parameters-in-java-methods-part-3-builder-pattern.html
     */
GetLocationsListener listener;
    public GetLocations(CustomGoogleApiClient customGoogleApiClient, GoogleMap googleMap){
        this.apiClient = customGoogleApiClient;
        this.googleMap = googleMap;

        
    }

   public void setDataSnapShot(DataSnapshot dataSnapshot){
        this.dataSnapshot = dataSnapshot;
    }
    

    @Override
    protected ArrayList<EventInformation> doInBackground(Object... params) {

// TODO: 12/23/2016 make sure to request location updates
        radius = 3000;

        location = apiClient.getLastLocation();
        for (DataSnapshot snapShot :
                dataSnapshot.getChildren()) {
            EventInformation event = snapShot.getValue(EventInformation.class);
            if(event.getPushID()==null)
                event.setPushID(snapShot.getKey());

            Location.distanceBetween(location.getLatitude(), location.getLongitude(), event.getLatitude(), event.getLongitude(), results);
            if(radius > results[0]) {

                eventList.add(event);
                publishProgress(event);
            }

        }


        return eventList;
    }

    @Override
    protected void onPostExecute(ArrayList<EventInformation> eventInformation) {
        super.onPostExecute(eventInformation);
        // TODO: 12/17/2016 find a way to remove old markers from eventList
        listener.startRoutes(eventInformation);


    }

    @Override
    protected void onProgressUpdate(EventInformation... eventInformation) {
        super.onProgressUpdate(eventInformation);
        EventInformation event = eventInformation[0];
        // TODO: 12/18/2016 change to LinkedHashMap with key value pairs
        listener.setLocations(event);
        LatLng eventLatLng = new LatLng(event.getLatitude(), event.getLongitude());
        googleMap.addMarker(new MarkerOptions().
                position(eventLatLng).
                title(event.getTitle()));


    }

    public void setListener(GetLocationsListener listener) {
        this.listener = listener;
    }

    public interface GetLocationsListener{
        // TODO: 12/18/2016 remove the arrayList and replace with LinkedHashMap
        void startRoutes(ArrayList<EventInformation> events);
        void setLocations(EventInformation events);


    }
}

