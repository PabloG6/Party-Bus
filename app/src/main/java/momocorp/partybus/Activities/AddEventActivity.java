package momocorp.partybus.Activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import momocorp.partybus.CustomObjects.EventInformation;
import momocorp.partybus.Fragments.Eventsfragments.MapMethods.CustomGoogleApiClient;
import momocorp.partybus.R;
import momocorp.partybus.misc.ID;

public class AddEventActivity extends AppCompatActivity {
    EventInformation eventInformation = new EventInformation();
    private GoogleApiClient googleApiClient;
    PlacePhotoMetadataResult result;
    //// TODO: 5/18/2017 either add an image from user interface or use a default place image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        CustomGoogleApiClient googleApiClient = getIntent().getParcelableExtra(ID.CUSTOMCLIENT.name());
        this.googleApiClient = googleApiClient.googleApiClient();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        EditText title = (EditText) findViewById(R.id.event_title);
        Switch isDrinks = (Switch) findViewById(R.id.drinks_available);


        PlaceAutocompleteFragment placeAutocompleteFragment =
                (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_auto_complete_fragment);
        placeAutocompleteFragment.
                setOnPlaceSelectedListener(new PlaceSelectionListener() {
                                               @Override
                                               public void onPlaceSelected(Place place) {
                                                   eventInformation.setLongitude(place.getLatLng().longitude);
                                                   eventInformation.setLatitude(place.getLatLng().latitude);
                                                   eventInformation.setAddress(place.getAddress().toString());
                                                   eventInformation.setPlaceId(place.getId());
//                                                   result  = Places.GeoDataApi.getPlacePhotos(googleApiClient, place.getId()).await();

                                               }

                                               @Override
                                               public void onError(Status status) {

                                               }
                                           }
                );
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                eventInformation.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null && !editable.toString().equals(""))
                    eventInformation.setTitle(editable.toString());
            }
        });

        EditText price = (EditText) findViewById(R.id.price);
        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                eventInformation.setPrice(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null && !editable.toString().equals(""))
                    eventInformation.setPrice(editable.toString());
            }
        });

        final TextView enter_date = (TextView) findViewById(R.id.enter_date);
        enter_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //enter the date via dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(AddEventActivity.this);
                View view_1 = LayoutInflater.from(AddEventActivity.this).inflate(R.layout.calendar_date_layout, null);
                final DatePicker datePicker = (DatePicker) view_1.findViewById(R.id.calendar_date);

                builder.setView(view_1);

                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int day = datePicker.getDayOfMonth();
                        int year = datePicker.getYear();
                        int month = datePicker.getMonth() + 1;
                        enter_date.setText(month + "/" + day + "/" + year);

                        EventInformation.Date date = new EventInformation.Date(datePicker.getDayOfMonth(),
                                datePicker.getMonth() + 1, datePicker.getYear());
                        eventInformation.setDate(date);
                    }


                });


                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.create();
                builder.show();
            }
        });

        final TextView enter_time = (TextView) findViewById(R.id.enter_time);
        enter_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //enter the date via dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(AddEventActivity.this);
                View view_1 = LayoutInflater.from(AddEventActivity.this).inflate(R.layout.time_date_layout, null);
                final TimePicker timePicker = (TimePicker) view_1.findViewById(R.id.choose_time);
                builder.setView(view_1);
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EventInformation.Time time = new EventInformation.Time(
                                timePicker.getCurrentHour(), timePicker.getCurrentMinute());
                        eventInformation.setTime(time);
                        enter_time.setText(timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute());
                    }
                });


                builder.create();

                builder.show();
            }
        });

        isDrinks.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                eventInformation.setDrinks(b);
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (eventInformation.getAddress() == null) {
                    Snackbar.make(view, "You need a location address!", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (eventInformation.getTitle() == null | eventInformation.getTitle().length() == 0) {
                    Toast.makeText(AddEventActivity.this, "You need a title!", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("events").push().setValue(eventInformation).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddEventActivity.this, "You're event has been published!", Snackbar.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddEventActivity.this, "Oops Something went wrong please check your internet connection!", Snackbar.LENGTH_SHORT).show();
                        finish();
                    }
                });


            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    //// TODO: 5/18/2017 create place photo api that gets list of photos
//    private class PlacePhoto extends AsyncTask<String, Void, PlacePhoto.AttributedPhoto> {
//
//        @Override
//        protected PlacePhoto.AttributedPhoto doInBackground(String... strings) {
//            return null;
//        }
//    }
}
