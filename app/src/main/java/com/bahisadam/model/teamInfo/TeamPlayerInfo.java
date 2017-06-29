package com.bahisadam.model.teamInfo;


import android.os.Parcel;
import android.os.Parcelable;

public class TeamPlayerInfo implements Parcelable{

    private String mSquadNumber;
    private String mName;
    private String mPosition;
    private int mGoals;
    private int mAssists;
    private int mCards;
    private String mCountryCode;
    private String mImageUrl;
    private String mPlayerId;
    private String mPlayerNationality;

    public String getPlayerId() {
        return mPlayerId;
    }

    public void setPlayerId(String playerId) {
        mPlayerId = playerId;
    }

    public TeamPlayerInfo() {
    }

    protected TeamPlayerInfo(Parcel in) {
        mSquadNumber = in.readString();
        mName = in.readString();
        mPosition = in.readString();
        mGoals = in.readInt();
        mAssists = in.readInt();
        mCards = in.readInt();
        mCountryCode = in.readString();
        mImageUrl = in.readString();
        mPlayerId = in.readString();
        mPlayerNationality = in.readString();
    }

    public static final Creator<TeamPlayerInfo> CREATOR = new Creator<TeamPlayerInfo>() {
        @Override
        public TeamPlayerInfo createFromParcel(Parcel in) {
            return new TeamPlayerInfo(in);
        }

        @Override
        public TeamPlayerInfo[] newArray(int size) {
            return new TeamPlayerInfo[size];
        }
    };

    public String getSquadNumber() {
        return mSquadNumber;
    }

    public void setSquadNumber(String squadNumber) {
        if(squadNumber == null) mSquadNumber = "-";
        else mSquadNumber = squadNumber;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPosition() {
        return mPosition;
    }

    public void setPosition(String position) {
        mPosition = position;
    }

    public int getGoals() {
        return mGoals;
    }

    public void setGoals(int goals) {
        mGoals = goals;
    }

    public int getAssists() {
        return mAssists;
    }

    public void setAssists(int assists) {
        mAssists = assists;
    }

    public int getCards() {
        return mGoals;
    }

    public void setCards (int cards) {
        mGoals = mCards;
    }

    public String getCountryCode() {
        return mCountryCode;
    }

    public void setCountryCode(String countryCode) {
        mCountryCode = countryCode;
    }

    public String getmPlayerNationality() {
        return mPlayerNationality;
    }

    public void setmPlayerNationality(String mPlayerNationality) {
        this.mPlayerNationality = mPlayerNationality;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mSquadNumber);
        parcel.writeString(mName);
        parcel.writeString(mPosition);
        parcel.writeInt(mGoals);
        parcel.writeInt(mAssists);
        parcel.writeInt(mCards);
        parcel.writeString(mCountryCode);
        parcel.writeString(mImageUrl);
        parcel.writeString(mPlayerId);
        parcel.writeString(mPlayerNationality);
    }
}
