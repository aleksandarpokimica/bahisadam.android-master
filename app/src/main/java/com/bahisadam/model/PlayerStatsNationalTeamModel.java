package com.bahisadam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Aleksandar on 6/30/2017.
 */

class PlayerStatsNationalTeamModel {
    @SerializedName("statistics")
    @Expose
    PlayerStatsNationalTeamStatsModel psntsModel;
    @SerializedName("tournament")
    @Expose
    PlayerStatsNationalTeamTournamentModel psnttModel;
    @SerializedName("season")
    @Expose
    PlayerStatsNationalTeamSeasonModel psntSeasonModel;
    @SerializedName("start_date")
    @Expose
    String startDate;
    @SerializedName("type")
    @Expose
    String type;
}
