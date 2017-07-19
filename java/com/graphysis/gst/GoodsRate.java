package com.graphysis.gst;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by pritesh on 11/7/17.
 */

public class GoodsRate extends Fragment {
    RecyclerView goodsRecycle;
    RecyclerAdapter recyclerAdapter;
    ArrayList<GstModel> dataset;
    EditText search;
    Storage storage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.good_rates,container,false);
        goodsRecycle = view.findViewById(R.id.goods_recycle);
        goodsRecycle.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerAdapter = new RecyclerAdapter(getContext());
        goodsRecycle.setAdapter(recyclerAdapter);
        search =(EditText)view.findViewById(R.id.search_text);
        storage = new Storage(getContext());
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String data = editable.toString().toLowerCase();
                ArrayList<GstModel> temp = new ArrayList<>();
                for(GstModel item : dataset){
                    if(item.getName().toLowerCase().contains(data) || item.getDescription().toLowerCase().contains(data)){
                        temp.add(item);
                    }
                }
                recyclerAdapter.initialise(temp);
                recyclerAdapter.notifyDataSetChanged();

            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        dataset = new ArrayList<GstModel>();

        Cursor cursor = Storage.goodItem;

        do{
            GstModel temp = new GstModel();
            temp.setName(String.valueOf(cursor.getString(cursor.getColumnIndex("Item"))));
            temp.setDescription(String.valueOf(cursor.getString(cursor.getColumnIndex("Description"))));
            temp.setRate(Float.parseFloat(cursor.getString(cursor.getColumnIndex("Rate"))));
            dataset.add(temp);
        }while (cursor.moveToNext());
        cursor.moveToFirst();

        recyclerAdapter.initialise(dataset);
        recyclerAdapter.notifyDataSetChanged();

    }

}
