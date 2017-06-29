package com.bahisadam.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.bahisadam.R;
import com.bahisadam.interfaces.Constant;
import com.bahisadam.model.MatchPOJO;
import com.bahisadam.utility.Utilities;
import com.crashlytics.android.Crashlytics;
import com.squareup.picasso.Picasso;
import okhttp3.internal.Util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashSet;


public class TeamMatchesFragment extends Fragment {

    public static final String ARG_TEAM_NAME = "team_name";
    private ArrayList<MatchPOJO.Match> mPastMatches = new ArrayList<>();
    private ArrayList<MatchPOJO.Match> mNextMatches = new ArrayList<>();
    private LinkedHashSet<String> mMatchLeagues = new LinkedHashSet<>();
    private LinkedHashSet<String> mFixtureLeagues = new LinkedHashSet<>();
    private String mTeamName;

    private RecyclerView mRecyclerView;

    public static TeamMatchesFragment newInstance(ArrayList<MatchPOJO.Match> pastMatches, ArrayList<MatchPOJO.Match> nextMatches, String teamName) {
        TeamMatchesFragment fragment = new TeamMatchesFragment();
        Bundle args = new Bundle();
        fragment.mPastMatches = pastMatches;
        fragment.mNextMatches = nextMatches;
        args.putString(ARG_TEAM_NAME,teamName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTeamName = getArguments().getString(ARG_TEAM_NAME);
        }
        mMatchLeagues = getMatchLeagues();
        mFixtureLeagues = getFixtureLeagues();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_matches, container, false);

        TabHost tabHost = (TabHost) view.findViewById(android.R.id.tabhost);

        tabHost.setup();
        TabHost.TabSpec tabSpec;


        tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setIndicator(getString(R.string.matches));
        tabSpec.setContent(R.id.tab1);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setIndicator(getString(R.string.fixtures));
        tabSpec.setContent(R.id.tab1);
        tabHost.addTab(tabSpec);

        ViewGroup.LayoutParams params = tabHost.getTabWidget().getChildAt(0).getLayoutParams();

