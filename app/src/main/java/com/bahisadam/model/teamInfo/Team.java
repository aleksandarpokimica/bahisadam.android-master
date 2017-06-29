package com.bahisadam.model.teamInfo;


import android.os.Parcel;
import android.os.Parcelable;

public class Team implements Parcelable{

    private int mId;
    private String mName;
    private String mNameEng;
    private String mLogoPath;
    private String mManager;
    private String mManagerNationality;

    public Team() {
    }

    protected Team(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mNameEng = in.readString();
        mLogoPath = in.readString();
        mManager = in.readString();
        mManagerNationality = in.readString();
    }

    public static final Creator<Team> CREATOR = new Creator<Team>() {
        @Override
        public Team createFromParcel(Parcel in) {
            return new Team(in);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getNameEng() {
        return mNameEng;
    }

    public void setNameEng(String mNameEng) {
        this.mNameEng = mNameEng;
    }

    public String getLogoPath() {
        return mLogoPath;
    }

    public void setLogoPath(String logoPath) {
        mLogoPath = logoPath;
    }

    public String getManager() {
        return mManager;
    }

    public void setManager(String mManager) {
        this.mManager = mManager;
    }

    public String getManagerNationality() {
        return mManagerNationality;
    }

    public void setManagerNationality(String mManagerNationality) {
        this.mManagerNationality = mManagerNationality;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mName);
        parcel.writeString(mNameEng);
        parcel.writeString(mLogoPath);
        parcel.writeString(mManager);
        parcel.writeString(mManagerNationality);
    }
}
