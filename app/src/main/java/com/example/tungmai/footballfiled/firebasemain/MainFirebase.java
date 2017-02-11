package com.example.tungmai.footballfiled.firebasemain;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.snapshot.IndexedNode;
import com.firebase.client.snapshot.Node;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by tungmai on 11/01/2017.
 */

public class MainFirebase {
    private Context context;
    private Firebase firebase;
    private static final String TAG = "MainFirebase";

    public MainFirebase(Context context) {
        this.context = context;
        Firebase.setAndroidContext(context);
        firebase = new Firebase("https://footballfirebase.firebaseio.com/");
    }

    public void createRegister(final String idUser, String name, int typeUser, Uri uri, final ProgressDialog dialog) {
        final Map<String, String> mh = new HashMap<>();
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("user");

        mh.put("nameUser", name);
        mh.put("typerUser", typeUser + "");
//        firebase.child("user").setValue(idUser);
        if (uri != null) {

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReferenceFromUrl("gs://footballfirebase.appspot.com");

            StorageReference riversRef = storageRef.child("imagesUser/" + idUser);
            UploadTask uploadTask = riversRef.putFile(uri);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri uri1 = taskSnapshot.getDownloadUrl();
                    String urlImage = uri1.toString();
                    Log.e(TAG, urlImage);
                    mh.put("imageUser", urlImage);

                    firebase.child("user/" + idUser).setValue(mh);
                    dialog.dismiss();
                }
            });
        } else {
            mh.put("imageUser", "");
            firebase.child("user/" + idUser).setValue(mh);
        }

//
    }

    public void sendMessage(String idSender,String nameSender,String urlImageSender, String idRecipient, String nameRecipient, String urlImageRecipient, String contentMessage, String time) {
        final Map<String, String> mh = new HashMap<>();
//        mh.put("idRecipient", idRecipient);
        mh.put("nameRecipient", nameRecipient);
//        mh.put("urlImageRecipient", urlImageRecipient);
        mh.put("contentMessage", contentMessage);
        mh.put("mRecipientOrSenderStatus", 0 + "");
        mh.put("time", time);

        final Map<String, String> mh1 = new HashMap<>();
//        mh1.put("idSender", idRecipient);
        mh1.put("nameSender", nameSender);
//        mh1.put("urlImageSender", urlImageSender);
        mh1.put("contentMessage", contentMessage);
        mh1.put("mRecipientOrSenderStatus", 1 + "");
        mh1.put("time", time);

        Map<String, String> mhIntroduction = new HashMap<>();
        mhIntroduction.put("name", nameRecipient);
        mhIntroduction.put("time", time);
        mhIntroduction.put("content", contentMessage);
        mhIntroduction.put("urlImage", urlImageRecipient);

        Map<String, String> mhIntroduction1 = new HashMap<>();
        mhIntroduction1.put("name", nameSender);
        mhIntroduction1.put("time", time);
        mhIntroduction1.put("content", contentMessage);
        mhIntroduction1.put("urlImage", urlImageSender);

        Firebase writeSender = firebase.child("user/" + idSender + "/messagers/" + idRecipient + "/contentMessager").push();
        String idWrite = writeSender.getKey();
        Firebase writeRecipient = firebase.child("user/" + idRecipient + "/messagers/" + idSender + "/contentMessager/" + idWrite);
        writeSender.setValue(mh);
        writeRecipient.setValue(mh1);
        Firebase writeIntro=firebase.child("user/" + idSender + "/messagers/" + idRecipient + "/introduction/");
        Firebase writeIntro1=firebase.child("user/" + idRecipient + "/messagers/" + idSender + "/introduction/");
        writeIntro.setValue(mhIntroduction);
        writeIntro1.setValue(mhIntroduction1);
    }

    public void writeNoti(String idUser, int typeWrite, String title, String phone, String nameGroup, String address, String describe, String time, String date, int edit, final ArrayList<Uri> arrUri, final ProgressDialog progressDialog) {
        final Map<String, String> mh = new HashMap<>();
        mh.put("time", time + "," + date);
        mh.put("idUser", idUser);
        mh.put("typeWrite", typeWrite + "");
        mh.put("title", title);
        mh.put("phone", phone);
        mh.put("nameGroup", nameGroup);
        mh.put("address", address);
        mh.put("describe", describe);
        //        mh.put("images",)

        final Firebase write = firebase.child("writen").push();
        String idWrite = write.getKey();
//        final Map<String, ArrayList<String>> mhUrl = new HashMap<>();
//        final ArrayList<String> arrUrl = new ArrayList<>();
        final StringBuilder strArrUrl = new StringBuilder();
        if (arrUri.size() > 0) {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReferenceFromUrl("gs://footballfirebase.appspot.com");
            for (int i = 0; i < arrUri.size(); i++) {
                StorageReference riversRef = storageRef.child("images/" + idWrite + "/" + arrUri.get(i).getLastPathSegment());
                UploadTask uploadTask = riversRef.putFile(arrUri.get(i));

//            // Register observers to listen for when the download is done or if it fails
                final int finalI = i;
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
//                        Log.e(TAG, downloadUrl.toString());
//                        arrUrl.add(downloadUrl.toString());
                        strArrUrl.append(downloadUrl.toString() + ";");
//                        Log.e(TAG, strArrUrl.toString());
                        if (finalI == arrUri.size() - 1) {
                            mh.put("images", strArrUrl.toString());
                            write.setValue(mh);
                            progressDialog.dismiss();
                        }
                    }
                });
            }
        } else {
            mh.put("images", "");
            write.setValue(mh);
        }
//        mhUrl.put("edit",edit+"");
//


        firebase.child("user/" + idUser + "/written/" + idWrite).setValue(edit);

    }


}
