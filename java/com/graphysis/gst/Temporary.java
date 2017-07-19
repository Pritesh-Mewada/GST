package com.graphysis.gst;

import android.content.Intent;

import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.google.firebase.database.DataSnapshot;

public class Temporary extends AppCompatActivity {
    Button goods,services,rates;
//    ViewFlipper viewFlipper;
    Toolbar toolbar;
    public static DataSnapshot GoodsData=null,ServiceData=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temporary);

        goods = (Button)findViewById(R.id.goods_activity);
        services = (Button)findViewById(R.id.service_activity);
        rates = (Button)findViewById(R.id.see_rate);
        toolbar  = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("GST");


//        viewFlipper = (ViewFlipper)findViewById(R.id.view_main);
//        viewFlipper.startFlipping();
//        viewFlipper.setFlipInterval(3000);

        goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Temporary.this,GoodsCalculator.class);
                startActivity(intent);

            }
        });

        services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Temporary.this,ServiceCalculator.class);
                startActivity(intent);

            }
        });

        rates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Temporary.this,MainActivity.class);
                startActivity(intent);

            }
        });


//        setTransition();
//        setExitTransition();

    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option,menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int a = item.getItemId();
        switch (a){
            case R.id.developer:
                Intent intent = new Intent(Temporary.this,Developers.class);
                startActivity(intent);

                break;
        }
        return true;
    }



}

