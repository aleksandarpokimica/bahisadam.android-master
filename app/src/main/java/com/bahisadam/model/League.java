package com.bahisadam.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ali on 6/10/17.
 */
public class League implements Parcelable {

    private int id;
    private String league_name_tr;

    public League() {
    }

    protected League(Parcel in) {
        id = in.readInt();
        league_name_tr = in.readString();
    }

    public static final Parcelable.Creator<League> CREATOR = new Parcelable.Creator<League>() {
        @Override
        public League createFromParcel(Parcel in) {
            return new League(in);
        }

        @Override
        public League[] newArray(int size) {
            return new League[size];
        }
    };

    public int getId() {
        return id;
    }

    public void set_id(int id) {
        this.id = id;
    }

    public String getLeague_name_tr() {
        return league_name_tr;
    }

    public void setLeague_name_tr(String league_name_tr) {
        this.league_name_tr = league_name_tr;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(league_name_tr);
    }

}
