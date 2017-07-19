package com.graphysis.gst;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by pritesh on 12/7/17.
 */

public class CalculateRecycleAdapter extends RecyclerView.Adapter<CalculateRecycleAdapter.ViewHolder> {
    ArrayList<Calculator> dataset;
    Dialog dialog;
    private static Context context;
    private static HashMap<String,Float> priceMap;
    private static  String []  list;
    private int type;
    Storage storage;
    CalculateRecycleAdapter(Context context,int type) {
        dataset = new ArrayList();
        this.context = context;
        storage = new Storage(context);
        this.type = type;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calculator_item_view, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;

    }

    public void  initialise_list(String [] list,HashMap<String,Float>priceMap,ArrayList<Calculator> data){
        this.list = list;
        this.priceMap = priceMap;
        this.dataset = data;

    }

    public void addData(Calculator data) {
        dataset.add(data);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        Calculator temp = dataset.get(position);
        holder.name.setText(temp.getItem());

        holder.rate.setText(priceMap.get(temp.getItem())+"%");

        holder.price.setText("Each: "+String.valueOf(temp.getCostprice()));
        holder.quantity.setText("No: "+String.valueOf(temp.getQuantity()));
        float costprice = Float.parseFloat(temp.getCostprice())*Integer.parseInt(temp.getQuantity());
        float tax = (Float.parseFloat(temp.getCostprice())*Integer.parseInt(temp.getQuantity())*Float.parseFloat(String.valueOf(priceMap.get(temp.getItem()))))/100;
        holder.total.setText(String.valueOf("Cost: "+costprice+" Tax: "+tax +" Total: "+(costprice+tax)));

        holder.delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(type ==0){
                    storage.delete(dataset.get(position).getId(),Tables.goodStore);
                }else{
                    storage.delete(dataset.get(position).getId(),Tables.serviceStore);
                }
                dataset.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initialiseDialogue(dataset.get(position) , position);

            }
        });

    }
    @Override
    public int getItemCount() {
        return dataset.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, rate, price, quantity, total,edit,delete;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.item_name);
            rate=(TextView) itemView.findViewById(R.id.item_rate);
            price=(TextView) itemView.findViewById(R.id.item_price);
            quantity=(TextView) itemView.findViewById(R.id.item_quantity);
            total=(TextView) itemView.findViewById(R.id.item_total);
            edit=(TextView) itemView.findViewById(R.id.edit_data);
            delete=(TextView) itemView.findViewById(R.id.delete);
        }
    }

    public void initialiseDialogue(Calculator calculator, final int position){
        final EditText itemPrice,itemQuantity;
        final AutoCompleteTextView itemSeek;
        final TextView textView;
        Button addItem,closeDialogue;

        dialog = new Dialog(CalculateRecycleAdapter.context);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.insert_dialogue);
        dialog.show();

        itemPrice = (EditText)dialog.findViewById(R.id.item_price);
        itemQuantity =(EditText)dialog.findViewById(R.id.item_quantity);
        addItem = (Button)dialog.findViewById(R.id.insert_item);
        textView = (TextView)dialog.findViewById(R.id.dialogue_text);
        textView.setText("Edit Item");
        closeDialogue =(Button)dialog.findViewById(R.id.close_dialogue);
        itemSeek = (AutoCompleteTextView)dialog.findViewById(R.id.item_seek);

        itemPrice.setText(calculator.getCostprice());
        itemQuantity.setText(calculator.getQuantity());
        itemSeek.setText(calculator.getItem());

        itemSeek.setAdapter(new ArrayAdapter(dialog.getContext(),android.R.layout.simple_list_item_1,list));
        itemSeek.setThreshold(1);

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemPrice.getText().toString().equals("") || itemQuantity.getText().toString().equals("") || itemSeek.getText().toString().equals("") ){
                    Toast.makeText(CalculateRecycleAdapter.context,"Please fill all",Toast.LENGTH_SHORT);
                    return;
                }

                float price=Float.parseFloat(itemPrice.getText().toString()),rate=priceMap.get(itemSeek.getText().toString());
                int quantity=Integer.parseInt(itemQuantity.getText().toString());

                Calculator calculator = new Calculator();
                calculator.setCostprice(String.valueOf(price));
                calculator.setItem(itemSeek.getText().toString());
                calculator.setQuantity(String.valueOf(quantity));
                calculator.setId(UUID.randomUUID().toString().replace('-','a'));

                itemPrice.setText("");
                itemQuantity.setText("");
                itemSeek.setText("");
                dialog.cancel();

                if(type ==0){
                    storage.delete(dataset.get(position).getId().toString(),Tables.goodStore);
                    storage.Insert(calculator,Tables.goodStore);

                }else{
                    storage.delete(dataset.get(position).getId().toString(),Tables.serviceStore);
                    storage.Insert(calculator,Tables.serviceStore);

                }
                dataset.remove(position);
                dataset.add(position,calculator);
                notifyDataSetChanged();
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