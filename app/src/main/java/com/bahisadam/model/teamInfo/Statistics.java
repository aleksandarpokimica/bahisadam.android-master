package com.bahisadam.model.teamInfo;


import com.bahisadam.view.DetailedPage.Item;

public class Statistics
{
    private Integer goal_attempts;

    private Integer offsides;

    private Integer shots_blocked;

    private Integer cards_given;

    private Integer shots_off_goal;

    private Integer shots_on_goal;

    private Integer goals_scored;

    private Integer assists;

    public Integer getGoal_attempts ()
    {
        return goal_attempts;
    }

    public void setGoal_attempts (Integer goal_attempts)
    {
        this.goal_attempts = goal_attempts;
    }

    public Integer getOffsides ()
    {
        return offsides;
    }

    public void setOffsides (Integer offsides)
    {
        this.offsides = offsides;
    }

    public Integer getShots_blocked ()
    {
        return shots_blocked;
    }

    public void setShots_blocked (Integer shots_blocked)
    {
        this.shots_blocked = shots_blocked;
    }

    public Integer getCards_given ()
    {
        return cards_given;
    }

    public void setCards_given (Integer cards_given)
    {
        this.cards_given = cards_given;
    }

    public Integer getShots_off_goal ()
    {
        return shots_off_goal;
    }

    public void setShots_off_goal (Integer shots_off_goal)
    {
        this.shots_off_goal = shots_off_goal;
    }

    public Integer getShots_on_goal ()
    {
        return shots_on_goal;
    }

    public void setShots_on_goal (Integer shots_on_goal)
    {
        this.shots_on_goal = shots_on_goal;
    }

    public Integer getGoals_scored ()
    {
        return goals_scored;
    }

    public void setGoals_scored (Integer goals_scored)
    {
        this.goals_scored = goals_scored;
    }

    public Integer getAssists() {
        return assists;
    }

    public void setAssists(Integer assists) {
        this.assists = assists;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [goal_attempts = "+goal_attempts+", offsides = "+offsides+", shots_blocked = "+shots_blocked+", cards_given = "+cards_given+", shots_off_goal = "+shots_off_goal+", shots_on_goal = "+shots_on_goal+", goals_scored = "+goals_scored+"]";
    }
}