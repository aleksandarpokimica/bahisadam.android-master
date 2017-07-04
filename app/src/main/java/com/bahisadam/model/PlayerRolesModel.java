package com.bahisadam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Aleksandar on 6/28/2017.
 */

public class PlayerRolesModel {
    @SerializedName("jersey_number")
    @Expose
    public int jerseyNumber;
    @SerializedName("start_date")
    @Expose
    public String startDate;
    @SerializedName("end_date")
    @Expose
    public String endDate;
    @SerializedName("team")
    @Expose
    public PlayerRolesModelTeam prmTeam;
    @SerializedName("active")
    @Expose
    public boolean active;
    @SerializedName("type")
    @Expose
    public String Type;

}
