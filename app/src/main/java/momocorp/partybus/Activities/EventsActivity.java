package momocorp.partybus.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.maps.model.LatLng;

import momocorp.partybus.Fragments.AddFragment;

import momocorp.partybus.Fragments.Eventsfragments.EventListFragment;
import momocorp.partybus.Fragments.Eventsfragments.EventListFragment.EventListFragmentListener;
import momocorp.partybus.Fragments.Eventsfragments.EventSubmissionFragment;
import momocorp.partybus.Fragments.Eventsfragments.FragmentInterfaces.FragmentInterface;
import momocorp.partybus.Fragments.Eventsfragments.MapMethods.CustomGoogleApiClient;
import momocorp.partybus.R;


public class EventsActivity extends AppCompatActivity
        implements EventSubmissionFragment.EventSubmissionListener, FragmentInterface, EventListFragmentListener {
    String eventSubFragmentTag;
    Toolbar toolbar;
    CustomGoogleApiClient customGoogleApiClient;
    GoogleApiClient googleApiClient;
    Fragment savedContent;
    ViewPager eventPager;


    public enum Fragments {
        ADDEVENT("AddFragment"),
        EVENTMAP("EventMaps"),
        EVENTLIST("EventList"),
        SAVED("Saved");

        String fragmentTag;

        Fragments(String fragment) {
            this.fragmentTag = fragment;
        }

        public String getTag() {
            return fragmentTag;
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getFragmentManager().putFragment(outState, Fragments.SAVED.name(), savedContent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);


        customGoogleApiClient = new CustomGoogleApiClient(this, this);
        googleApiClient = new GoogleApiClient.Builder(this).
                addOnConnectionFailedListener(customGoogleApiClient)
                .addConnectionCallbacks(customGoogleApiClient).addApi(LocationServices.API).build();
        googleApiClient.connect();
        customGoogleApiClient.setGoogleApiClient(googleApiClient);


        eventSubFragmentTag = getResources().getString(R.string.event_submission);
        toolbar = (Toolbar) findViewById(R.id.event_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(R.string.app_name);


        }
        if (savedInstanceState == null) {
            EventListFragment eventListFragment = EventListFragment.newInstance(customGoogleApiClient);
            savedContent = eventListFragment;
            getFragmentManager()
                    .beginTransaction().
                    add(R.id.event_pager,
                            eventListFragment,
                            Fragments.EVENTLIST.name()).
                    addToBackStack(Fragments.EVENTLIST.name()).commit();
        } else {
            savedContent = getFragmentManager().getFragment(savedInstanceState,
                    Fragments.SAVED.name());
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.event_pager, savedContent)
                    .commit();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i("OnRequestPermissions", "request permissions");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //todo flesh out toolbar
            case R.id.add_event:
                Intent intent = new Intent(this, AddActivity.class);
                startActivity(intent);

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public void updateCamera(LatLng latLng) {

    }

    @Override
    public void onFragmentInteraction() {

    }

    @Override
    public void addEvent() {
        if (eventPager.isShown()) {
            eventPager.setVisibility(View.GONE);

            getFragmentManager().beginTransaction().add(R.id.container, AddFragment.newInstance(customGoogleApiClient))
                    .addToBackStack(Fragments.ADDEVENT.getTag()).commit();

        }
    }

}
