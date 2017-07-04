package com.bahisadam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Aleksandar on 7/3/2017.
 */

public class PlayerStatsTeamModelStats {
    @SerializedName("last_event_time")
    @Expose
    public String lastEventTime;
    @SerializedName("red_cards")
    @Expose
    public String redCards;
    @SerializedName("yellow_red_cards")
    @Expose
    public String yellowRedCards;
    @SerializedName("yellow_cards")
    @Expose
    public String yellowCards;
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
