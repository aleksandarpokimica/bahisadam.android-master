package com.bahisadam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Aleksandar on 6/30/2017.
 */

class PlayerStatsNationalTeamStatsModel {
    @SerializedName("country_id")
    @Expose
    int countryId;
    @SerializedName("team_name_tr")
    @Expose
    String teamNameTr;
    @SerializedName("team_name")
    @Expose
    String teamName;
    @SerializedName("_id")
    @Expose
    int id;
}
