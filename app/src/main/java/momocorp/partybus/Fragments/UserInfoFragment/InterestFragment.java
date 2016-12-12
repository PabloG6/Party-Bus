package momocorp.partybus.Fragments.UserInfoFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import momocorp.partybus.Adapters.InterestAdapter;
import momocorp.partybus.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InterestFragmentListener} interface
 * to handle interaction events.
 * Use the {@link InterestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InterestFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button interestButton;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    //get authentication information

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private InterestFragmentListener mListener;
    View layout;

    public InterestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InterestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InterestFragment newInstance(String param1, String param2) {
        InterestFragment fragment = new InterestFragment();
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
        mListener = (InterestFragmentListener) getActivity();
        layout = inflater.inflate(R.layout.fragment_interest, container, false);
        // TODO: 10/21/2016 get values from interest activity.
        RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.interest_list);
        interestButton = (Button) layout.findViewById(R.id.interest_next);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new InterestAdapter(getActivity()));

        interestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user!=null) {
                    DatabaseReference newReference = databaseReference.child(user.getUid()).child("interests");

                    // TODO: 10/21/2016 add interests to activity
                    mListener.startNew();
                }

            }
        });
        //// TODO: 10/21/2016 store data about interests

        return layout;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.startNew();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InterestFragmentListener) {
            mListener = (InterestFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement InterestFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface InterestFragmentListener {
        // TODO: Update argument type and name
        void startNew();
    }
}
