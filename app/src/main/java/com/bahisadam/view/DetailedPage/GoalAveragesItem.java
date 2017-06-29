package com.bahisadam.view.DetailedPage;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import com.bahisadam.MyApplication;
import com.bahisadam.holder.GoalAveragesHolder;
import com.bahisadam.model.MatchPOJO;
import com.bahisadam.utility.Utilities;
import com.bahisadam.model.StandingsRequest;


import java.text.DecimalFormat;

/**
 * Created by atata on 01/12/2016.
 */

public class GoalAveragesItem implements Item {
    GoalAveragesHolder holder;
    MatchPOJO.HomeTeam homeTeam;
    MatchPOJO.AwayTeam awayTeam;
    Activity mActivity;

    StandingsRequest.TeamStanding homeStandingHome;
    StandingsRequest.TeamStanding homeStandingAway;
    StandingsRequest.TeamStanding awayStandingHome;
    StandingsRequest.TeamStanding awayStandingAway;

    public GoalAveragesItem(MatchPOJO.HomeTeam homeTeam,
                            MatchPOJO.AwayTeam awayTeam,
                            Activity activity) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.mActivity = activity;

    }

    public StandingsRequest.TeamStanding getHomeStandingHome() {
        return homeStandingHome;
    }

    public void setHomeStandingHome(StandingsRequest.TeamStanding homeStandingHome) {
        this.homeStandingHome = homeStandingHome;
    }

    public StandingsRequest.TeamStanding getHomeStandingAway() {
        return homeStandingAway;
    }

    public void setHomeStandingAway(StandingsRequest.TeamStanding homeStandingAway) {
        this.homeStandingAway = homeStandingAway;
    }

    public StandingsRequest.TeamStanding getAwayStandingHome() {
        return awayStandingHome;
    }

    public void setAwayStandingHome(StandingsRequest.TeamStanding awayStandingHome) {
        this.awayStandingHome = awayStandingHome;
    }

    public StandingsRequest.TeamStanding getAwayStandingAway() {
        return awayStandingAway;
    }

    public void setAwayStandingAway(StandingsRequest.TeamStanding awayStandingAway) {
        this.awayStandingAway = awayStandingAway;
    }

    @Override
    public int getItemType() {
        return TYPE_HOMEAWAY;
    }

    @Override
    public void bindViewHolder(RecyclerView.ViewHolder cardHolder) {
        holder =  (GoalAveragesHolder) cardHolder;
        showGoalAverages();

    }
    private void showGoalAverages() {
        DecimalFormat df = new DecimalFormat("#0.0");

        holder.goalHomeTeam.setText(homeTeam.getTeamNameTr());
        if(homeStandingHome != null){
            Double ag = homeStandingHome.getAG().doubleValue() / homeStandingHome.getOM().doubleValue();
            ag = ag.isNaN() ? 0 : ag;
            holder.goalHomeAG.setText(df.format(ag));
            Double yg = homeStandingHome.getYG().doubleValue() / homeStandingHome.getOM().doubleValue();
            yg = yg.isNaN() ? 0 : yg;
            holder.goalHomeYG.setText(df.format(yg));

        }

        holder.goalAwayTeam.setText(awayTeam.getTeamNameTr());
        if(awayStandingHome !=null ){
            Double ag = awayStandingHome.getAG().doubleValue() / awayStandingHome.getOM().doubleValue();
            ag = ag.isNaN() ? 0 : ag;
            holder.goalAwayAG.setText(df.format(ag));
            Double yg = awayStandingHome.getYG().doubleValue() / awayStandingHome.getOM().doubleValue();
            yg = yg.isNaN() ? 0 : yg;
            holder.goalAwayYG.setText(df.format(yg));
        }

        if(MyApplication.sUse_Logo){
            Utilities.loadLogoToView(holder.homeTeamLogo,homeTeam.getId());
            Utilities.loadLogoToView(holder.awayTeamLogo,awayTeam.getId());
        }
    }
}
