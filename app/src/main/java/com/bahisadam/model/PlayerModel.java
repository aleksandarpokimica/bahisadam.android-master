package com.bahisadam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by fatih on 16.04.2017.
 */
public class PlayerModel {
    @SerializedName("_id")
    @Expose
    public String _id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("age")
    @Expose
    public String age;
    @SerializedName("country")
    @Expose
    public String country;
    @SerializedName("updated_date")
    @Expose
    public String updatedDate;
    @SerializedName("preferred_foot")
    @Expose
    public String preferredFoot;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("birthdate")
    @Expose
    public String Birthdate;
    @SerializedName("weight")
    @Expose
    public String weight;
    @SerializedName("height")
    @Expose
    public String height;
    @SerializedName("nationality")
    @Expose
    public String nationality;
    @SerializedName("country_code")
    @Expose
    public String countryCode;
    @SerializedName("asset_url")
    @Expose
    public String asset_url;
    @SerializedName("roles")
    @Expose
    public ArrayList<PlayerRolesModel> prModel;

    @SerializedName("statistics")
    @Expose
    public PlayerStatsModel psModel;

    @SerializedName("teams")
    @Expose
    public ArrayList<TeamModel> prTeams;
}

