package com.bahisadam.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bahisadam.R;
import com.bahisadam.fragment.SquadFragment;
import com.bahisadam.fragment.StandingsFragment;
import com.bahisadam.fragment.TeamAnalysisFragment;
import com.bahisadam.fragment.TeamDetailsFragment;
import com.bahisadam.fragment.TeamMatchesFragment;
import com.bahisadam.fragment.TeamStatisticsNewFragment;
import com.bahisadam.interfaces.RestClient;
import com.bahisadam.model.League;
import com.bahisadam.model.LeagueModel;
import com.bahisadam.model.MatchPOJO;
import com.bahisadam.model.teamInfo.*;
import com.bahisadam.utility.Preferences;
import com.bahisadam.utility.Utilities;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TeamDetailsActivity extends BaseActivity {

    public static final String ARG_TEAM_INFO = "TEAM_INFO";
    public static final String ARG_TEAM_NAME = "TEAM_NAME";
    public static final String ARG_TEAM_ICON = "TEAM_ICON";
    public static Integer mTeamId;
    private TeamDetailsActivity.SectionsPagerAdapter mSectionsPagerAdapter;

    private View fav_layout;
    private TextView tv_fav;
    private TextView teamNameTV;
    private TextView teamContryLeague;
    private ImageView teamFlag;
    private CircleImageView teamLogoIV;

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Bundle mTeamInfo;
    private ArrayList<PlayerStatistics> mPlayers;
    private ArrayList<TeamMatch> mMatches;
    private ArrayList<TeamFixture> mFixtures;
    private Set<String> teamsFollow;
    private RestClient restClient;
    public static TeamStats analiz_stats;
    public static String teamName;
    public static String teamLogoPath;
    String color1;
    String color2;
    public TeamDetailModel mDetails;
    public TeamStats mStats;
    public ArrayList<PlayerStatistics> mKeyPlayers;
    public ArrayList<MatchPOJO.Match> mPastMatches = new ArrayList<>();
    public ArrayList<MatchPOJO.Match> mNextMatches = new ArrayList<>();

    public ArrayList<LeagueModel> mLeagues;
    public ArrayList<PlayerStatistics> mGoalLeaders = new ArrayList<>();
    public ArrayList<PlayerStatistics> mAsistLeaders = new ArrayList<>();
    public ArrayList<PlayerStatistics> mCardLeaders = new ArrayList<>();



    private int league_id = 1;

    @Override
    public void api(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details);

        initFooterToolbar();
        tv_fav = (TextView)findViewById(R.id.tv_fav);
        fav_layout = findViewById(R.id.fav_layout);
        teamNameTV = (TextView)findViewById(R.id.team_details_teamName);
        teamContryLeague = (TextView) findViewById(R.id.teamCountryLeague);
        teamFlag = (ImageView) findViewById(R.id.img_flag);
        teamLogoIV = (CircleImageView)findViewById(R.id.team_details_teamLogo);
        Intent intent = getIntent();
        mTeamId = intent.getIntExtra(BaseActivity.ID,1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        restClient = Utilities.buildRetrofit();
        mViewPager = (ViewPager) findViewById(R.id.container);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);

        if(savedInstanceState == null) {
            loadData();
        } else{
            mTeamInfo = savedInstanceState.getBundle(ARG_TEAM_INFO);
            updateUI();
        }

        mfavicon(false);

        teamsFollow = Preferences.getTeamsFollow();
        fav_layout.setOnClickListener(this);

    }

    private void mfavicon(boolean b) {

        if (b) {
            tv_fav.setText("Takip Etme");
            tv_fav.setTextColor(Color.parseColor("#32B846"));
            fav_layout.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            tv_fav.setText("Takip Et");
            tv_fav.setTextColor(Color.parseColor("#ffffff"));
            fav_layout.setBackgroundColor(Color.parseColor("#32B846"));
        }

    }

    private void loadData() {

        Call<TeamDetailResponseModel> call = restClient.teamDetails(mTeamId);
        call.enqueue(new Callback<TeamDetailResponseModel>() {
            @Override
            public void onResponse(Call<TeamDetailResponseModel> call, Response<TeamDetailResponseModel> response) {

                TeamDetailResponseModel responseModel = response.body();
                mDetails = responseModel.getTeamDetail();
                mStats = responseModel.getTeamStats();
                if(mStats != null) {
                    if(mStats.getGoal_leaders() != null)
                        mGoalLeaders.addAll(mStats.getGoal_leaders());

                    if(mStats.getAssist_leaders() != null)
                        mAsistLeaders.addAll(mStats.getAssist_leaders());

                    if(mStats.getCard_leaders() != null)
                        mCardLeaders.addAll(mStats.getCard_leaders());

                    if(mStats.getKeyPlayers() != null)
                        mKeyPlayers = mStats.getKeyPlayers();
                }

                mLeagues = (ArrayList<LeagueModel>) responseModel.getLeagues();
                mPastMatches = (ArrayList<MatchPOJO.Match>) responseModel.getPastMatches();
                mNextMatches = (ArrayList<MatchPOJO.Match>) responseModel.getNextMatches();


                mSectionsPagerAdapter = new TeamDetailsActivity.SectionsPagerAdapter(getSupportFragmentManager());
                mViewPager.setAdapter(mSectionsPagerAdapter);
                mTabLayout.setupWithViewPager(mViewPager);

                updateUI();
            }

            @Override
            public void onFailure(Call<TeamDetailResponseModel> call, Throwable t) {

                Log.e("reali", "fail");

            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.fav_layout){
            try{
                if(teamsFollow.contains(mTeamId.toString())) {
                    mfavicon(false);
                    teamsFollow.remove(mTeamId.toString());
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("team_" + mTeamId.toString());
                    Utilities.addRemoveTeamToFavorites(mTeamId, true, this);
                } else {
                    mfavicon(true);
                    teamsFollow.add(mTeamId.toString());
                    FirebaseMessaging.getInstance().subscribeToTopic("team_" + mTeamId.toString());
                    Log.d("topic synced",  mTeamId.toString());
                    Utilities.addRemoveTeamToFavorites(mTeamId, false, this);
                }

                Preferences.setTeamsFollow(teamsFollow);
            } catch (NullPointerException e){
                Log.e("error", "error during adding selected team to favorites", e);
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(ARG_TEAM_INFO,mTeamInfo);
    }


    private void updateUI(){
     //parseInfo(mTeamInfo);
     if(mDetails != null) {
         teamName = mDetails.getName();
         teamNameTV.setText(mDetails.getName());
         Utilities.loadLogoToView(teamLogoIV, mTeamId);


         boolean isFavorite = false;
         if(teamsFollow.contains(mTeamId.toString())) {
             isFavorite = true;
         }
         mfavicon(isFavorite);
     }

     teamFlag.setImageResource(android.R.color.transparent);

     if(mLeagues != null &&  mLeagues.size() > 0 && mLeagues.get(0) != null)
        league_id = mLeagues.get(0)._id;
        teamContryLeague.setText(mLeagues.get(0).league_name_tr);
    }

   private void parseInfo(Bundle info){
       mMatches = info.getParcelableArrayList(TeamInfoFetcher.TEAM_INFO_MATCHES);
       mFixtures = info.getParcelableArrayList(TeamInfoFetcher.TEAM_INFO_FIXTURES);
   }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment frag = null;

            switch (position) {
                case 0 : frag = SquadFragment.newInstance(mDetails, mKeyPlayers); break;
                case 1 : frag = TeamMatchesFragment.newInstance(mPastMatches, mNextMatches, mDetails.getName_tr()); break;
                case 2 : frag = TeamStatisticsNewFragment.newInstance(mLeagues, mTeamId, mGoalLeaders, mAsistLeaders, mCardLeaders); break;
                case 3 : frag = TeamAnalysisFragment.newInstance(); break;
                case 4 : frag = StandingsFragment.newInstance(league_id); break;
                default: frag = TeamDetailsFragment.newInstance(mDetails); break;
            }

            return frag;
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.kadro);
                case 1:
                    return getString(R.string.matches);
                case 2:
                    return getString(R.string.statistics);
                case 3:
                    return getString(R.string.analysis);
                case 4:
                    return getString(R.string.detal_page_standings_mini);
            }
            return null;
        }
    }

}
