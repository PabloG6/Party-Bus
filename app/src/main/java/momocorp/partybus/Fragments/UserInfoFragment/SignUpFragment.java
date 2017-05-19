package momocorp.partybus.Fragments.UserInfoFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import momocorp.partybus.DataStructures.UserProfile;
import momocorp.partybus.FirebaseListeners.FirebaseImplementation;
import momocorp.partybus.R;
import momocorp.partybus.StoredUserInfo.UserPreferences;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignUpFragmentListener} interface
 * to handle interaction events.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SignUpFragmentListener mListener;
    Button signUpButton;
    TextInputEditText firstName;
    TextInputEditText lastName;
    TextInputEditText userName;

    CheckBox keepMeSignedUp;

    public static FirebaseAuth firebaseAuth;

    TextInputEditText password;
    TextInputEditText emailAddress;
    Context context;

    TextWatcher inputWatcher;
    View mainView;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
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

        firebaseAuth = FirebaseAuth.getInstance();
        mListener = (SignUpFragmentListener) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mainView = inflater.inflate(R.layout.fragment_signup, container, false);
        firstName = (TextInputEditText) mainView.findViewById(R.id.input_first_name);
        lastName = (TextInputEditText) mainView.findViewById(R.id.input_last_name);
        userName = (TextInputEditText) mainView.findViewById(R.id.input_user_name);
        password = (TextInputEditText) mainView.findViewById(R.id.input_password);
        context = getActivity();
        keepMeSignedUp = (CheckBox) mainView.findViewById(R.id.keep_me_signed_up);

        emailAddress = (TextInputEditText) mainView.findViewById(R.id.input_user_email);

        //sign up instantiation
        signUpButton = (Button) mainView.findViewById(R.id.sign_up_button);


        //instantiate input watcher
        inputWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.length() < 6){
                    signUpButton.setEnabled(false);
                } else {
                    signUpButton.setEnabled(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0){
                    signUpButton.setEnabled(false);
                } else {
                    signUpButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        firstName.addTextChangedListener(inputWatcher);
        lastName.addTextChangedListener(inputWatcher);

        userName.addTextChangedListener(inputWatcher);
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() < 6){
                    signUpButton.setEnabled(false);
                } else {
                    signUpButton.setEnabled(true);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        emailAddress.addTextChangedListener(inputWatcher);



        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.getText().toString().length() < 6) {
                    password.setError("Password must be greater than 6 characters");
                } else {

                    getAnswers();
                }

                //// TODO: 9/15/2016 create slide in transition

            }
        });


        keepMeSignedUp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences.Editor editor =  context.
                        getSharedPreferences(UserPreferences.UP, Context.MODE_PRIVATE).edit();
                editor.putBoolean(UserPreferences.KSI, b);
                editor.apply();


            }
        });


        return mainView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SignUpFragmentListener) {
            mListener = (SignUpFragmentListener) context;
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

    @Override
    public void onStart() {
        super.onStart();
    }

    void getAnswers() {

        final String firstName = this.firstName.getText().toString();
        final String lastName = this.lastName.getText().toString();

        final String emailAddress = this.emailAddress.getText().toString();
        final String password = this.password.getText().toString();

        final String userName = this.userName.getText().toString();


                firebaseAuth.createUserWithEmailAndPassword(emailAddress, password).
                        addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                signUpButton.setEnabled(false);
                                if (task.isSuccessful()) {

                                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                    assert firebaseUser != null;
                                    String UID = firebaseUser.getUid();


                                    final UserProfile userProfile = new UserProfile(userName, firstName, lastName, UID);
                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                    databaseReference.child("users").child(UID).setValue(userProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            SharedPreferences.Editor editor = context.getSharedPreferences(UserPreferences.UP, Context.MODE_PRIVATE).edit();
                                            editor.putBoolean(UserPreferences.FIRST_TIME, false);
                                            editor.apply();
                                            //ask for information about completion before destroying the fragment
                                            if (task.getResult() != null)
                                                Log.i("Task completed", "result: " + task.getResult().toString());
                                                mListener.triggerEventsActivity();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.i("Task failed", "result: " + e.getMessage());
                                        }
                                    });
                                    Snackbar.make(mainView, "Successful creation", Snackbar.LENGTH_LONG).show();

                                    //// TODO: 10/21/2016 change fragment to interest fragment

                                    //start interest fragment;

                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof FirebaseAuthUserCollisionException) {
                            if (mainView != null) {
                                Snackbar.make(mainView, "A user exists who already has that account", Snackbar.LENGTH_SHORT).show();

                            }
                        }
                    }
                });

        }

    public interface SignUpFragmentListener {
        // TODO: Update argument type and name
        void triggerEventsActivity();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("life cycle", "on destroy called");
    }


}
