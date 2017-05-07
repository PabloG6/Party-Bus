package momocorp.partybus.Fragments.Eventsfragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import momocorp.partybus.CustomObjects.EventInformation;
import momocorp.partybus.DataStructures.UserProfile;
import momocorp.partybus.Fragments.UserInfoFragment.LoginFragment;
import momocorp.partybus.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventSubmissionListener} interface
 * to handle interaction events.
 * Use the {@link EventSubmissionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventSubmissionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    //todo change the style of the placeautocompletefragment
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private TextInputEditText eventTitle;
    private PlaceAutocompleteFragment eventPlaceFragment;
    private EventInformation eventInformation;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public Place mPlace;
    private View mView;
    private EventSubmissionListener mListener;

    public EventSubmissionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventSubmissionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventSubmissionFragment newInstance(String param1, String param2) {
        EventSubmissionFragment fragment = new EventSubmissionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_event_submission, container, false);
        mView = view;
        eventTitle = (TextInputEditText) view.findViewById(R.id.event_title_submit);
        eventPlaceFragment = (PlaceAutocompleteFragment) getChildFragmentManager()
                .findFragmentById(R.id.place_autocomplete_submit);
        eventPlaceFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {


            @Override
            public void onPlaceSelected(Place place) {

                // TODO: 10/25/2016 tie user specific credentials and security to the eventInformation1
                mPlace = place;
                double latitude = place.getLatLng().latitude;
                double longitude = place.getLatLng().longitude;
                CharSequence address = place.getAddress();
                eventInformation = new EventInformation(latitude, longitude);
                eventInformation.setAddress(String.valueOf(address));

            }

            @Override
            public void onError(Status status) {
                Log.i("An error occured Place", ""+status.getStatusMessage());

            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    private void onButtonPressed(){
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EventSubmissionListener) {
            mListener = (EventSubmissionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement EventSubmissionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void submitInformation() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (eventTitle.getText().length()!=0 && mPlace!=null) {
            eventInformation.setTitle(eventTitle.getText().toString());
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            DatabaseReference eventsRef = databaseReference.child("events").push();
            if(firebaseUser!=null) {
                DatabaseReference userRef = databaseReference.child("users").child(firebaseUser.getUid()).child("events").push();
                userRef.setValue(eventInformation);
            }
            else {
                // TODO: 10/26/2016 sign in user right now
                UserProfile userProfile = new UserProfile(getActivity());
                firebaseUser = userProfile.signIn();
                DatabaseReference userRef = databaseReference.child("users").child(firebaseUser.getUid()).child("events").push();
                userRef.setValue(eventInformation);
            }
                eventsRef.setValue(eventInformation).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Snackbar.make(mView, "Hey you pushed me", Snackbar.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface EventSubmissionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }
}
