package com.example.olx;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;
    //private List<String> names;

    public ImageAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }


    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);
        holder.textViewPrice.setText("₹ "+uploadCurrent.getmPrice());
        holder.textViewName.setText(uploadCurrent.getmName());
        holder.textViewCategory.setText("Address : "+uploadCurrent.getmCategory());
        holder.textViewMobile.setText("Mobile : "+uploadCurrent.getmContact());
        Picasso.get()
                .load(uploadCurrent.getmImageUrl())
                .placeholder(R.mipmap.loading)
                .into(holder.imageView);
        System.out.println(uploadCurrent.getmImageUrl());
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewPrice;
        public TextView textViewCategory;
        public TextView textViewMobile;
        public ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);

            textViewPrice = itemView.findViewById(R.id.text_view_price);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewCategory = itemView.findViewById(R.id.text_view_category);
            textViewMobile = itemView.findViewById(R.id.text_view_mobile);
            imageView = itemView.findViewById(R.id.image_view_upload);
        }
    }
}