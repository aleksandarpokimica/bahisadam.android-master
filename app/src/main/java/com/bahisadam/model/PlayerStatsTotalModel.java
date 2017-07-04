package com.bahisadam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Aleksandar on 6/30/2017.
 */

public class PlayerStatsTotalModel {

    @SerializedName("last_event_time")
    @Expose
    public String lastEventTime;
    @SerializedName("yellow_cards")
    @Expose
    public String yellowCards;
    @SerializedName("yellow_red_cards")
    @Expose
    public String yellowRedCards;
    @SerializedName("red_cards")
    @Expose
    public String redCards;
    @SerializedName("own_goals")
    @Expose
    public String ownGoals;
    @SerializedName("assists")
    @Expose
    public String assists;
    @SerializedName("goals_scored")
    @Expose
    public String goalsScored;
    @SerializedName("substituted_out")
    @Expose
    public String substitutedOut;
    @SerializedName("substituted_in")
    @Expose
    public String substitutedIn;
    @SerializedName("matches_played")
    @Expose
    public String matchesPlayed;

}