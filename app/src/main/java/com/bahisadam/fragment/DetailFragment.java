package com.bahisadam.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.bahisadam.R;
import com.bahisadam.adapter.CommentsAdapter;
import com.bahisadam.adapter.MatchAdapter;
import com.bahisadam.holder.*;
import com.bahisadam.interfaces.Constant;
import com.bahisadam.interfaces.RestClient;
import com.bahisadam.model.StandingsRequest;
import com.bahisadam.view.DetailedPage.*;
import com.bahisadam.model.MatchPOJO;
import com.bahisadam.view.DetailPageActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.txusballesteros.widgets.FitChart;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class DetailFragment extends Fragment implements Constant,CommentsAdapter.OnLikeClickListener, MatchAdapter.UpdateMatchListener {


    private static final String ARG_MATCH_ID = "MATCH_ID";
    private MatchAdapter matchAdapter;


    private View rootView;
    @BindView(R.id.rootLayout) LinearLayout rootLayout;
    public List<MatchPOJO.Comment> comments;
    private CommentsAdapter adapter;
    private Retrofit retrofit;
    private RestClient restClientAPI;
    private MatchPOJO.HomeTeam homeTeam;
    private MatchPOJO.AwayTeam awayTeam;
    StandingsItem standingsItem;
    GoalAveragesItem goalAveragesItem;
    CommentsItem commentsItem;
    MatchOverviewItem matchOverviewItem;
    String matchId;
    String resultType;
    DetailPageActivity mActivity;


    private boolean voted;
    private View voteView;
    private View standingView;
    private View goalAveragesView;
    private View commentsView;
    private View predictionView;
    private View otherMatchView;
    private View matchOverviewView;
    private VoteHolder voteHolder;
    private StandingsHolder standingsHolder;
    private GoalAveragesHolder goalAveragesHolder;
    private CommentsHolder commentsHolder;
    private PredictionHolder predictionHolder;
    private OtherMatchHolder otherMatchHolder;
    private MatchOverviewHolder matchOverviewHolder;

    public DetailFragment() {
        // Required empty public constructor
    }


    public static DetailFragment newInstance(String matchId ) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MATCH_ID, matchId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            matchId = getArguments().getString(ARG_MATCH_ID);

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mActivity = (DetailPageActivity) this.getActivity();
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        rootView = view;
        ButterKnife.bind(this,view);
        buildRetrofit();

        comments = new ArrayList<MatchPOJO.Comment>();
        adapter = new CommentsAdapter(getActivity(),comments);
        adapter.setOnLikeClickListener(this);
        getComments();

        voteView =  LayoutInflater.from(rootView.getContext()).inflate(R.layout.vote_layout,null,false);
        rootLayout.addView(voteView,0);
        voteHolder= new VoteHolder(voteView);

        matchOverviewView = LayoutInflater
                .from(rootView.getContext()).inflate(R.layout.detail_match_overview, null, false);
        rootLayout.addView(matchOverviewView, 1);
        matchOverviewHolder = new MatchOverviewHolder(matchOverviewView);

        standingView = LayoutInflater
                .from(rootView.getContext()).inflate(R.layout.standings_layout,null,false);
        rootLayout.addView(standingView);
        standingsHolder = new StandingsHolder(standingView);


        goalAveragesView = LayoutInflater
                .from(rootView.getContext())
                .inflate(R.layout.homeaway_layout,null,false);
        rootLayout.addView(goalAveragesView);
        goalAveragesHolder = new GoalAveragesHolder(goalAveragesView);


        commentsView = LayoutInflater.from(rootView.getContext())
                .inflate(R.layout.comments_layout,null,false);
        rootLayout.addView(commentsView);
        commentsHolder = new CommentsHolder(commentsView);


        predictionView = LayoutInflater
                .from(rootView.getContext())
                .inflate(R.layout.prediction_layout,null,false);

        rootLayout.addView(predictionView);
        predictionHolder = new PredictionHolder(predictionView);

        otherMatchView = LayoutInflater.from(rootView.getContext()).inflate(R.layout.other_match_layout, null, false);
        rootLayout.addView(otherMatchView);
        otherMatchHolder = new OtherMatchHolder(otherMatchView);

        mActivity.subscribeForChanges(this);
        updateData();
        return view;
    }


    public void updateMatch(MatchPOJO.MatchDetailed match){
        if( null == match  || rootLayout == null) return;

        if(match.getStadium() != null) {
            matchOverviewItem = new MatchOverviewItem(match.getTv(), match.getReferee(), match.getStadium(), match.getOtherMatch(), getActivity());
            matchOverviewItem.bindViewHolder(matchOverviewHolder);
            matchOverviewView.setVisibility(View.VISIBLE);
        } else {
            matchOverviewView.setVisibility(View.GONE);
        }

        homeTeam = match.getHomeTeam();
        awayTeam = match.getAwayTeam();
        Double away = match.getForecast().getAway() == null ? 0 : match.getForecast().getAway().doubleValue();
        Double home = match.getForecast().getHome() == null ? 0 : match.getForecast().getHome().doubleValue();
        Double draw = match.getForecast().getDraw() == null ? 0 : match.getForecast().getDraw().doubleValue();
        //boolean voted = match.getResult_type().equals("NOT_PLAYED");
        boolean voted = false;
        VoteItem voteItem = new VoteItem(
                home,away,draw,
                homeTeam.getTeamNameTr().split(" ")[0],
                awayTeam.getTeamNameTr().split(" ")[0],
                matchId,mActivity);
        if( !"NotPlayed".equals(match.getResult_type()) )
            voteItem.setVoted(true);
        voteItem.bindViewHolder(voteHolder);



        if(match.getLeagueId().getType().equals("League")){
            standingView.setVisibility(View.VISIBLE);
            goalAveragesView.setVisibility(View.VISIBLE);
        } else {
            standingView.setVisibility(View.GONE);
            goalAveragesView.setVisibility(View.GONE);
        }

        standingsItem = new StandingsItem(homeTeam,awayTeam,getActivity());
        standingsItem.bindViewHolder(standingsHolder);
        goalAveragesItem = new GoalAveragesItem(homeTeam,awayTeam,getActivity());


        commentsItem = new CommentsItem(comments,adapter,getActivity());
        commentsItem.bindViewHolder(commentsHolder);

        PredictionItem predictionItem = new PredictionItem(matchId,(DetailPageActivity) getActivity());
        predictionItem.setFragment(this);
        predictionItem.bindViewHolder(predictionHolder);
        int leagueId = match.getLeagueId().getId();
        homeTeam = mActivity.getHomeTeam();
        awayTeam = mActivity.getAwayTeam();
    }
    public void updateData(){
        MatchPOJO.MatchDetailed match = mActivity.getMatchDetailed();
        updateMatch(match);
        updateStandings();
        updateGoalAverages();
    }

    @Override
    public void updateGoalAverages() {
        if(goalAveragesItem == null || standingsItem == null) return;
        getHomeAwayStandings();
    }

    @Override
    public void updateStandings() {
        if( standingsItem == null) return;
        getGeneralStandings();

    }

    public void buildRetrofit(){
        Gson gson = new GsonBuilder()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.ROOT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        // prepare call in Retrofit 2.0
        restClientAPI = retrofit.create(RestClient.class);

    }

    public void getGeneralStandings(){
        StandingsRequest.Standing standings = mActivity.getGeneralStandings();
        if(standings == null) return;

        StandingsRequest.TeamStanding homeStanding = null;
        StandingsRequest.TeamStanding awayStanding = null;
        if(mActivity.getGeneralStandings().getGroups().size() > 0) {
            homeStanding = checkGroup(mActivity.getGeneralStandings().getGroups().get(0).getTeamStandings(), homeTeam.getTeamName());
            awayStanding = checkGroup(mActivity.getGeneralStandings().getGroups().get(0).getTeamStandings(), awayTeam.getTeamName());

            standingsItem.setHomeStanding(homeStanding);
            standingsItem.setAwayStanding(awayStanding);
            standingsItem.bindViewHolder(standingsHolder);
        }
    }

    public void getHomeAwayStandings(){
        StandingsRequest.Standing standingsHome = mActivity.getStandingsHome();
        StandingsRequest.Standing standingsAway = mActivity.getStandingsAway();
        if(standingsAway == null || standingsHome == null || standingsHome.getGroups().size() == 0) return;


        StandingsRequest.TeamStanding homeStandingHome = null;
        StandingsRequest.TeamStanding homeStandingAway = null;
        StandingsRequest.TeamStanding awayStandingHome = null;
        StandingsRequest.TeamStanding awayStandingAway = null;

        if(standingsHome.getGroups().size() > 0) {
            homeStandingHome = checkGroup(standingsHome.getGroups().get(0).getTeamStandings(), homeTeam.getTeamName());
            homeStandingAway = checkGroup(standingsAway.getGroups().get(0).getTeamStandings(), homeTeam.getTeamName());
            awayStandingHome = checkGroup(standingsHome.getGroups().get(0).getTeamStandings(), awayTeam.getTeamName());
            awayStandingAway = checkGroup(standingsAway.getGroups().get(0).getTeamStandings(), awayTeam.getTeamName());


            standingsItem.setAwayStandingAway(awayStandingAway);
            standingsItem.setAwayStandingHome(awayStandingHome);
            standingsItem.setHomeStandingAway(homeStandingAway);
            standingsItem.setHomeStandingHome(homeStandingHome);

            goalAveragesItem.setAwayStandingAway(awayStandingAway);
            goalAveragesItem.setAwayStandingHome(awayStandingHome);
            goalAveragesItem.setHomeStandingAway(homeStandingAway);
            goalAveragesItem.setHomeStandingHome(homeStandingHome);



            standingsItem.bindViewHolder(standingsHolder);
            goalAveragesItem.bindViewHolder(goalAveragesHolder);
        }
    }


    public void getComments(){



        Call<List<MatchPOJO.Comment>> call = restClientAPI.commentsRequest(matchId);

        //asynchronous call
        call.enqueue(new Callback<List<MatchPOJO.Comment>>() {
            @Override
            public void onResponse(Call<List<MatchPOJO.Comment>> call, Response<List<MatchPOJO.Comment>> response) {
                if(response.body() != null ){
                    comments.clear();
                    comments.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    if(commentsItem!=null && commentsHolder != null)
                        commentsItem.bindViewHolder(commentsHolder);
                }

            }

            @Override
            public void onFailure(Call<List<MatchPOJO.Comment>> call, Throwable t) {
                int x=0;
                t.printStackTrace();

            }
        });
    }


    private StandingsRequest.TeamStanding checkGroup(List<StandingsRequest.TeamStanding> standings, String teamName){
        for(int i=0; i < standings.size(); i++){
            if(standings.get(i) != null && standings.get(i).getTeamName() != null && standings.get(i).getTeamName().equals(teamName))
                return  standings.get(i);
        }
        return null;
    }


    @Override
    public void onLikeClick(int pos) {
        String commentId = comments.get(pos).getId();
        Call<MatchPOJO.LikeUpdate>  call = restClientAPI.likeUpdateRequest(commentId);
        call.enqueue(new Callback<MatchPOJO.LikeUpdate>() {
            @Override
            public void onResponse(Call<MatchPOJO.LikeUpdate> call, Response<MatchPOJO.LikeUpdate> response) {
                if(response.body().success)
                    getComments();
            }

            @Override
            public void onFailure(Call<MatchPOJO.LikeUpdate> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
