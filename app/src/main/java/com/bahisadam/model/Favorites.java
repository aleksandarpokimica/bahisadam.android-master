package com.bahisadam.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fatih on 16.04.2017.
 */
public class Favorites {
    public Favorites() {
        matches= new ArrayList<MatchModel>();
        teams= new ArrayList<TeamModel>();
        leagues= new ArrayList<LeagueModel>();
        players= new ArrayList<PlayerModel>();
    }


    public ArrayList<MatchModel> matches;
    public ArrayList<TeamModel> teams;
    public ArrayList<LeagueModel> leagues;
    public ArrayList<PlayerModel> players;
}