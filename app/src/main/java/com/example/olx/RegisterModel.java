package com.example.olx;

public class RegisterModel
{
    private String mAadhar;
    private String mName;
    private String mMobile;
    private String mAddress;
    private String mCategory;
    private String mPassword;

    public RegisterModel(){}

    public RegisterModel(String aadhar,String name, String mobile, String address, String category, String password){
        mAadhar = aadhar;
        mName = name;
        mMobile = mobile;
        mAddress = address;
        mCategory = category;
        mPassword = password;
    }

    public String getmAadhar() {
        return mAadhar;
    }

    public void setmAadhar(String mAadhar) {
        this.mAadhar = mAadhar;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmMobile() {
        return mMobile;
    }

    public void setmMobile(String mMobile) {
        this.mMobile = mMobile;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmCategory() {
        return mCategory;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }
}
