package com.bahisadam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Aleksandar on 7/3/2017.
 */

public class PlayerStatsTeamModelSeasons {

    @SerializedName("statistics")
    @Expose
    public PlayerStatsTeamModelStats pstmsModel;
    @SerializedName("tournament")
    @Expose
    public PlayerStatsTeamModelTournament pstmtModel;
    @SerializedName("season")
    @Expose
    public PlayerStatsTeamModelSeason pstmSeasonModel;
    @SerializedName("start_date")
    @Expose
    public String startDate;
    @SerializedName("type")
    @Expose
    public String type;

}
