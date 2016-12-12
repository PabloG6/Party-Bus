package momocorp.partybus.Fragments.Eventsfragments.MapMethods;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import momocorp.partybus.CustomObjects.EventInformation;
import momocorp.partybus.R;

/**
 * Created by Pablo on 10/23/2016.
 */
public class EventMapMethods{


    //create the constructor and pass the radius
    String searchFenceString;
    private static Circle mapCircle;
    GoogleMap googleMap;
    float radius;
    public EventMapMethods(double latitude, double longitude, double radius,
                           Context context, GoogleMap googleMap){
        searchFenceString = context.getResources().getString(R.string.search_fence);
        LatLng latLng = new LatLng(latitude, longitude);
        this.googleMap = googleMap;


    }


    public void setCircle(double latitude, double longitude, double radius){
        LatLng latLng = new LatLng(latitude, longitude);
        if (mapCircle != null){
            mapCircle.setCenter(new LatLng(latitude, longitude));
            mapCircle.setRadius(radius);
        }
         else {
            mapCircle = googleMap.addCircle(new CircleOptions().center(latLng).
                    strokeColor(Color.RED).fillColor(Color.BLUE).radius(radius));
        }


    }





    //receive information from firebase

}
