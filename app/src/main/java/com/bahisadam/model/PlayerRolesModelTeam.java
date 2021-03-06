package com.bahisadam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Aleksandar on 6/28/2017.
 */

public class PlayerRolesModelTeam {

    @SerializedName("country_id")
    @Expose
    public int prmCountryId;
    @SerializedName("team_name_tr")
    @Expose
    public String prmTeamNameTr;
    @SerializedName("team_name")
    @Expose
    public String prmTeamName;
    @SerializedName("_id")
    @Expose
    public int prmId;

}
