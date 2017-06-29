package com.bahisadam.model.teamInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TeamStats
{
    @SerializedName("off")
    private String offsides;

    @SerializedName("cor")
    private String corner_kicks;

    @SerializedName("frk")
    private String free_kicks;

    @SerializedName("cards")
    private String cards_given;

    @SerializedName("tho")
    private String though;

    @SerializedName("blk")
    private String shots_blocked;

    @SerializedName("pos")
    private String ball_possession;

    @SerializedName("sot")
    private String goal_attempts;

    @SerializedName("soff")
    private String shots_off_goal;

    @SerializedName("son")
    private String shots_on_goal;;

    private String goals_scored;

    private String matches_played;

    private String matches_won;

    private String id;
    private String _id;

    private String updated_date;

    private String inserted_date;

    private String goals_conceded;

    private String goals_by_head;

    private String goals_by_foot;


    private String form;

    @SerializedName("goaltime_statistics")
    private GoalTimeStatistics goaltime_statistics;

    private String __v;

    @SerializedName("player_statistics")
    private ArrayList<PlayerStatistics> player_statistics;

    @SerializedName("goal_leaders")
    private ArrayList<PlayerStatistics> goal_leaders;

    @SerializedName("assist_leaders")
    private ArrayList<PlayerStatistics> assist_leaders;

    @SerializedName("card_leaders")
    private ArrayList<PlayerStatistics> card_leaders;

    @SerializedName("keyPlayers")
    private ArrayList<PlayerStatistics> keyPlayers;

    private String team;

    private String league;


    public String getOffsides ()
    {
        if(offsides == null) return "-";
        return offsides;
    }

    public void setOffsides (String offsides)
    {
        this.offsides = offsides;
    }

    public String getInserted_date ()
    {
        return inserted_date;
    }

    public void setInserted_date (String inserted_date)
    {
        this.inserted_date = inserted_date;
    }

    public String getCards_given ()
    {
        if(cards_given == null) return "-";
        return cards_given;
    }

    public void setCards_given (String cards_given)
    {
        this.cards_given = cards_given;
    }

    public String getCorner_kicks ()
    {
        if(corner_kicks == null)
            return "-";
        return corner_kicks;
    }

    public void setCorner_kicks (String corner_kicks)
    {
        this.corner_kicks = corner_kicks;
    }

    public String getMatches_played ()
    {
        if(matches_played == null) return "-";
        return matches_played;
    }

    public void setMatches_played (String matches_played)
    {
        this.matches_played = matches_played;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getUpdated_date ()
    {
        return updated_date;
    }

    public void setUpdated_date (String updated_date)
    {
        this.updated_date = updated_date;
    }

    public String getShots_blocked ()
    {
        if(shots_blocked == null) return "-";
        return shots_blocked;
    }

    public void setShots_blocked (String shots_blocked)
    {
        this.shots_blocked = shots_blocked;

    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getGoals_conceded ()
    {
        if(goals_conceded == null) return "-";

        return goals_conceded;
    }

    public void setGoals_conceded (String goals_conceded)
    {
        this.goals_conceded = goals_conceded;
    }

    public String getGoals_by_head ()
    {
        if(goals_by_head == null) return "-";

        return goals_by_head;
    }

    public void setGoals_by_head (String goals_by_head)
    {
        this.goals_by_head = goals_by_head;
    }

    public String getGoals_by_foot ()
    {
        if(shots_blocked == null) return "-";

        return goals_by_foot;
    }

    public void setGoals_by_foot (String goals_by_foot)
    {
        this.goals_by_foot = goals_by_foot;
    }

    public String getFree_kicks ()
    {
        if(free_kicks == null) return "-";
        return free_kicks;
    }

    public void setFree_kicks (String free_kicks)
    {
        this.free_kicks = free_kicks;
    }

    public String getForm ()
    {
        return form;
    }

    public void setForm (String form)
    {
        this.form = form;
    }

    public GoalTimeStatistics getGoaltime_statistics ()
    {
        return goaltime_statistics;
    }

    public void setGoaltime_statistics (GoalTimeStatistics goaltime_statistics)
    {
        this.goaltime_statistics = goaltime_statistics;
    }

    public String get__v ()
    {
        return __v;
    }

    public void set__v (String __v)
    {
        this.__v = __v;
    }

    public ArrayList<PlayerStatistics> getPlayer_statistics ()
    {
        return player_statistics;
    }

    public void setPlayer_statistics (ArrayList<PlayerStatistics> player_statistics)
    {
        this.player_statistics = player_statistics;
    }

    public String getTeam ()
    {
        return team;
    }

    public void setTeam (String team)
    {
        this.team = team;
    }

    public String getLeague ()
    {
        return league;
    }

    public void setLeague (String league)
    {
        this.league = league;
    }

    public String getMatches_won ()
    {
        if(matches_won == null) return "-";
        return matches_won;
    }

    public void setMatches_won (String matches_won)
    {
        this.matches_won = matches_won;
    }

    public String getBall_possession ()
    {
        if(ball_possession == null) return "-";
        return ball_possession;
    }

    public void setBall_possession (String ball_possession)
    {
        this.ball_possession = ball_possession;
    }

    public String getGoals_scored ()
    {
        if(goals_scored == null) return "-";

        return goals_scored;
    }

    public void setGoals_scored (String goals_scored)
    {
        this.goals_scored = goals_scored;
    }

    public String getGoal_attempts ()
    {
        if(goal_attempts == null) return "-";

        return goal_attempts;
    }

    public void setGoal_attempts (String goal_attempts)
    {
        this.goal_attempts = goal_attempts;
    }

    public String getShots_off_goal ()
    {
        if(shots_off_goal == null) return "-";

        return shots_off_goal;
    }

    public void setShots_off_goal (String shots_off_goal)
    {
        this.shots_off_goal = shots_off_goal;
    }

    public String getShots_on_goal ()
    {
        if(shots_on_goal == null) return "-";

        return shots_on_goal;
    }

    public void setShots_on_goal (String shots_on_goal)
    {
        this.shots_on_goal = shots_on_goal;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [offsides = "+offsides+", inserted_date = "+inserted_date+", cards_given = "+cards_given+", corner_kicks = "+corner_kicks+", matches_played = "+matches_played+", id = "+id+", updated_date = "+updated_date+", shots_blocked = "+shots_blocked+", _id = "+_id+", goals_conceded = "+goals_conceded+", goals_by_head = "+goals_by_head+", goals_by_foot = "+goals_by_foot+", free_kicks = "+free_kicks+", form = "+form+", goaltime_statistics = "+goaltime_statistics+", __v = "+__v+", player_statistics = "+player_statistics+", team = "+team+", league = "+league+", matches_won = "+matches_won+", ball_possession = "+ball_possession+", goals_scored = "+goals_scored+", goal_attempts = "+goal_attempts+", shots_off_goal = "+shots_off_goal+", shots_on_goal = "+shots_on_goal+"]";
    }

    public ArrayList<PlayerStatistics> getGoal_leaders() {
        return goal_leaders;
    }

    public void setGoal_leaders(ArrayList<PlayerStatistics> goal_leaders) {
        this.goal_leaders = goal_leaders;
    }

    public ArrayList<PlayerStatistics> getAssist_leaders() {
        return assist_leaders;
    }

    public void setAssist_leaders(ArrayList<PlayerStatistics> assist_leaders) {
        this.assist_leaders = assist_leaders;
    }

    public ArrayList<PlayerStatistics> getCard_leaders() {
        return card_leaders;
    }

    public void setCard_leaders(ArrayList<PlayerStatistics> card_leaders) {
        this.card_leaders = card_leaders;
    }
    public ArrayList<PlayerStatistics> getKeyPlayers() {
        if(keyPlayers == null) return new ArrayList<>();
        return keyPlayers;
    }

    public void setKeyPlayers(ArrayList<PlayerStatistics> keyPlayers) {
        this.keyPlayers = keyPlayers;
    }
}



