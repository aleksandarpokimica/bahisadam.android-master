package com.bahisadam.model.teamInfo;

import com.bahisadam.model.*;
import com.bahisadam.model.common.*;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fatih on 14.06.2017.
 */
public class TeamDetailResponseModel {
    @SerializedName("detail")
    @Expose
    private TeamDetailModel detail = null;

    @SerializedName("stats")
    @Expose
    private TeamStats teamStats = null;

    @SerializedName("competitions")
    @Expose
    private List<LeagueModel> competitions = null;



    private List<MatchPOJO.Match> pastMatches;
    private List<MatchPOJO.Match> nextMatches;

    public TeamDetailModel getTeamDetail() {
        return detail;
    }

    public void setTeamInfo(TeamDetailModel detail) {
        this.detail = detail;
    }

    public TeamStats getTeamStats() {
        return teamStats;
    }

    public void setTeamStats(TeamStats teamStats) {
        this.teamStats = teamStats;
    }

    public List<LeagueModel> getLeagues() {
        return competitions;
    }

    public void setLeagues(List<LeagueModel> competitions) {
        this.competitions = competitions;
    }

    public List<MatchPOJO.Match> getPastMatches() {
        return pastMatches;
    }

    public void setPastMatches(List<MatchPOJO.Match> pastMatches) {
        this.pastMatches = pastMatches;
    }

    public List<MatchPOJO.Match> getNextMatches() {
        return nextMatches;
    }

    public void setNextMatches(List<MatchPOJO.Match> nextMatches) {
        this.nextMatches = nextMatches;
    }

}
