package momocorp.partybus.Activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;

import momocorp.partybus.Fragments.Eventsfragments.EventFragment;
import momocorp.partybus.Fragments.Eventsfragments.EventSubmissionFragment;
import momocorp.partybus.Fragments.Eventsfragments.FragmentInterface;
import momocorp.partybus.R;


public class EventsActivity extends AppCompatActivity implements EventSubmissionFragment.EventSubmissionListener, FragmentInterface {
    FloatingActionButton changeEventsFButton;
    Toolbar eventToolBar;
    String eventSubFragmentTag;
    EventFragment eventFragment;
    EventSubmissionFragment eventSubmissionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        eventToolBar = (Toolbar) findViewById(R.id.event_toolbar);
        setSupportActionBar(eventToolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        eventFragment = new EventFragment();
        eventSubmissionFragment = new EventSubmissionFragment();
        final String eventFragmentTag = getResources().getString(R.string.event_fragment);
        eventSubFragmentTag = getResources().getString(R.string.event_submission);


        //// TODO: 10/22/2016 check if fragment is in container

        getFragmentManager().beginTransaction().add(R.id.events_container, eventFragment,
                eventFragmentTag)
                .commit();
        // FIXME: 11/10/2016 make this method better
//        changeEventsFButton = (FloatingActionButton) findViewById(R.id.change_event_focus);
//        changeEventsFButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EventSubmissionFragment eventSubmissionFragment
//                        = (EventSubmissionFragment) getFragmentManager().findFragmentByTag(eventSubFragmentTag);
//                if (eventSubmissionFragment != null && eventSubmissionFragment.isVisible()) {
//                    // TODO: 10/25/2016 call method that submits information.
//                    eventSubmissionFragment.submitInformation();
//                } else {
//                    eventFragment.onChangeFragment();
//
//                }
//            }
//        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //flesh out toolbar
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void updateCamera(LatLng latLng) {
        eventFragment.updateCamera(latLng);
    }

    @Override
    public void onFragmentInteraction() {

    }
}
