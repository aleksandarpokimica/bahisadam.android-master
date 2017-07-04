package com.bahisadam.fragment;

import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.bahisadam.R;
import com.bahisadam.adapter.TeamStatsAdapter;
import com.bahisadam.model.teamInfo.PlayerStatistics;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Aleksandar on 7/1/2017.
 */

public class PlayerDetailsFragment extends Fragment{

    public ArrayList<PlayerStatistics> goal_leaders;
    public ArrayList<PlayerStatistics> assist_leaders;
    public ArrayList<PlayerStatistics> card_leaders;

    public static final String ARG_LEAGUES = "LEAGUES";
    public static final String TEAM_ID = "TEAM_ID";

    public static final String TAG1 = "tag_1";
    public static final String TAG2 = "tag_2";
    public static final String TAG3 = "tag_3";

    private TeamStatsAdapter mAdapter;

    private static final int STATISTICS_GOAL = 1, STATISTICS_ASISTS = 2, STATISTICS_CARDS = 3;
    @BindView(R.id.leagueStats)
    RecyclerView rv;
    @BindView(R.id.statsUnderlinesLayout)
    LinearLayout underlines;
    @BindView(R.id.spinner)
    Spinner spnner_leagues;



}
