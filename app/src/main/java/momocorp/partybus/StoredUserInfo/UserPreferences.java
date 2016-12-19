package momocorp.partybus.StoredUserInfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import momocorp.partybus.CustomObjects.UserValues;

/**
 * Created by Pablo on 10/21/2016.
 */

// TODO: 12/17/2016 please get rid of this. no need to store user email and password. 
public class UserPreferences {
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    Context mContext;
    public static String UP = "User Preferences";
    public static String KSI = "Keep Signed In";
    public static String FIRST_TIME = "first time";
    private SharedPreferences userPref;
    public UserPreferences(Context context){
        this.mContext = context;
        userPref = context.getSharedPreferences(UP, Context.MODE_PRIVATE);

    }
    //access from get first time

    //check if user opens app for first time
    public boolean isFirstTime(){

        if(userPref.getBoolean(FIRST_TIME, true)) {
            putFirstTime();
            return true;
        }

        return false;

    }

    public void setSignedIn(boolean signedIn){
        SharedPreferences.Editor editUserPref = userPref.edit();
            editUserPref.putBoolean(KSI, signedIn).apply();


    }

    public boolean getSignedIn(){
        return userPref.getBoolean(KSI, false);
    }

    private void putFirstTime(){
        //check if user opens app for first time
        SharedPreferences.Editor edit = userPref.edit();
        edit.putBoolean(FIRST_TIME, false).apply();

    }


    // TODO: 10/21/2016 too abstract. defeats purpose of java

}
