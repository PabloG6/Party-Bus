package momocorp.partybus.Activities;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import momocorp.partybus.Activities.EventsActivity;
import momocorp.partybus.Fragments.UserInfoFragment.InterestFragment;
import momocorp.partybus.Fragments.UserInfoFragment.LoginFragment;
import momocorp.partybus.Fragments.UserInfoFragment.SignUpFragment;
import momocorp.partybus.R;
import momocorp.partybus.StoredUserInfo.UserPreferences;


public class LoginActivity extends AppCompatActivity implements
        SignUpFragment.SignUpFragmentListener, LoginFragment.LoginFragmentListener,
        InterestFragment.InterestFragmentListener {

    FirebaseAuth mAuth;
    Context context;
    View layout;

    // TODO: 12/11/2016 check login and sign up criteria and change shared preferences to not save password
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        FirebaseApp.initializeApp(context);
        SharedPreferences sharedPreferences = getSharedPreferences(UserPreferences.UP, Context.MODE_PRIVATE);
        boolean isFirst = sharedPreferences.getBoolean(UserPreferences.FIRST_TIME, true);
        mAuth = FirebaseAuth.getInstance();
        //if user is using app for first time call sign up
        layout = findViewById(R.id.login_container);
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser==null) {
            if(isFirst) {
                Fragment signUpFragment = new SignUpFragment();

                getFragmentManager().beginTransaction().add(R.id.login_container, signUpFragment,
                        getResources().getString(R.string.sign_up_fragment))
                        .addToBackStack(getResources().getString(R.string.sign_up_fragment))
                        .commit();

            }
             else {
                Fragment loginFragment = new LoginFragment();
                getFragmentManager().beginTransaction().add(R.id.login_container, loginFragment,
                        getResources().getString(R.string.sign_up_fragment))
                        .addToBackStack(getResources().getString(R.string.sign_up_fragment))
                        .commit();
            }

        } else {

           {
                Intent intent = new Intent(context, EventsActivity.class);
                startActivity(intent);
            }
        }

    }


    @Override
    public void triggerInterestFragment() {
        //replace fragment
        Fragment interestFragment = new InterestFragment();
        String interestString = getResources().getString(R.string.interest_fragment);
        getFragmentManager().beginTransaction().replace(R.id.login_container, interestFragment,
                interestString).addToBackStack(interestString).commit();
    }

    @Override
    public void logInFragmentInteraction() {

        Intent intent = new Intent(context, EventsActivity.class);
        startActivity(intent);


    }

    @Override
    public void startNew() {
        //start the events activity
        Intent intent = new Intent(this, EventsActivity.class);
        startActivity(intent);


    }
}