package com.bahisadam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Aleksandar on 7/3/2017.
 */

public class PlayerStatsTeamModelTournament {

    @SerializedName("country_code")
    @Expose
    public String countryCode;
    @SerializedName("league_name_tr")
    @Expose
    public String leagueNameTr;
    @SerializedName("league_name")
    @Expose
    public String leagueName;
    @SerializedName("_id")
    @Expose
    public String id;

}