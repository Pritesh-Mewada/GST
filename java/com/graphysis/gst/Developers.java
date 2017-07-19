package com.graphysis.gst;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Developers extends AppCompatActivity {
    ImageView pritesh_call,pritesh_mail,pritesh_linked;
    ImageView kush_call,kush_mail,kush_linked;
    ImageView pavan_call,pavan_mail,pavan_instagram;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers);

        pritesh_call = (ImageView) findViewById(R.id.pritesh_call);
        pritesh_mail = (ImageView) findViewById(R.id.pritesh_email);
        pritesh_linked = (ImageView) findViewById(R.id.pritesh_linked);

        kush_call =(ImageView) findViewById(R.id.kush_call);
        kush_mail =(ImageView) findViewById(R.id.kush_email);
        kush_linked =(ImageView) findViewById(R.id.kush_linked);

        pavan_call = (ImageView) findViewById(R.id.pavan_call);
        pavan_mail = (ImageView) findViewById(R.id.pavan_email);
        pavan_instagram = (ImageView) findViewById(R.id.pavan_instagram);
        pavan_instagram.bringToFront();
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Developers");

        pritesh_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:9172977934"));
                startActivity(i);
            }
        });

        pritesh_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);

                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{ "mewadapritesh5@gmail.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "About GST APP");
                startActivity(Intent.createChooser(emailIntent,"Sending mail through..."));
            }
        });

        pritesh_linked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.linkedin.com/in/pritesh-mewada/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        kush_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:9892379125"));
                startActivity(i);
            }
        });

        kush_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);

                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{ "kushgadhvi@gmail.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "About GST APP");
                startActivity(Intent.createChooser(emailIntent,"Sending mail through..."));
            }
        });

        kush_linked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.linkedin.com/in/kush-gadhvi-4532b8105/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        pavan_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:8097299525"));
                startActivity(i);
            }
        });

        pavan_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{ "pavansuthar90@gmail.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "About GST APP");
                startActivity(Intent.createChooser(emailIntent,"Sending mail through..."));
            }
        });

        pavan_instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.instagram.com/invisible.pavan/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();

    }
}
