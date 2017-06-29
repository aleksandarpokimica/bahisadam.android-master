package com.bahisadam.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;
import com.bahisadam.MyApplication;
import com.bahisadam.R;
import com.bahisadam.interfaces.Constant;
import com.bahisadam.model.LiveResponse;
import com.bahisadam.model.MatchModel;
import com.bahisadam.model.MatchPOJO;
import com.bahisadam.utility.Preferences;
import com.bahisadam.utility.Utilities;
import com.bahisadam.view.DetailPageActivity;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by atata on 13/12/2016.
 */

public class LiveMatchAdapter extends SectionedRecyclerViewAdapter<RecyclerView.ViewHolder> {
    public List<LiveResponse.LiveItem> data;
    private final ArrayMap<Integer, Integer> mHeaderLocationMap;
    private LiveMatchAdapter.OnMatchClickListener mListener = null;

    public LiveMatchAdapter() {
        data = new LinkedList<LiveResponse.LiveItem>();
        mHeaderLocationMap = new ArrayMap<>();

    }
    public int[] getSectionIndexAndRelativePosition(int itemPosition) {
        synchronized (mHeaderLocationMap) {
            Integer lastSectionIndex = -1;
            for (final Integer sectionIndex : mHeaderLocationMap.keySet()) {
                if (itemPosition > sectionIndex) {
                    lastSectionIndex = sectionIndex;
                } else {
                    break;
                }
            }
            return new int[]{mHeaderLocationMap.get(lastSectionIndex), itemPosition - lastSectionIndex - 1};
        }
    }
    public int getSection(int absolutePath){
        return mHeaderLocationMap.get(absolutePath);
    }
    public int allocateHeaders(){
        int count = 0;
        mHeaderLocationMap.clear();
        for (int s = 0; s < getSectionCount(); s++) {
            int itemCount = getItemCount(s);
            if ((itemCount > 0)) {
                mHeaderLocationMap.put(count, s);
                count += itemCount + 1;
            }
        }
        return count;
    }

    @Override
    public int getSectionCount() {
        return data.size();
    }

    @Override
    public int getItemCount(int section) {
        if( data.get(section).getMatches() == null ) return 0;
        return data.get(section).getMatches().size();
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder headerHolder, int section) {
        LeagueVH holder = (LeagueVH) headerHolder;
        holder.leagueTv.setText(data.get(section).getLeague_name_tr());
        Context ctx = holder.img.getContext();
        int id = ctx.getResources().getIdentifier(data.get(section).getFlag(),"drawable",ctx.getPackageName());



        try {
            Drawable dr;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                dr = ctx.getResources().getDrawable(id, ctx.getTheme());
            } else {
                dr = ctx.getResources().getDrawable(id);
            }
            Bitmap bitmap = Bitmap.createBitmap(200,
                    200, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            dr.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            dr.draw(canvas);
            Bitmap rounded = Utilities.getRoundedCornerBitmap(bitmap,Utilities.getPx(ctx,50));
            holder.img.setImageBitmap(rounded);

        } catch (Resources.NotFoundException e){
           // e.printStackTrace();
        }

    }


    public interface OnMatchClickListener {
        void onFavoriteClick(int section, int position);
        void onMatchClick(int section, int position);
    }

    public void setOnClickListener(LiveMatchAdapter.OnMatchClickListener listener){
        mListener = listener;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder itemHolder, final int section, int relativePosition, int absolutePosition) {
        final Activity activity = (Activity)itemHolder.itemView.getContext();

        final MatchVH holder = (MatchVH) itemHolder;
        if(relativePosition == 0 ) holder.divider.setVisibility(View.GONE);
        final MatchPOJO.Match match = data.get(section).getMatches().get(relativePosition);

        holder.score.setText(match.getHomeGoals() + " : " + match.getAwayGoals());
        String team1 = Utilities.truncateTeamName(match.getHomeTeam().getTeamNameTr());
        String team2 = Utilities.truncateTeamName(match.getAwayTeam().getTeamNameTr());
        holder.team1tv.setText(team1);
        holder.team2tv.setText(team2);
        if(Preferences.getFavoriteMatches().contains(match.getId()) ) {
            Utilities.paintFavoriteIconOntoWhiteBg(activity, holder.favIcon, true);
        } else {
            Utilities.paintFavoriteIconOntoWhiteBg(activity, holder.favIcon, false);
        }


        holder.itemSection = section;
        holder.relativePosition = relativePosition;
        holder.absolutePosition = absolutePosition;


        if(MyApplication.sUse_Logo){
            Utilities.loadLogoToView(holder.team1Iv,match.getHomeTeam().getId());
            Utilities.loadLogoToView(holder.team2Iv,match.getAwayTeam().getId());
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                Bitmap team1logo = Utilities.paintTeamIcon(holder.team1Iv.getContext(),
                        match.getHomeTeam().getColor1(),
                        match.getHomeTeam().getColor2());
                Bitmap team2logo = Utilities.paintTeamIcon(holder.team2Iv.getContext(),
                        match.getAwayTeam().getColor1(),
                        match.getAwayTeam().getColor2());
                holder.team1Iv.setImageBitmap(team1logo);
                holder.team2Iv.setImageBitmap(team2logo);
            }
        }

