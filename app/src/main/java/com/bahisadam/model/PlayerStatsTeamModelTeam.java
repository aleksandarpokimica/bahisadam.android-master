package com.bahisadam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Aleksandar on 7/3/2017.
 */

public class PlayerStatsTeamModelTeam {

    @SerializedName("country_id")
    @Expose
    public String countryId;
    @SerializedName("team_name_tr")
    @Expose
    public String teamNameTr;
    @SerializedName("team_name")
    @Expose
    public String teamName;
    @SerializedName("_id")
    @Expose
    public String id;

}