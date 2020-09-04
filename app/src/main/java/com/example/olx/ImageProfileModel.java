package com.example.olx;


public class ImageProfileModel {

    private String mAadhar;
    private String mImageUrl;
    public ImageProfileModel() {
        //empty constructor needed
    }
    public ImageProfileModel(String aadhar,  String imageUrl) {

        mAadhar = aadhar;
        mImageUrl = imageUrl;
    }

    public String getmAadhar() {
        return mAadhar;
    }

    public void setmAadhar(String mAadhar) {
        this.mAadhar = mAadhar;
    }


    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}
