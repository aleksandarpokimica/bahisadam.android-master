package com.bahisadam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Aleksandar on 6/30/2017.
 */

public class PlayerStatsTeamModel {

    @SerializedName("isNational")
    @Expose
    public boolean isNational;
    @SerializedName("start_date")
    @Expose
    public String startDate;
    @SerializedName("seasons")
    @Expose
    public ArrayList<PlayerStatsTeamModelSeasons> pstmsModel;
    @SerializedName("team")
    @Expose
    public PlayerStatsTeamModelTeam pstmtModel;
    @SerializedName("type")
    @Expose
    public String type;

}
