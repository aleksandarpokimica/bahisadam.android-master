package com.bahisadam.view.DetailedPage;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.bahisadam.fragment.DetailFragment;
import com.bahisadam.holder.MatchOverviewHolder;
import com.bahisadam.model.MatchPOJO;

import java.text.DecimalFormat;

/**
 * Created by atata on 01/12/2016.
 */

public class MatchOverviewItem implements Item {
    MatchOverviewHolder holder;
    String tv;
    String referee;
    MatchPOJO.Stadium stadium;
    Activity mActivity;
    MatchPOJO.Match otherMatch;


    public MatchOverviewItem(String tv, String referee, MatchPOJO.Stadium stadium, MatchPOJO.Match otherMatch,
                             Activity activity) {
        this.tv = tv;
        this.referee = referee;
        this.stadium = stadium;
        this.otherMatch = otherMatch;
        this.mActivity = activity;
    }

    public MatchPOJO.Stadium getStadium() {
        return stadium;
    }
    public void setStadium(MatchPOJO.Stadium stadium) { this.stadium = stadium; }

    public String getTv() { return this.tv; }
    public void setTv(String value) { this.tv = value; }

    @Override
    public int getItemType() {
        return TYPE_OVERVIEW;
    }

    @Override
    public void bindViewHolder(RecyclerView.ViewHolder cardHolder) {
        holder =  (MatchOverviewHolder) cardHolder;
        if(stadium != null) {
            holder.stadiumName.setText(stadium.getName());
            holder.stadiumCapacity.setText(stadium.getCapacity());
        }
        if(referee == null) {
            holder.refereeRow.setVisibility(View.GONE);
        } else
        {
            holder.refereeRow.setVisibility(View.VISIBLE);
            holder.referee.setText(referee);
        }

        if(tv == null){
            holder.tvRow.setVisibility(View.GONE);
        } else {
            holder.tvRow.setVisibility(View.VISIBLE);
            holder.tv.setText(tv);
        }

        if(otherMatch == null) {
            holder.otherMatchRow.setVisibility(View.GONE);
        } else {
            holder.otherMatchRow.setVisibility(View.VISIBLE);
            holder.otherMatch.setText(otherMatch.getHomeTeam().getTeamNameTr() + " " +  otherMatch.getHomeGoals() + "-" +   otherMatch.getAwayGoals() + " " + otherMatch.getAwayTeam().getTeamNameTr());
        }
    }
}
