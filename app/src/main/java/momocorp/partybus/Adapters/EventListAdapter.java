package momocorp.partybus.Adapters;

import android.annotation.TargetApi;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CpuUsageInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

import momocorp.partybus.CustomObjects.EventInformation;
import momocorp.partybus.Fragments.Eventsfragments.MapMethods.CustomGoogleApiClient;
import momocorp.partybus.R;

/**
 * Created by Pablo on 12/15/2016.
 */
public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder> {
    // TODO: 12/16/2016 create asynchronous method which downloads sixteen events and attaches them to array list
    ArrayList<EventInformation> events;
    Location location;
    CustomGoogleApiClient customGoogleApiClient;

    private final static int TEXT = 0;
    private final static int EVENT = 1;

    /**
     * @param events   the list of events within 8040 metres (5 miles) of the user
     * @param location used to find the users most recent location

     */
    public EventListAdapter(ArrayList<EventInformation> events, Location location,
                            CustomGoogleApiClient customGoogleApiClient) {
        this.events = events;
        this.location = location;
        this.customGoogleApiClient = customGoogleApiClient;
        // populate view for the first time
        EventLoader eventLoader = new EventLoader(events, customGoogleApiClient);
        eventLoader.execute();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == events.size()) ? TEXT : EVENT;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == TEXT) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_card_view, parent, false);
            return new EventViewHolder(itemView);
        } else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_text, parent, false);
            return new EventViewHolder(itemView, "");
        }


    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {

        return 0;
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView loadingText;
        TextView titleOfEvent;
        TextView firstLetterOfEvent;
        View seeEventDescription;


        public EventViewHolder(View itemView) {
            super(itemView);
            titleOfEvent = (TextView) itemView.findViewById(R.id.event_title);
            firstLetterOfEvent = (TextView) itemView.findViewById(R.id.first_event_title);
            seeEventDescription = itemView.findViewById(R.id.get_info);

        }

        public EventViewHolder(View itemView, String param) {
            super(itemView);
            loadingText = (TextView) itemView;

        }
    }




    class EventLoader extends AsyncTask<Object, Void, ArrayList<EventInformation>> {
        DatabaseReference eventDatabase = FirebaseDatabase.getInstance().getReference();
        ArrayList<EventInformation> events;
        int count = 0;
        Location location;

        public EventLoader(ArrayList<EventInformation> events,
                           CustomGoogleApiClient customGoogleApiClient) {
            this.events = events;
            this.location = customGoogleApiClient.getLastLocation();
        }

        @Override
        protected ArrayList<EventInformation> doInBackground(Object... objects) {

            eventDatabase.startAt(events.size()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot :
                            dataSnapshot.getChildren()) {
                        float[] results = new float[3];
                        int radius = 8040;
                        EventInformation event = snapshot.getValue(EventInformation.class);
                        Location.distanceBetween(location.getLatitude(), location.getLongitude(), event.getLatitude(), event.getLongitude(), results);
                        publishProgress();

                        if (radius > results[0]) {
                            events.add(event);
                            count++;
                            if (count == 16) {
                                break;
                            }
                        }
                    }
                    if (count != 16) {

                    }

                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            return events;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }
    }


}




