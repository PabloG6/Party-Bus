package momocorp.partybus.FirebaseListeners;

import android.support.annotation.NonNull;

import com.google.android.gms.common.data.DataBufferObserver;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Pablo on 10/20/2016.
 */
public class FirebaseImplementation {

     DatabaseReference  database = FirebaseDatabase.getInstance().getReference();

    public FirebaseImplementation() {
    }


    public void pushData(Object value, String child){
        DatabaseReference newReference = database.child(child).push();
        newReference.setValue(value);

    }
}
