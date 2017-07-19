package com.graphysis.gst;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by pritesh on 11/7/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    ArrayList<GstModel> data ;
    Context context;

    public RecyclerAdapter(Context context){
        this.context = context;
        this.data = new ArrayList<GstModel>();
    }
    public void initialise(ArrayList<GstModel> data){
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rate_card,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.item.setText(data.get(position).getName());
        holder.itemRate.setText(String.valueOf(data.get(position).getRate())+"%");
        holder.itemDescription.setText(String.valueOf(data.get(position).getDescription()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView item,itemRate,itemDescription;
        public ViewHolder(View itemView) {
            super(itemView);
            item =(TextView) itemView.findViewById(R.id.item);
            itemRate =(TextView) itemView.findViewById(R.id.item_rate);
            itemDescription =(TextView) itemView.findViewById(R.id.item_description);
        }
    }
}
