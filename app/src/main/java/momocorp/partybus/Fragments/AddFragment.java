package momocorp.partybus.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
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

    public static AddFragment newInstance() {
        AddFragment fragment = new AddFragment();
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
        PlaceAutocompleteFragment placeFragment = (PlaceAutocompleteFragment)
                getChildFragmentManager().findFragmentById(R.id.search_place);
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


}