        String matchStatusStr = "-";
        if(match.getResultType().equals("Played")){
            matchStatusStr  = holder.matchStatus.getContext().getString(R.string.ms);
            holder.matchStatus.setText(matchStatusStr);
            holder.score.setBackgroundResource(R.drawable.rounded_grey);
        } else if (match.getResultType().equals("Interrupted")) {
            holder.matchStatus.setText("Ara verildi");
        }
        else {

            try {
                if(match.getResultType().equals(Constant.PLAYING))
                if (match.getIsHalfTime()) {
                    holder.matchStatus.setText(R.string.halfTime);
                } else {
                    if(match.getLiveMinute() == null ) {
                        matchStatusStr = "-"; }
                    else {
                        matchStatusStr = match.getLiveMinute().toString() + "'";
                        holder.matchStatus.setText(matchStatusStr);
                    }

                    holder.matchStatus.setText(matchStatusStr);
                }
                /*
                if (match.getIsHalfTime()) {
                    String homeStr = match.getHalfTimeHomeScore() != null ? match.getHalfTimeHomeScore().toString() : "";
                    String awayStr = match.getHalfTimeAwayScore() != null ? match.getHalfTimeAwayScore().toString() : "";
                    String res = holder.score.getContext().getString(R.string.iv) + " " + homeStr + " : " + awayStr;
                    if("".equals(homeStr) || "".equals(awayStr)) {
                        holder.halfTimeScore.setVisibility(View.GONE);
                    }else {
                        holder.halfTimeScore.setVisibility(View.VISIBLE);
                        holder.halfTimeScore.setText(res);
                    }
                    /*holder.halfTimeScore.setText( + " " +
                            +match.getHalfTimeHomeScore() + " : "
                            + match.getHalfTimeAwayScore());
                }*/

                holder.score.setBackgroundResource(R.drawable.rounded_green);

                final Animation animation1 = new AlphaAnimation(0.7f, 1f);
                animation1.setDuration(200);

                final Animation animation2 = new AlphaAnimation(1f, 0.7f);
                animation2.setDuration(200);

                //animation1 AnimationListener
                animation1.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationEnd(Animation arg0) {
                        // start animation2 when animation1 ends (continue)
                        holder.score.startAnimation(animation2);
                    }

                    @Override
                    public void onAnimationRepeat(Animation arg0) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onAnimationStart(Animation arg0) {
                        // TODO Auto-generated method stub

                    }

                });

                //animation2 AnimationListener
                animation2.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationEnd(Animation arg0) {
                        // start animation1 when animation2 ends (repeat)
                        holder.score.startAnimation(animation1);
                    }

                    @Override
                    public void onAnimationRepeat(Animation arg0) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onAnimationStart(Animation arg0) {
                        // TODO Auto-generated method stub
                    }

                });

                holder.score.setBackgroundResource(R.drawable.rounded_green);

                holder.score.startAnimation(animation1);


            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        this.setOnClickListener(new LiveMatchAdapter.OnMatchClickListener() {
            @Override
            public void onFavoriteClick(int section, int position) {
                MatchPOJO.Match m = data.get(section).getMatches().get(position);
                boolean remove = Preferences.getFavoriteMatches().contains(m.getId());

                if(remove) {
                    Preferences.getFavoriteMatches().remove(m.getId());
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(m.getId());
                } else {
                    Preferences.getFavoriteMatches().add(m.getId());
                    FirebaseMessaging.getInstance().subscribeToTopic(m.getId());
                }

                Utilities.addRemoveMatchToFavorites(m.getId(), remove, activity);

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onMatchClick(int section, int position) {
                MatchPOJO.Match m = data.get(section).getMatches().get(position);
                if(m.getHomeTeam() == null || m.getAwayTeam() ==null)
                {
                    return;
                }

                Bundle bundle = new Bundle();
                bundle.putString(DetailPageActivity.MATCH_ID,m.getId());
                bundle.putInt(DetailPageActivity.LEAGUE_ID, m.getLeagueId().getId());
                bundle.putString(DetailPageActivity.ARG_TEAM_HOME_NAME,m.getHomeTeam().getTeamNameTr());
                bundle.putString(DetailPageActivity.ARG_TEAM_AWAY_NAME,m.getAwayTeam().getTeamNameTr());
                bundle.putString(DetailPageActivity.ARG_REUSULT_TYPE, m.getResultType());
                Utilities.openMatchDetails(activity, bundle);
            }
        });
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_HEADER) {
            return new LeagueVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_section,parent,false));
        } else{
            return new MatchVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.live_item,parent,false));
        }
    }

    public class MatchVH extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.team1) TextView team1tv;
        @BindView(R.id.team2) TextView team2tv;
        @BindView(R.id.team1logo) ImageView team1Iv;
        @BindView(R.id.team2logo) ImageView team2Iv;
        @BindView(R.id.score) TextView score;
        @BindView(R.id.halfTimeScore) TextView halfTimeScore;
        @BindView(R.id.matchStatus) TextView matchStatus;
        @BindView(R.id.matchLayout) LinearLayout matchLayout;
        @BindView(R.id.divider) View divider;
        @BindView(R.id.fav_icon_container) LinearLayout favIconContainer;
        @BindView(R.id.fav_icon) TextView favIcon;
        int itemSection = -1;
        int relativePosition = -1;
        int absolutePosition = -1;


        public MatchVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            score.setBackgroundResource(R.drawable.rounded_green);
            favIconContainer.setOnClickListener(this);
            matchLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mListener ==null) return;

            try {
                switch (v.getId()) {
                    case R.id.fav_icon_container:
                        mListener.onFavoriteClick(itemSection, relativePosition);
                        break;
                    default :
                        mListener.onMatchClick(itemSection, relativePosition);
                        break;
                }
            } catch (NullPointerException e) {

                // pass
                e.printStackTrace();
            }
        }
    }
    public class LeagueVH extends RecyclerView.ViewHolder{
        @BindView(R.id.img_title_logo_img) ImageView img;
        @BindView(R.id.sectionTitle) TextView leagueTv;
        public LeagueVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}