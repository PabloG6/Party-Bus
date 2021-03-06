package momocorp.partybus.Fragments.Eventsfragments.MapMethods;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
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


import momocorp.partybus.Fragments.Eventsfragments.FragmentInterface;
import momocorp.partybus.R;

/**
 * Created by Pablo on 10/23/2016.
 */
public class CustomGoogleApiClient implements ConnectionCallbacks,
        OnConnectionFailedListener, LocationListener, Parcelable {
    private Location mLastLocation;
    private GoogleApiClient googleApiClient;
    private  Context context;
    private LocationRequest locationRequest;
    long fast_interval;
    long norm_interval;
    FragmentInterface fragmentInterface;
    public CustomGoogleApiClient(Context context){
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

    public void setGoogleApiClient(GoogleApiClient googleApiClient){
        this.googleApiClient = googleApiClient;
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;

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
