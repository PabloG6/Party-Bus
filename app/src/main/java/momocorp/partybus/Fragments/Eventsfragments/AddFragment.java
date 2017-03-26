package momocorp.partybus.Fragments.Eventsfragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import momocorp.partybus.CustomObjects.EventInformation;
import momocorp.partybus.Fragments.Eventsfragments.MapMethods.CustomGoogleApiClient;
import momocorp.partybus.R;
import momocorp.partybus.misc.ID;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {

    ArrayList<String> tags = new ArrayList<>();
    AutoCompleteTextView hashTags;

    CustomGoogleApiClient customGoogleApiClient;

    public AddFragment() {
        // Required empty public constructor
    }

    public static AddFragment newInstance(CustomGoogleApiClient customGoogleApiClient) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        args.putParcelable(ID.CUSTOMCLIENT.name(), customGoogleApiClient);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            customGoogleApiClient = getArguments().getParcelable(ID.CUSTOMCLIENT.name());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final EventInformation eventInformation = new EventInformation();
        final View view = inflater.inflate(R.layout.fragment_add, container, false);
        final EditText priceEdit = (EditText) view.findViewById(R.id.price_edit);
        final TextInputEditText ageEditText = (TextInputEditText) view.findViewById(R.id.age_edit);
        ageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                eventInformation.setAge(Integer.parseInt(editable.toString()));

            }
        });
        priceEdit.setText("0.00");
        priceEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    eventInformation.setPrice(Integer.parseInt(editable.toString()));
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
        });

        PlaceAutocompleteFragment placeFragment = (PlaceAutocompleteFragment) getChildFragmentManager().findFragmentById(R.id.search_fragment);
        final TextInputEditText titleEdit = (TextInputEditText) view.findViewById(R.id.event_title);
        titleEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                eventInformation.setTitle(editable.toString());

            }
        });
        placeFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                eventInformation.setLatitude(place.getLatLng().latitude);
                eventInformation.setLongitude(place.getLatLng().longitude);
                eventInformation.setAddress(place.getAddress().toString());


            }

            @Override
            public void onError(Status status) {
                Toast.makeText(getActivity(), "We seem to have a problem", Toast.LENGTH_SHORT).show();
            }
        });


        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("events");
        final String pushId = reference.push().getKey();
        eventInformation.setPushID(pushId);
        dateMethod(view, eventInformation);
        Button sendButton = (Button) view.findViewById(R.id.submit_event);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View fabView) {
                reference.child(pushId).setValue(eventInformation).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Snackbar.make(view, "Push complete, your event was registered", Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });

//        Button dateButton = (Button) view.findViewById(R.id.date_picker);
//        dateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            }
//        });
        //todo need to figure out how to get date from user

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    // TODO: 3/20/2017 fix this pleeeeeeeeeeeeeaaaaaaaaaaaaaaaaaaassssssssssssseeeeeee

    public void dateMethod(View view, EventInformation eventInformation) {
        Spinner day_picker = (Spinner) view.findViewById(R.id.day_picker);
        Spinner month_picker = (Spinner) view.findViewById(R.id.month_picker);
        Spinner year_picker = (Spinner) view.findViewById(R.id.year_picker);

        ArrayList<Integer> days = new ArrayList<Integer>();
        for (int i = 1; i < 32; i++) {
            days.add(i);
        }

        ArrayList<Integer> months = new ArrayList<>();
        for (int j = 1; j < 13; j++) {
            months.add(j);
        }

        ArrayList<Integer> year = new ArrayList<>();
        for(int k = 2000; k<2030; k++) {
            year.add(k);
        }

        ArrayAdapter<Integer> dayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, days);

        ArrayAdapter<Integer> monthAdapter = new
                ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, days);

        ArrayAdapter<Integer> yearAdapter = new
                ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, year);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        month_picker.setAdapter(monthAdapter);
        day_picker.setAdapter(dayAdapter);
        year_picker.setAdapter(yearAdapter);

        month_picker.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });

        day_picker.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getSelectedItem();

            }
        });

        year_picker.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });


    }

}
