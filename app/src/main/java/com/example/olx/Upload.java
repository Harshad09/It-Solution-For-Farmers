package com.example.olx;


public class Upload {
    private String mName;
    private String mPrice;
    private String mCategory;
    private String mContact;
    private String mAadhar;
    private String mImageUrl;
    public Upload() {
        //empty constructor needed
    }
    public Upload(String name,String price, String category,String contact, String aadhar,  String imageUrl) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        mName = name;
        mPrice = price;
        mCategory = category;
        mContact = contact;
        mAadhar = aadhar;
        mImageUrl = imageUrl;
    }

    public String getmAadhar() {
        return mAadhar;
    }

    public void setmAadhar(String mAadhar) {
        this.mAadhar = mAadhar;
    }

    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public String getmCategory() {
        return mCategory;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public String getmContact() {
        return mContact;
    }

    public void setmContact(String mContact) {
        this.mContact = mContact;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}
