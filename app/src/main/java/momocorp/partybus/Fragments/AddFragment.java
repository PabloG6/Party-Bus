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
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import momocorp.partybus.Adapters.AddFragmentPagerAdapter;
import momocorp.partybus.CustomObjects.EventInformation;
import momocorp.partybus.Fragments.Eventsfragments.MapMethods.CustomGoogleApiClient;
import momocorp.partybus.R;
import momocorp.partybus.misc.ID;


public class AddFragment extends Fragment {

    EventInformation.Interface eventInfoInterface;
    CustomGoogleApiClient customGoogleApiClient;

    EventInformation eventInformation;

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

    /**
     *
     * @param eventInfo the variable to hold the event information
     * @param addFragmentPagerAdapter the context used to create the interface object to set the eventinformation after it's been set
     * @return instance of fragment
     */
    public static AddFragment newInstance(EventInformation eventInfo, AddFragmentPagerAdapter addFragmentPagerAdapter) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        args.putParcelable(ID.addFragmentAdapter.name(), addFragmentPagerAdapter);
        args.putParcelable(ID.eventInfo.name(), eventInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            eventInformation = getArguments().getParcelable(ID.eventInfo.name());
            eventInfoInterface =  getArguments().getParcelable(ID.addFragmentAdapter.name());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        final View view = inflater.inflate(R.layout.fragment_add, container, false);

        //get layout that searches for the fragment
        PlaceAutocompleteFragment placeFragment = (PlaceAutocompleteFragment)
                getChildFragmentManager().findFragmentById(R.id.search_place);
         TextInputEditText titleEdit = (TextInputEditText) view.findViewById(R.id.event_title);

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

        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

        //pass updated event info via interface
        if (eventInfoInterface != null) {
            eventInfoInterface.setEventInformation(eventInformation);
        }

    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


}
