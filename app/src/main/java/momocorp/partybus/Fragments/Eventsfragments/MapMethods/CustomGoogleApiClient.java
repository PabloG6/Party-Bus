package momocorp.partybus.Fragments.Eventsfragments.MapMethods;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;


import java.util.List;

import momocorp.partybus.Fragments.Eventsfragments.FragmentInterface;
import momocorp.partybus.R;

/**
 * Created by Pablo on 10/23/2016.
 */
public class CustomGoogleApiClient implements ConnectionCallbacks,
        OnConnectionFailedListener, LocationListener, Parcelable {
    private Location mLastLocation;
    private GoogleApiClient googleApiClient;
    private Context context;
    private LocationRequest locationRequest;
    long fast_interval;
    long norm_interval;
    FragmentInterface fragmentInterface;

    public CustomGoogleApiClient(Context context) {
        this.context = context;
        locationRequest = new LocationRequest();
        fast_interval = (long) context.getResources().getInteger(R.integer.fast_interval_speed);
        norm_interval = (long) context.getResources().getInteger(R.integer.norm_interval_speed);
        locationRequest.setFastestInterval(fast_interval);
        locationRequest.setInterval(norm_interval);
        fragmentInterface = (FragmentInterface) context;


    }

    protected CustomGoogleApiClient(Parcel in) {
        mLastLocation = in.readParcelable(Location.class.getClassLoader());
        locationRequest = in.readParcelable(LocationRequest.class.getClassLoader());
        fast_interval = in.readLong();
        norm_interval = in.readLong();
    }

    public static final Creator<CustomGoogleApiClient> CREATOR = new Creator<CustomGoogleApiClient>() {
        @Override
        public CustomGoogleApiClient createFromParcel(Parcel in) {
            return new CustomGoogleApiClient(in);
        }

        @Override
        public CustomGoogleApiClient[] newArray(int size) {
            return new CustomGoogleApiClient[size];
        }
    };

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (googleApiClient != null)
            if (ActivityCompat.
                    checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            }

        Log.i("On Connected", "Connection called");
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);


    }

    /**
     *
     * @return mLastLocation
     */
    public Location getLastLocation() {
        return mLastLocation;
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(context, "Connection suspended", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(context, "Connection failed", Toast.LENGTH_SHORT).show();
    }

    public void setGoogleApiClient(GoogleApiClient googleApiClient) {
        this.googleApiClient = googleApiClient;
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;

    }

    /**
     *
     * @returns last known location with fused location service provider
     */
    public Location getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                googleApiClient);
        if (mLastLocation != null) {
            return mLastLocation;
        } else {
            Toast.makeText(context, "Please give us you're closes location", Toast.LENGTH_SHORT).show();
            return null;
        }

    }

    /**
     *
     * @return last known location based on other application locations
     */
    public Location getLastLocationFromProvider() {
        LocationManager mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
            }
            Location l = mLocationManager.getLastKnownLocation(provider);


            if (l == null) {
                continue;
            }
            if (bestLocation == null
                    || l.getAccuracy() < bestLocation.getAccuracy()) {

                bestLocation = l;
            }
        }
        if (bestLocation == null) {
            return null;
        }
        return bestLocation;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(mLastLocation, i);
        parcel.writeParcelable(locationRequest, i);
        parcel.writeLong(fast_interval);
        parcel.writeLong(norm_interval);
    }
}
