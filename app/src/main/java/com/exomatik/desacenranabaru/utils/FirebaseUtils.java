package com.exomatik.desacenranabaru.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Iterator;

public class FirebaseUtils {
    private FirebaseDatabase firebaseDatabase;
    private StorageReference storageReference;
    private Context context;

    public FirebaseUtils(Context context) {
        this.context = context;
    }

    public void setValue(String reference, String childId, Object object, OnFailureListener onFailureListener,
                         OnCompleteListener onCompleteListener) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference(reference)
                .child(childId)
                .setValue(object)
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(onFailureListener);
    }

    public void getUrlFoto(UploadTask.TaskSnapshot taskSnapshot
            , OnSuccessListener<Uri> onSuccess
            , OnFailureListener onFailureListener){
        taskSnapshot.getStorage().getDownloadUrl()
                .addOnSuccessListener(onSuccess)
                .addOnFailureListener(onFailureListener);
    }

    public void setFoto(String id, Uri image, String child
            , OnSuccessListener<UploadTask.TaskSnapshot> onSuccessListener
            , OnFailureListener onFailureListener) {
        storageReference = FirebaseStorage.getInstance().getReference();
        final String file = id + "_" + image.getLastPathSegment();
        storageReference.child(child + "/" + file).putFile(image).addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    public void getAllValue(String reference, ValueEventListener valueEventListener) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference(reference)
                .addListenerForSingleValueEvent(valueEventListener);
    }
}
