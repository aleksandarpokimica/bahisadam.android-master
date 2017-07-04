package com.bahisadam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Aleksandar on 6/30/2017.
 */

public class PlayerStatsNationalModel {
    @SerializedName("isNational")
    @Expose
    boolean isNational;

    @SerializedName("start_date")
    @Expose
    String startDate;

    @SerializedName("seasons")
    @Expose
    ArrayList<PlayerStatsNationalSeasonsModel> psnsModel;

    @SerializedName("team")
    @Expose
    PlayerStatsNationalTeamModel psntModel;
    @SerializedName("type")
    @Expose
    String type;
}
