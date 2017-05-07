package momocorp.partybus;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.LayoutInflaterCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;


/**
 * Created by Pablo on 3/5/2017.
 */

public class EventViewLayout extends CoordinatorLayout {
    private MapView mapView;
    ProgressBar progressBar;
    TextView title;
    TextView titleFirstLetter;
    TextView numberAttending;
    ImageButton comments, attending, share;


    public EventViewLayout(Context context) {
        super(context);
    }

    public void onCreate(Bundle savedInstanceState) {
        if(savedInstanceState==null) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.card_layout, this);
            mapView = (MapView) view.findViewById(R.id.event_location);
            progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
            numberAttending = (TextView) view.findViewById(R.id.number_attending);
            attending = (ImageButton) view.findViewById(R.id.attending_social);
            share = (ImageButton) view.findViewById(R.id.share_social);
            comments = (ImageButton) view.findViewById(R.id.comment_social);

            mapView.onCreate(null);

            return;
        }
        View view = LayoutInflater.from(getContext()).inflate(R.layout.card_layout, this);
        mapView = (MapView) view.findViewById(R.id.event_location);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        numberAttending = (TextView) view.findViewById(R.id.number_attending);
        attending = (ImageButton) view.findViewById(R.id.attending_social);
        share = (ImageButton) view.findViewById(R.id.share_social);
        comments = (ImageButton) view.findViewById(R.id.comment_social);
        mapView.onCreate(savedInstanceState);
    }

    public void mapViewOnResume() {
        if(mapView!=null) {
            mapView.onResume();
        }
    }

    public void mapViewOnStart() {
        mapView.onStart();
    }

    public void mapViewOnStop() {
        mapView.onStop();
    }

    public void mapViewOnPause() {
        mapView.onPause();
    }


}
