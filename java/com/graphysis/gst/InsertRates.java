package com.graphysis.gst;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InsertRates extends AppCompatActivity {
    EditText name,rate,description;
    DatabaseReference goods,services;
    Spinner spinner ;
    Button insert;
    ProgressDialog progressDialog;
    int selected;
    String [] data ={"Good","Service"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_rates);
        name =(EditText)findViewById(R.id.editText2);
        rate = (EditText)findViewById(R.id.editText3);
        description = (EditText)findViewById(R.id.editText4);
        spinner =  (Spinner)findViewById(R.id.spinner);
        insert = (Button)findViewById(R.id.button4);
        spinner.setAdapter(new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,data));
        goods = FirebaseDatabase.getInstance().getReference("Goods");
        services = FirebaseDatabase.getInstance().getReference("Services");
        progressDialog = new ProgressDialog(InsertRates.this);
        progressDialog.setTitle("Uploading");
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                GstModel temp = new GstModel();
                temp.setName(name.getText().toString());
                temp.setRate(Float.parseFloat(String.valueOf(rate.getText())));
                temp.setDescription(description.getText().toString());
                if(selected==0){
                    goods.child(temp.getName()).setValue(temp).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressDialog.dismiss();
                            name.setText("");
                            rate.setText("");
                            description.setText("");

                        }
                    });
                }else{
                    services.child(temp.getName()).setValue(temp).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressDialog.dismiss();
                            name.setText("");
                            rate.setText("");
                            description.setText("");
                        }
                    });
                }
            }
        });



    }
}
