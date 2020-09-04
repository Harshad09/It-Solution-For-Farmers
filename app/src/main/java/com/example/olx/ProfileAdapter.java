package com.example.olx;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import java.util.List;


public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {
    private Context mContext;
    private List<ImageProfileModel> mUploads;
    //private List<String> names;

    public ProfileAdapter(Context context, List<ImageProfileModel> uploads) {
        mContext = context;
        mUploads = uploads;
    }


    @Override
    public ProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.profile_item, parent, false);
        return new ProfileViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProfileViewHolder holder, int position) {
        ImageProfileModel uploadCurrent = mUploads.get(position);
        holder.textViewName.setText("Aadhar : "+uploadCurrent.getmAadhar());
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

    public class ProfileViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public ImageView imageView;

        public ProfileViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.image_view_upload);
        }
    }
}