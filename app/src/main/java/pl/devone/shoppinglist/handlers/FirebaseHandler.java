package pl.devone.shoppinglist.handlers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ljedrzynski on 09.01.2018.
 */

public class FirebaseHandler {

    public static DatabaseReference getRef(){
        return FirebaseDatabase.getInstance().getReference();
    }

    public static DatabaseReference getRef(String refName) {
        return FirebaseDatabase.getInstance().getReference(refName);
    }
}
