package com.graphysis.gst;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreen extends AppCompatActivity {
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    Storage storage;
    public static boolean good = false,service = false;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        storage = new Storage(SplashScreen.this);
        storage.initialiseItems(Tables.goodItems);
        storage.initialiseItems(Tables.serviceItem);
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog = new ProgressDialog(SplashScreen.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching data..");
        progressDialog.setTitle("Please wait");

        if(check_internet() && ! (storage.readItems(Tables.goodItems).getCount()==0 || storage.readItems(Tables.serviceItem).getCount()==0 )){
            updateDatabase(false);
            setTimer();
        }else if(!check_internet() && (storage.readItems(Tables.goodItems).getCount()==0 || storage.readItems(Tables.serviceItem).getCount()==0 )){
            showDialog();
        }else if(check_internet() && (storage.readItems(Tables.goodItems).getCount()==0 || storage.readItems(Tables.serviceItem).getCount()==0 )){
            progressDialog.show();
            updateDatabase(true);
        }else{
            setTimer();
        }

    }


    public  void updateDatabase(final boolean firstime){
        databaseReference = FirebaseDatabase.getInstance().getReference("Goods");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(progressDialog.isShowing()){
                    progressDialog.cancel();
                }
                storage.reCreateItems(Tables.goodItems);

                for(DataSnapshot children : dataSnapshot.getChildren()){
                    GstModel temp = new GstModel();
                    temp.setName(children.getKey());
                    for(DataSnapshot innerItems : children.getChildren()){
                        String name = innerItems.getKey();
                        switch (name){
                            case "description":
                                temp.setDescription(innerItems.getValue().toString());
                                break;
                            case "rate":
                                temp.setRate(Float.parseFloat(innerItems.getValue().toString()));
                                break;
                        }
                    }
                    storage.insertItem(Tables.goodItems,temp);
                }
                storage.initialiseItems(Tables.goodItems);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                if(progressDialog.isShowing()){
                    progressDialog.cancel();
                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                }
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("Services");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(progressDialog.isShowing()){
                    progressDialog.cancel();
                }
                storage.reCreateItems(Tables.serviceItem);

                for(DataSnapshot children : dataSnapshot.getChildren()){
                    GstModel temp = new GstModel();
                    temp.setName(children.getKey());
                    for(DataSnapshot innerItems : children.getChildren()){
                        String name = innerItems.getKey();
                        switch (name){
                            case "description":
                                temp.setDescription(innerItems.getValue().toString());
                                break;
                            case "rate":
                                temp.setRate(Float.parseFloat(innerItems.getValue().toString()));
                                break;
                        }
                    }
                    storage.insertItem(Tables.serviceItem,temp);
                }

                storage.initialiseItems(Tables.serviceItem);
                if(firstime == true){
                    changeActivity();
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                if(progressDialog.isShowing()){
                    progressDialog.cancel();
                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean check_internet(){
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

    private void showDialog()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This app require internet")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        startActivity(new Intent(Settings.ACTION_SETTINGS));

                    }
                })
                .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void setTimer(){
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                changeActivity();
                // close this activity
                finish();

            }
        }, 3000);
    }
    public void changeActivity(){
        Intent change = new Intent(SplashScreen.this,Temporary.class);
        startActivity(change);

    }


}
