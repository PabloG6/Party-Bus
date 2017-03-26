package momocorp.partybus.DataStructures;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import momocorp.partybus.StoredUserInfo.UserPreferences;

/**
 * Created by Pablo on 10/20/2016.
 */
public class UserProfile {
    public FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private String userName;

    private String firstName;
    private String lastName;
    private String uID;
    Activity activity;

    public UserProfile(Activity activity) {
        this.activity = activity;
    }


    public UserProfile(String userName, String firstName,
                       String lastName, String uID) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.uID = uID;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }



    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }


    public FirebaseUser signIn(){
        SharedPreferences sharedPreferences = activity.getSharedPreferences(UserPreferences.UP, Context.MODE_PRIVATE);
        final String emailAddress = sharedPreferences.getString(UserPreferences.EMAIL, "nothing");
        final String passWord = sharedPreferences.getString(UserPreferences.PASSWORD, "nothing");
        firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()!=null){
                    uID = firebaseAuth.getCurrentUser().getUid();
                }
                else {
                    if (!emailAddress.equals("nothing")){
                        firebaseAuth.signInWithEmailAndPassword(emailAddress, passWord).
                                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                    }
                                });
                    }

                    else {
                        // TODO: 10/26/2016 decide what to do to when the stored email address was wrong
                    }

                }
            }
        });

        return firebaseAuth.getCurrentUser();

    }
}

