package com.bahisadam.fragment;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import com.bahisadam.R;
import com.bahisadam.adapter.TeamStatsAdapter;
import com.bahisadam.interfaces.RestClient;
import com.bahisadam.model.*;
import com.bahisadam.model.teamInfo.PlayerStatistics;
import com.bahisadam.model.teamInfo.TeamStats;
import com.bahisadam.utility.Utilities;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ali on 6/7/17.
 */
public class TeamStatisticsNewFragment extends Fragment { //modified LeagueStatisticsFragment.java

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


    private RestClient mClient;

    private ArrayList<LeagueModel> leagues;
    private int teamId;

    HashMap<Integer, stats> mList = new HashMap<>();

    private int cur_league_id = 0;
    private int cur_tab = STATISTICS_GOAL;

    public static TeamStatisticsNewFragment newInstance(ArrayList<LeagueModel> leagues, int teamId, ArrayList<PlayerStatistics> goal_leaders, ArrayList<PlayerStatistics> asist_leaders, ArrayList<PlayerStatistics> card_leaders) {
        TeamStatisticsNewFragment fragment = new TeamStatisticsNewFragment();
        Bundle args = new Bundle();
        args.putInt(TEAM_ID, teamId);
        fragment.setArguments(args);
        fragment.goal_leaders = goal_leaders;
        fragment.assist_leaders = asist_leaders;
        fragment.card_leaders = card_leaders;
        fragment.leagues = leagues;
        return fragment;
    }

    public TeamStatisticsNewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            teamId = getArguments().getInt(TEAM_ID);
        }

        stats s = new stats();
        s.goal_leaders = new ArrayList<>();
        s.goal_leaders.addAll(goal_leaders);

        s.asist_leaders = new ArrayList<>();
        s.asist_leaders.addAll(assist_leaders);

        s.card_leaders = new ArrayList<>();
        s.card_leaders.addAll(card_leaders);

        mList.put(0, s);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_team_new_statistics, container, false);
        ButterKnife.bind(this,view);
        mClient = Utilities.buildRetrofit();
        mAdapter = new TeamStatsAdapter(getActivity());
        rv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rv.setAdapter(mAdapter);
//        loadData();
        TabHost tabHost = (TabHost) view.findViewById(android.R.id.tabhost);

        tabHost.setup();


        TabHost.TabSpec tabSpec;

        tabSpec = tabHost.newTabSpec(TAG1);
        tabSpec.setIndicator(getString(R.string.goal));
        tabSpec.setContent(R.id.tab1);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec(TAG2);
        tabSpec.setIndicator(getString(R.string.asist));
        tabSpec.setContent(R.id.tab1);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec(TAG3);
        tabSpec.setContent(R.id.tab1);
        tabSpec.setIndicator(getString(R.string.yellowCards));
        tabHost.addTab(tabSpec);

        String[] leag = new String[leagues.size()];
        for (int i=0; i<leagues.size(); i++) {
            leag[i] = leagues.get(i).league_name_tr;
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, leag); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnner_leagues.setAdapter(spinnerArrayAdapter);
        spnner_leagues.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cur_league_id = i;
                loadData(i, leagues.get(i)._id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ViewGroup.LayoutParams params = tabHost.getTabWidget().getChildAt(0).getLayoutParams();

        params.height = (int) (params.height * 0.7);
        for(int i =0 ;i < tabHost.getTabWidget().getChildCount(); i++) {
            View child = tabHost.getTabWidget().getChildAt(i);
            child.setLayoutParams(params);
            child.setBackgroundResource(R.drawable.tab_selector);
        }
        tabHost.setCurrentTabByTag(TAG1);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                if(s.equals(TAG1)){
                    showStatistics(STATISTICS_GOAL);
                } else if(s.equals(TAG2)){
                    showStatistics(STATISTICS_ASISTS);
                } else if(s.equals(TAG3)){
                    showStatistics(STATISTICS_CARDS);
                }
            }
        });

        if(mList.get(cur_league_id) != null && mList.get(cur_league_id).goal_leaders != null) {
            mAdapter.add(mList.get(cur_league_id).goal_leaders, "goal_leaders");
            mAdapter.notifyDataSetChanged();
        }

        ((LinearLayout) underlines.getChildAt(0)).getChildAt(0).setVisibility(View.VISIBLE);

        return view;
    }

    private void loadData(final int index, int leagueId) {

        if (mList.containsKey(index)) {
            showStatistics(cur_tab);
            return;
        }

        Call<BaseResponse<TeamStats>> call = mClient.teamStatsByLeague(teamId, leagueId);
        call.enqueue(new Callback<BaseResponse<TeamStats>>() {
            @Override
            public void onResponse(Call<BaseResponse<TeamStats>> call, Response<BaseResponse<TeamStats>> response) {

                BaseResponse<TeamStats> body = response.body();
                if(body.isSuccess && body.data != null) {
                    TeamStats stats = body.data;
                    stats s = new stats();
                    if(stats.getGoal_leaders() != null) {
                        s.goal_leaders = new ArrayList<>();
                        s.goal_leaders.addAll(stats.getGoal_leaders());
                    }

                    if(stats.getAssist_leaders() != null) {
                        s.asist_leaders = new ArrayList<>();
                        s.asist_leaders.addAll(stats.getAssist_leaders());
                    }

                    if(stats.getCard_leaders() != null) {
                        s.card_leaders = new ArrayList<>();
                        s.card_leaders.addAll(stats.getCard_leaders());
                    }

                    mList.put(index, s);
                    showStatistics(cur_tab);
                } else {
                    mList.put(index, new stats());
                    showStatistics(cur_tab);
                }

            }

            @Override
            public void onFailure(Call<BaseResponse<TeamStats>> call, Throwable t) {

            }
        });

    }

    private void showStatistics(int type){
        cur_tab = type;
        switch (type) {
            case STATISTICS_GOAL:
                mAdapter.clear();
                mAdapter.add(mList.get(cur_league_id).goal_leaders, "goal_leaders");
                mAdapter.notifyDataSetChanged();
                ((LinearLayout) underlines.getChildAt(0)).getChildAt(0).setVisibility(View.VISIBLE);
                break;
            case STATISTICS_ASISTS:
                mAdapter.clear();
                mAdapter.add(mList.get(cur_league_id).asist_leaders, "assist_leaders");
                mAdapter.notifyDataSetChanged();
                ((LinearLayout) underlines.getChildAt(1)).getChildAt(0).setVisibility(View.VISIBLE);
                break;
            case STATISTICS_CARDS:
                mAdapter.clear();
                mAdapter.add(mList.get(cur_league_id).card_leaders, "card_leaders");
                mAdapter.notifyDataSetChanged();
                ((LinearLayout) underlines.getChildAt(2)).getChildAt(0).setVisibility(View.VISIBLE);
                break;
        }
    }

    class stats {

        public ArrayList<PlayerStatistics> goal_leaders = new ArrayList<>();
        public ArrayList<PlayerStatistics> asist_leaders = new ArrayList<>();
        public ArrayList<PlayerStatistics> card_leaders = new ArrayList<>();

    }
}
