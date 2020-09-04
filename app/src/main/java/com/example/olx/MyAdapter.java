package com.example.olx;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;



public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context sContext;
    private List<MyUpload> sUploads;
    private AdapterView.OnItemClickListener mListener;

    public MyAdapter(Context context, List<MyUpload> suploads) {
        sContext = context;
        sUploads = suploads;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(sContext).inflate(R.layout.my_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyUpload uploadCurrent = sUploads.get(position);
        holder.textViewPrice.setText("₹ "+uploadCurrent.getmPrice());
        holder.textViewName.setText(uploadCurrent.getmName());
        holder.textViewCategory.setText(uploadCurrent.getmCategory());
        holder.textViewMobile.setText(uploadCurrent.getmContact());
        Picasso.get()
                .load(uploadCurrent.getmImageUrl())
                .placeholder(R.mipmap.loading)
                .into(holder.imageView);
        System.out.println(uploadCurrent.getmImageUrl());
    }

    @Override
    public int getItemCount() {
        return sUploads.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewPrice;
        public TextView textViewCategory;
        public TextView textViewMobile;
        public ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);

            textViewPrice = itemView.findViewById(R.id.text_view_price);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewCategory = itemView.findViewById(R.id.text_view_category);
            textViewMobile = itemView.findViewById(R.id.text_view_mobile);
            imageView = itemView.findViewById(R.id.image_view_upload);
        }
    }
}