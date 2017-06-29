package com.bahisadam.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fatih on 07.05.2017.
 */
public class Group{
    // public List<MatchPOJO.Standing> mlist;
    public List<StandingsRequest.TeamStanding> mList;
    public Group(){
        // mlist = new ArrayList<MatchPOJO.Standing>();
        mList = new ArrayList<StandingsRequest.TeamStanding>();
    }
}


