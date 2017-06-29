package com.bahisadam.model.teamInfo;

import com.bahisadam.model.MatchPOJO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GoalTimeStatistics
{
    private Conceded conceded;

    private Scored scored;

    public Conceded getConceded ()
    {
        return conceded;
    }

    public void setConceded (Conceded conceded)
    {
        this.conceded = conceded;
    }

    public Scored getScored ()
    {
        return scored;
    }

    public void setScored (Scored scored)
    {
        this.scored = scored;
    }


    @Override
    public String toString()
    {
        return "ClassPojo [conceded = "+conceded+", scored = "+scored+"]";
    }


}

