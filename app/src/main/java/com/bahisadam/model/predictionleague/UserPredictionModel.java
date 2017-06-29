package com.bahisadam.model.predictionleague;

import com.bahisadam.model.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by fatih on 06.05.2017.
 */
public class UserPredictionModel {
    public String _id;
    public TeamModel home_team;
    public TeamModel away_team;
    public MatchModel match_id;
    public String forecast;
    public boolean success;

    public class MatchModel {
        public String _id;
        public int home_team;
        public int away_team;
        public int league_id;
        public Date match_date;
        public int year;
        public String result_type;
        public int home_goals;
        public int away_goals;
        public int half_time_home_score;
        public int half_time_away_score;
        public Odds odds;
    }

    public class Odds
    {
        private Map<String, Double> iddaa;

        public Map<String, Double> getIddaa()
        {
            return iddaa;
        }

        public void setIddaa ( Map<String, Double> iddaa)
        {
            this.iddaa = iddaa;
        }
    }


}
