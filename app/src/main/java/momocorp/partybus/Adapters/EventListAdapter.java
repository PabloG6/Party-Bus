package momocorp.partybus.Adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CpuUsageInfo;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
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
import momocorp.partybus.misc.ColorEnum;

/**
 * Created by Pablo on 12/15/2016.
 */
public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder> {
    // TODO: 12/16/2016 create asynchronous method which downloads sixteen events and attaches them to array list
    ArrayList<EventInformation> events;

    CustomGoogleApiClient customGoogleApiClient;
    Context context;
    private final static int TEXT = 0;
    private final static int EVENT = 1;

    /**
     * @param events                the list of events within 8040 metres (5 miles) of the user
     * @param customGoogleApiClient used to access the users most recent location
     */
    public EventListAdapter(ArrayList<EventInformation> events,
                            CustomGoogleApiClient customGoogleApiClient, Context context) {
        this.events = events;
        this.customGoogleApiClient = customGoogleApiClient;
        // populate view for the first time
        EventLoader eventLoader = new EventLoader(events, customGoogleApiClient);
        this.context = context;
        eventLoader.execute();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == events.size()) ? TEXT : EVENT;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        // TODO: 12/17/2016 return loading view if at final point of recyclerview
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_card_view, parent, false);
        return new EventViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        EventInformation eventInformation = events.get(position);


        holder.firstLetterOfEvent.setText(String.valueOf(Character.toUpperCase(eventInformation.getTitle().charAt(0))));
        holder.titleOfEvent.setText(eventInformation.getTitle());
        holder.seeEventDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Show description", Toast.LENGTH_SHORT).show();
            }
        });
        int colorPosition = position % ColorEnum.values().length;
        ColorEnum colorEnum = ColorEnum.values()[colorPosition];
        int color = Color.parseColor(context.getResources().getString(colorEnum.getBackGroundColorResId()));
        holder.relativeLayout.setBackgroundColor(color);

    }

    @Override
    public int getItemCount() {

        return events.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView loadingText;
        TextView titleOfEvent;
        TextView firstLetterOfEvent;
        View seeEventDescription;

        RelativeLayout relativeLayout;
        public EventViewHolder(View itemView) {
            super(itemView);
            titleOfEvent = (TextView) itemView.findViewById(R.id.event_title);
            firstLetterOfEvent = (TextView) itemView.findViewById(R.id.first_event_title);
            seeEventDescription = itemView.findViewById(R.id.get_info);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.event_card);

        }


    }


    class EventLoader extends AsyncTask<Object, Void, ArrayList<EventInformation>> {
        DatabaseReference eventDatabase = FirebaseDatabase.getInstance().getReference("events");
        ArrayList<EventInformation> events;
        int count = 0;
        Location location;

        public EventLoader(ArrayList<EventInformation> events,
                           CustomGoogleApiClient customGoogleApiClient) {
            this.events = events;

            if(customGoogleApiClient.getLastLocation()==null) {
                location = customGoogleApiClient.getLastKnownLocation();
            } else {
                location = customGoogleApiClient.getLastLocation();
            }
        }

        @Override
        protected ArrayList<EventInformation> doInBackground(Object... objects) {

            eventDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot :
                            dataSnapshot.getChildren()) {
                        float[] results = new float[3];
                        int radius = 8040;

                        EventInformation event = snapshot.getValue(EventInformation.class);
                        // TODO: 12/17/2016 make sure to wait on the location and make sure it's not null
                        if(location!=null){
                            Location.distanceBetween(location.getLatitude(), location.getLongitude(), event.getLatitude(), event.getLongitude(), results);
                        } else {
                            location = customGoogleApiClient.getLastLocationFromProvider();
                            if (location!=null){
                                Location.distanceBetween(location.getLatitude(), location.getLongitude(), event.getLatitude(), event.getLongitude(), results);
                            } else {

                                // TODO: 12/18/2016 store user information based on where they frequent and use that instead.
                                Toast.makeText(context, "We can't find you're last known location", Toast.LENGTH_SHORT).show();
                            }
                        }

                        if (radius > results[0]) {
                            events.add(event);
                            count++;
                            publishProgress();
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
            notifyDataSetChanged();
            Toast.makeText(context, "Hey i'm updating", Toast.LENGTH_SHORT).show();

        }
    }


}




