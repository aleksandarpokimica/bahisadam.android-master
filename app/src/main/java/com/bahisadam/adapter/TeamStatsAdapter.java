package com.bahisadam.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bahisadam.R;
import com.bahisadam.fragment.TeamStatisticsNewFragment;
import com.bahisadam.holder.CardHolderBase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.bahisadam.model.teamInfo.PlayerStatistics;

/**
 * Created by ali on 6/8/17.
 */

public class TeamStatsAdapter extends RecyclerView.Adapter<CardHolderBase> {

    List<PlayerStatistics> mList;
    private String statType;

    Activity mActivity;

    public TeamStatsAdapter(Activity activity) {
        this.mList = new ArrayList<>();
        mActivity = activity;
    }
    public void clear(){
        mList.clear();
    }
    public void add(List<PlayerStatistics>  sublist, String statType){
        this.statType = statType;
        mList.addAll(sublist);
    }
    public void add(PlayerStatistics pStats){
        mList.add(pStats);
    }


    @Override
    public CardHolderBase onCreateViewHolder(ViewGroup parent, int viewType) {

        int layout = R.layout.stats_item_new;

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);
        return new StatsVH(itemView);

    }

    @Override
    public void onBindViewHolder(CardHolderBase holder1, int position) {

        StatsVH holder = (StatsVH) holder1;

        holder.nameTv.setText(mList.get(position).getName());

        if(statType.equals("goal_leaders")) {
            holder.totalTv.setText(mList.get(position).getGoals() + "");
            holder.icon.setImageResource(R.drawable.fball);
        } else if (statType.equals("assist_leaders")) {
            holder.totalTv.setText(mList.get(position).getAssists() + "");
            holder.icon.setImageResource(R.drawable.ic_assist);
        } else if (statType.equals("card_leaders")) {
            holder.totalTv.setText(mList.get(position).getCards() + "");
            holder.icon.setImageResource(R.drawable.y_card);
        }

        int i = position + 1;

        holder.rank.setText(i+"");
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class StatsVH extends CardHolderBase {
        @BindView(R.id.mainView) View mainView;
        @BindView(R.id.playerName) TextView nameTv;
        @BindView(R.id.playerTotal) TextView totalTv;
        @BindView(R.id.rank) TextView rank;
        @BindView(R.id.icon) ImageView icon;
        @BindView(R.id.ll_player_info) LinearLayout ll_player_info;

        public StatsVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }
}