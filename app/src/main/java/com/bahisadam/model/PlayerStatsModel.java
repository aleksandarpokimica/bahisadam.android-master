package com.bahisadam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Aleksandar on 6/30/2017.
 */

public class PlayerStatsModel {
    @SerializedName("national_team_statistics")
    @Expose
    public ArrayList<PlayerStatsNationalModel> psnModel;

    @SerializedName("team_statistics")
    @Expose
    public ArrayList<PlayerStatsTeamModel> pstModel;

    @SerializedName("totals")
    @Expose
    public PlayerStatsTotalModel totalModel;
}
