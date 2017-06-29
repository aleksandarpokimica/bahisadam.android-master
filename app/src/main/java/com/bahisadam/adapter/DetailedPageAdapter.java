package com.bahisadam.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bahisadam.R;
import com.bahisadam.holder.*;
import com.bahisadam.view.DetailedPage.Item;
import com.txusballesteros.widgets.FitChart;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Created by atata on 30/11/2016.
 */

public class DetailedPageAdapter extends RecyclerView.Adapter<CardHolderBase> {

    public static final int TYPE_VOTE = 1,TYPE_STANDINGS = 2, TYPE_HOMEAWAY = 3, TYPE_COMMENTS = 4, TYPE_IDEA=5, TYPE_OVERVIEW=6;
    private List<Item> itemsList;

    public DetailedPageAdapter(List<Item> itemsList) {
        this.itemsList = itemsList;
    }

    @Override
    public CardHolderBase onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = R.layout.vote_layout;
        switch (viewType) {
            case TYPE_VOTE: layout = R.layout.vote_layout;
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(layout, parent, false);
                    return new VoteHolder(v);
            case TYPE_STANDINGS : layout = R.layout.standings_layout;
                View standingsView = LayoutInflater.from(parent.getContext())
                        .inflate(layout, parent, false);
                return new StandingsHolder(standingsView);
            case TYPE_HOMEAWAY: layout = R.layout.homeaway_layout;
                View homeawayView = LayoutInflater.from(parent.getContext())
                        .inflate(layout,parent,false);
                return new GoalAveragesHolder(homeawayView);
            case TYPE_COMMENTS: layout = R.layout.comments_layout;
                View commentsView = LayoutInflater.from(parent.getContext())
                        .inflate(layout,parent,false);
                return new CommentsHolder(commentsView);
            case TYPE_IDEA: layout = R.layout.prediction_layout;
                View predictionView = LayoutInflater.from(parent.getContext())
                        .inflate(layout,parent,false);
                return new PredictionHolder(predictionView);
            case TYPE_OVERVIEW: layout = R.layout.detail_match_overview;
                View overviewView = LayoutInflater.from(parent.getContext())
                        .inflate(layout,parent,false);
                return new MatchOverviewHolder(overviewView);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(CardHolderBase holder, int position) {
        itemsList.get(position).bindViewHolder(holder);
    }

    @Override
    public int getItemViewType(int position) {
        Item item = itemsList.get(position);
        return itemsList.get(position).getItemType();
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }



}