        params.height = (int) (params.height * 0.7);
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            View child = tabHost.getTabWidget().getChildAt(i);
            child.setLayoutParams(params);
            child.setBackgroundResource(R.drawable.tab_selector);
        }

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_cards_team_matches);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new TeamMatchesFragment.CardMatchAdapter(mMatchLeagues, mPastMatches));

        tabHost.setCurrentTabByTag("tag1");
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                if (s.equals("tag1")) {
                    mRecyclerView.setAdapter(new TeamMatchesFragment.CardMatchAdapter(mMatchLeagues, mPastMatches));
                } else if (s.equals("tag2")) {
                    mRecyclerView.setAdapter(new TeamMatchesFragment.CardFixtureAdapter(mFixtureLeagues, mNextMatches));

                }
            }
        });

        return view;
    }

    private LinkedHashSet<String> getMatchLeagues() {
        LinkedHashSet<String> leagues = new LinkedHashSet<>();
        if (mPastMatches != null) {
            for (MatchPOJO.Match match : mPastMatches) {
                leagues.add(match.getLeagueId().getLeagueNameTr());
            }
        }
        return leagues;
    }

    private LinkedHashSet<String> getFixtureLeagues() {
        LinkedHashSet<String> leagues = new LinkedHashSet<>();
        if (mNextMatches != null) {
            for (MatchPOJO.Match fixture : mNextMatches) {
                leagues.add(fixture.getLeagueId().getLeagueName());
            }
        }
        return leagues;
    }

    public class MatchHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewScore;
        private TextView mTextViewDate;
        private TextView mTextViewHomeTeam;
        private TextView mTextViewAwayTeam;
        private TextView mTextViewResult;
        private ImageView mImageViewHome;
        private ImageView mImageViewAway;


        public MatchHolder(View itemView) {
            super(itemView);
            mTextViewScore = (TextView) itemView.findViewById(R.id.score);
            mTextViewDate = (TextView) itemView.findViewById(R.id.matchDate);
            mTextViewHomeTeam = (TextView) itemView.findViewById(R.id.team1);
            mTextViewAwayTeam = (TextView) itemView.findViewById(R.id.team2);
            mTextViewResult = (TextView) itemView.findViewById(R.id.match_item_result);
            mImageViewHome = (ImageView) itemView.findViewById(R.id.team1logo);
            mImageViewAway = (ImageView) itemView.findViewById(R.id.team2logo);
            mTextViewResult = (TextView) itemView.findViewById(R.id.matchStatus);


        }

        void bindItem(MatchPOJO.Match match) {
            try {
                String homeTeam = match.getHomeTeam().getTeamNameTr();
                String awayTeam = match.getAwayTeam().getTeamNameTr();

                String team1 = homeTeam.length() > 10 ? homeTeam.substring(0,10) + "...": homeTeam;
                String team2 = awayTeam.length() > 10 ? awayTeam.substring(0,10) + "...": awayTeam;

                mTextViewHomeTeam.setText(team1);
                mTextViewAwayTeam.setText(team2);

                Calendar cal = Utilities.parseDate(match.getMatchDate());
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month= cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);

                String dateStr = Constant.SHORTMONTH[month] + " " + day;// + lnthFix;

                mTextViewDate.setText(dateStr);

                mTextViewScore.setText(match.getHomeGoals() + " : " + match.getAwayGoals());

                Utilities.loadLogoToView(mImageViewHome, match.getHomeTeam().getId());
                Utilities.loadLogoToView(mImageViewAway, match.getAwayTeam().getId());

                int homeGoals = Integer.valueOf(match.getHomeGoals());
                int awayGoals = Integer.valueOf(match.getAwayGoals());
                if((match.getHomeTeam().getTeamNameTr().equals(mTeamName) && homeGoals>awayGoals) ||
                        (match.getAwayTeam().getTeamNameTr().equals(mTeamName) && homeGoals<awayGoals)){
                    mTextViewResult.setBackgroundResource(R.drawable.shape_green);
                    mTextViewResult.setText("G");
                } else if (homeGoals == awayGoals){
                    mTextViewResult.setBackgroundResource(R.drawable.shape_yellow);
                    mTextViewResult.setText("B");
                } else{
                    mTextViewResult.setBackgroundResource(R.drawable.shape_red);
                    mTextViewResult.setText("M");
                }
            } catch(Exception ex) {
                Crashlytics.logException(ex);
            }
        }
    }

        public class MatchAdapter extends RecyclerView.Adapter<MatchHolder> {

            private ArrayList<MatchPOJO.Match> mMatches;

            public MatchAdapter(ArrayList<MatchPOJO.Match> matches) {
                mMatches = matches;
            }

            @Override
            public MatchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.maclar_item, parent, false); //was team_info_match_item
                return new MatchHolder(view);
            }

            @Override
            public void onBindViewHolder(MatchHolder holder, int position) {
                MatchPOJO.Match match = mMatches.get(position);
                holder.bindItem(match);
            }

            @Override
            public int getItemCount() {
                return mMatches.size();
            }
        }

        public class FixtureHolder extends RecyclerView.ViewHolder {

            private TextView mTextViewScore;
            private TextView mTextViewDate;
            private TextView mTextViewHomeTeam;
            private TextView mTextViewAwayTeam;
            private TextView mTextViewResult;
            private ImageView mImageViewHome;
            private ImageView mImageViewAway;


            public FixtureHolder(View itemView) {
                super(itemView);
                mTextViewScore = (TextView) itemView.findViewById(R.id.match_item_score);
                mTextViewDate = (TextView) itemView.findViewById(R.id.match_item_date);
                mTextViewHomeTeam = (TextView) itemView.findViewById(R.id.match_item_home_name);
                mTextViewAwayTeam = (TextView) itemView.findViewById(R.id.match_item_away_name);
                mTextViewResult = (TextView) itemView.findViewById(R.id.match_item_result);
                mImageViewHome = (ImageView) itemView.findViewById(R.id.match_item_home_logo);
                mImageViewAway = (ImageView) itemView.findViewById(R.id.match_item_away_logo);


            }

            void bindItem(MatchPOJO.Match fixture) {
                mTextViewScore.setText("-:-");
                mTextViewDate.setText(fixture.getMatchDate());
                mTextViewHomeTeam.setText(fixture.getHomeTeam().getTeamNameTr());
                mTextViewAwayTeam.setText(fixture.getAwayTeam().getTeamNameTr());
                try {
                    String url1 = Constant.IMAGES_ROOT + Constant.LOGO_PATH + fixture.getHomeTeam().getId() + "_logo.png";
                    String url2 = Constant.IMAGES_ROOT + Constant.LOGO_PATH + fixture.getAwayTeam().getId() + "_logo.png";
                    Picasso.with(getActivity()).load(url1).fit().into(mImageViewHome);
                    Picasso.with(getActivity()).load(url2).fit().into(mImageViewAway);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }

            public class FixtureAdapter extends RecyclerView.Adapter<FixtureHolder> {

                private ArrayList<MatchPOJO.Match> mFixtures;

                public FixtureAdapter(ArrayList<MatchPOJO.Match> fixtures) {
                    mFixtures = fixtures;
                }


                @Override
                public FixtureHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.team_info_match_item, parent, false);
                    return new FixtureHolder(view);
                }

                @Override
                public void onBindViewHolder(FixtureHolder holder, int position) {
                    MatchPOJO.Match fixture = mFixtures.get(position);
                    holder.bindItem(fixture);
                }

                @Override
                public int getItemCount() {
                    return mFixtures.size();
                }
            }


            public class StatsCardHolder extends RecyclerView.ViewHolder {

                private ArrayList<MatchPOJO.Match> mMatches;
                private ArrayList<MatchPOJO.Match> mFixtures;
                private TextView mTextView;
                private RecyclerView mRecyclerView;

                public StatsCardHolder(View itemView) {
                    super(itemView);
                    mTextView = (TextView) itemView.findViewById(R.id.text_view_card_league_name);
                    mRecyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_view_stats);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                }

                void bindMatch(ArrayList<MatchPOJO.Match> matches, String leagueName, final int leagueID) {
                    mMatches = matches;
                    mTextView.setText(leagueName);
                    mTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Utilities.openLeagueDetails(getActivity(),leagueID);
                        }
                    });
                    mRecyclerView.setAdapter(new MatchAdapter(mMatches)); //MatchListAdapter
                }

                void bindFixture(ArrayList<MatchPOJO.Match> fixtures, String leagueName, final int leagueID) {
                    mFixtures = fixtures;
                    mTextView.setText(leagueName);
                    mTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Utilities.openLeagueDetails(getActivity(),leagueID);
                        }
                    });
                    mRecyclerView.setAdapter(new FixtureAdapter(mFixtures));
                }
            }

            public class CardMatchAdapter extends RecyclerView.Adapter<StatsCardHolder> {

                private ArrayList<String> mLeagues = new ArrayList<>();
                private ArrayList<MatchPOJO.Match> mMatches = new ArrayList<>();

                public CardMatchAdapter(LinkedHashSet<String> leagues, ArrayList<MatchPOJO.Match> matches) {
                    mLeagues.addAll(leagues);
                    mMatches = matches;
                }

                @Override
                public StatsCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.team_info_card_match_item, parent, false);
                    return new StatsCardHolder(view);
                }

                @Override
                public void onBindViewHolder(StatsCardHolder holder, int position) {
                    String leagueName = mLeagues.get(position);
                    int leagueID = 1;
                    ArrayList<MatchPOJO.Match> matches = new ArrayList<>();
                    for (MatchPOJO.Match match : mMatches) {
                        if (match.getLeagueId().getLeagueNameTr().equals(leagueName)) {
                            matches.add(match);
                            leagueID = Integer.valueOf(match.getLeagueId().getId());
                        }
                    }
                    holder.bindMatch(matches, leagueName,leagueID);


                }

                @Override
                public int getItemCount() {
                    return mLeagues.size();
                }
            }

            public class CardFixtureAdapter extends RecyclerView.Adapter<StatsCardHolder> {

                private ArrayList<String> mLeagues = new ArrayList<>();
                private ArrayList<MatchPOJO.Match> mFixtures = new ArrayList<>();

                public CardFixtureAdapter(LinkedHashSet<String> leagues, ArrayList<MatchPOJO.Match> fixtures) {
                    mLeagues.addAll(leagues);
                    mFixtures = fixtures;
                }

                @Override
                public StatsCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.team_info_card_match_item, parent, false);
                    return new StatsCardHolder(view);
                }

                @Override
                public void onBindViewHolder(StatsCardHolder holder, int position) {
                    String leagueName = mLeagues.get(position);
                    int leagueID = 1;
                    ArrayList<MatchPOJO.Match> fixtures = new ArrayList<>();
                    for (MatchPOJO.Match fixture : mFixtures) {
                        if (fixture.getLeagueId().getLeagueNameTr().equals(leagueName)) {
                            fixtures.add(fixture);
                            leagueID = Integer.valueOf(fixture.getLeagueId().getId());
                        }
                    }
                    holder.bindFixture(fixtures, leagueName,leagueID);


                }

                @Override
                public int getItemCount() {
                    return mLeagues.size();
                }
            }


}
