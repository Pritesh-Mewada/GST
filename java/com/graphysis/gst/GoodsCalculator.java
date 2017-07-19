package com.graphysis.gst;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class GoodsCalculator extends AppCompatActivity {
    RecyclerView recyclerViewCalculator;
    CalculateRecycleAdapter calculateRecycleAdapter;
    Button openDialogue,addItem,closeDialogue;
    String [] list={};
    EditText itemPrice,itemQuantity;
    TextView dialogueText;
    Toolbar toolbar;
    AutoCompleteTextView itemSeek;
    HashMap<String,Float> priceMap;
    Dialog dialog;
    Storage goods;
    ArrayList<Calculator> dataset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_calculator);
        recyclerViewCalculator = (RecyclerView)findViewById(R.id.goods_calculate);
        openDialogue = (Button)findViewById(R.id.insert_good);
        dialog = new Dialog(GoodsCalculator.this);
        priceMap = new HashMap<String,Float>();
        goods = new Storage(GoodsCalculator.this);
        recyclerViewCalculator.setLayoutManager(new LinearLayoutManager(this));
        calculateRecycleAdapter = new CalculateRecycleAdapter(GoodsCalculator.this,0);
        recyclerViewCalculator.setAdapter(calculateRecycleAdapter);
        dataset = new ArrayList<>();

        toolbar  = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Goods Calculator");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        initialiseAdapter();

        openDialogue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                dialogueText.setText("Add Item");
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();

    }

    public void initialiseAdapter(){

        //initialise the data
        Cursor cursor = goods.readItems(Tables.goodItems);
        do{
            priceMap.put(cursor.getString(cursor.getColumnIndex("Item")),Float.parseFloat(cursor.getString(cursor.getColumnIndex("Rate"))));
        }while (cursor.moveToNext());

        cursor.moveToFirst();


        list = priceMap.keySet().toArray(new String[0]);

        //get stored data
        cursor = goods.read(Tables.goodStore);

        if(cursor.getCount()>0 && cursor !=null){
            do{
                Calculator calculator = new Calculator();
                calculator.setItem(cursor.getString(cursor.getColumnIndex("Item")));
                calculator.setCostprice(cursor.getString(cursor.getColumnIndex("Price")));
                calculator.setQuantity(cursor.getString(cursor.getColumnIndex("Quantity")));
                calculator.setId(cursor.getString(cursor.getColumnIndex("Id")));
                dataset.add(calculator);

            }while(cursor.moveToNext());

        }

        cursor.moveToFirst();

        calculateRecycleAdapter.initialise_list(list, priceMap,dataset);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.insert_dialogue);
        itemPrice       = dialog.findViewById(R.id.item_price);
        itemQuantity    =  dialog.findViewById(R.id.item_quantity);
        addItem = dialog.findViewById(R.id.insert_item);
        dialogueText = (TextView)dialog.findViewById(R.id.dialogue_text);

        closeDialogue = dialog.findViewById(R.id.close_dialogue);
        itemSeek = dialog.findViewById(R.id.item_seek);
        itemSeek.setAdapter(new ArrayAdapter(dialog.getContext(),android.R.layout.simple_list_item_1,list));
        itemSeek.setThreshold(1);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemPrice.getText().toString().equals("") || itemQuantity.getText().toString().equals("") || itemSeek.getText().toString().equals("") ){
                    Toast.makeText(getApplicationContext(),"Please fill all",Toast.LENGTH_SHORT);
                    return;
                }
                float price=Float.parseFloat(itemPrice.getText().toString()),rate=priceMap.get(itemSeek.getText().toString());
                int quantity=Integer.parseInt(itemQuantity.getText().toString());
                Calculator calculator = new Calculator();
                calculator.setCostprice(String.valueOf(price));
                calculator.setItem(itemSeek.getText().toString());
                calculator.setQuantity(String.valueOf(quantity));
                calculator.setId(UUID.randomUUID().toString().replace('-','a'));

                goods.Insert(calculator,Tables.goodStore);

                calculateRecycleAdapter.addData(calculator);
                calculateRecycleAdapter.notifyDataSetChanged();
                itemPrice.setText("");
                itemQuantity.setText("");
                itemSeek.setText("");
                dialog.cancel();

            }
        });

        closeDialogue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

}

