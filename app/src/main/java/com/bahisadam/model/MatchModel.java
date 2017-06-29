package com.bahisadam.model;

import java.util.Date;

/**
 * Created by fatih on 16.04.2017.
 */
public class MatchModel {
    public String _id;
    public TeamModel home_team;
    public TeamModel away_team;
    public int league_id;
    public Date match_date;
    public int year;
    public String result_type;
    public boolean is_half_time;
    public int home_goals;
    public int away_goals;
    public int half_time_home_score;
    public int half_time_away_score;
    public String live_minute;
    public String iddaa_code;
}
