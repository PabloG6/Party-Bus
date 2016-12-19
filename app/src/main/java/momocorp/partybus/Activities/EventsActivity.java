package momocorp.partybus.Activities;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
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
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import momocorp.partybus.Adapters.EventFragmentAdapter;
import momocorp.partybus.Fragments.Eventsfragments.EventFragment;
import momocorp.partybus.Fragments.Eventsfragments.EventListFragment;
import momocorp.partybus.Fragments.Eventsfragments.EventLocationFragment;
import momocorp.partybus.Fragments.Eventsfragments.EventSubmissionFragment;
import momocorp.partybus.Fragments.Eventsfragments.FragmentInterface;
import momocorp.partybus.Fragments.Eventsfragments.MapMethods.CustomGoogleApiClient;
import momocorp.partybus.R;


public class EventsActivity extends AppCompatActivity implements EventSubmissionFragment.EventSubmissionListener, FragmentInterface {
    String eventSubFragmentTag;
    EventFragment eventFragment;
    ;
    CustomGoogleApiClient customGoogleApiClient;
    GoogleApiClient googleApiClient;
    ViewPager eventPager;




    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        // TODO: 12/17/2016 save the information when out of view
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        //connect api client if it's not connected
        customGoogleApiClient = new CustomGoogleApiClient(this);
        googleApiClient = new GoogleApiClient.Builder(this).
                addOnConnectionFailedListener(customGoogleApiClient)
                .addConnectionCallbacks(customGoogleApiClient).addApi(LocationServices.API).build();
        googleApiClient.connect();
        customGoogleApiClient.setGoogleApiClient(googleApiClient);

        eventFragment = new EventFragment();
        eventSubFragmentTag = getResources().getString(R.string.event_submission);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(EventLocationFragment.newInstance(customGoogleApiClient));
        fragments.add(EventListFragment.newInstance(customGoogleApiClient));


        EventFragmentAdapter eventFragmentAdapter = new EventFragmentAdapter(getFragmentManager(), fragments);

        eventPager = (ViewPager) findViewById(R.id.event_pager);
        eventPager.setAdapter(eventFragmentAdapter);
        eventPager.setOffscreenPageLimit(2);


        //// TODO: 10/22/2016 check if fragment is in container

//        getFragmentManager().beginTransaction().add(R.id.events_container, eventFragment,
//                eventFragmentTag)
//                .commit();


    }


    public void requestPermissions(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            googleMap.setMyLocationEnabled(true);

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    getResources().getInteger(R.integer.location));
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //todo flesh out toolbar
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
