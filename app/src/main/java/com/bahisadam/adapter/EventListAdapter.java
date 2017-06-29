package com.bahisadam.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bahisadam.R;
import com.bahisadam.fragment.StatisticsFragment;
import com.bahisadam.interfaces.Constant;
import com.bahisadam.model.MatchPOJO;
import com.bahisadam.utility.Utilities;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by atata on 06/12/2016.
 */

public class EventListAdapter extends SectionedAdapter<RecyclerView.ViewHolder> implements Constant {
    public static final String PLAYER_SUBSTITUTION_IN = "19";
    private List<StatisticsFragment.Events> mEventsList;
    private List<MatchPOJO.Event> mSubstitutionsOut;
    private Activity mActivity;
    private int homeTeamId;

    public EventListAdapter(List<StatisticsFragment.Events> eventsList, List<MatchPOJO.Event> substitutionsOut, Activity activity) {
        mEventsList = eventsList;
        mSubstitutionsOut = substitutionsOut;
        mActivity = activity;
    }

    public void setHomeTeamId(int homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    @Override
    public int getSectionCount() {
        return mEventsList.size();
    }

    @Override
    public int getItemCount(int section) {
        return mEventsList.get(section).getEvents().size();
    }


    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder titleHolder, final int section) {
        HeaderVH holder = (HeaderVH) titleHolder;
        String type = mEventsList.get(section).getEventType();
        if (type.equals(EVENT_GOAL)) {
            holder.label.setText(mActivity.getString(R.string.titleGoller));
        } else if (type.equals(EVENT_ASSIST)) {
            holder.label.setText(mActivity.getString(R.string.titleAssists));
        } else if (type.equals(EVENT_SUBSTITUTION)) {
            holder.label.setText(mActivity.getString(R.string.titleSubstitution));
        } else if (type.equals(EVENT_CARD)) {
            holder.label.setText(mActivity.getString(R.string.titleCards));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder itemHolder, int section, int relativePosition, int absolutePosition) {
        ItemHolder holder = (ItemHolder) itemHolder;
        MatchPOJO.Event event = mEventsList.get(section).getEvents().get(relativePosition);
        String type = event.getEventType();

        if (event.getTeam() == homeTeamId) {
            loadToHomeView(event, holder);

        } else {
            loadToAwayView(event, holder, relativePosition);
        }
    }

    private void loadToAwayView(MatchPOJO.Event event, ItemHolder holder, int relativePosition) {
        if (event.getEventType().equals(EVENT_SUBSTITUTION) && event.getActionType().equals(PLAYER_SUBSTITUTION_IN)) {
            holder.awayPlayer.setVisibility(View.GONE);
            holder.homePlayer.setVisibility(View.GONE);
            holder.awayPlayerSubstLayout.setVisibility(View.VISIBLE);
            holder.awayPlayerSubst1.setText(event.getPlayer());
            holder.awayPlayerSubst2.setText(findSubstitutionOutPlayerName(event));
        }

        holder.awayMinute.setText(event.getMinute() + "'");
        holder.awayPlayer.setText(event.getPlayer());
        String actionType = null != event.getActionType() ? event.getActionType() : "";
        loadIcon(event.getEventType(), actionType, holder.awayIcon);
        holder.homePlayer.setText("");
        holder.homePlayer.setVisibility(View.GONE);
        holder.homeMinute.setVisibility(View.GONE);
        holder.homeIcon.setImageResource(0);
    }

    private void loadToHomeView(MatchPOJO.Event event, ItemHolder holder) {
        if (event.getEventType().equals(EVENT_SUBSTITUTION) && event.getActionType().equals(PLAYER_SUBSTITUTION_IN)) {
            holder.awayPlayer.setVisibility(View.GONE);
            holder.homePlayer.setVisibility(View.GONE);
            holder.homePlayerSubstLayout.setVisibility(View.VISIBLE);
            holder.homePlayerSubst2.setText(findSubstitutionOutPlayerName(event));
            holder.homePlayerSubst1.setText(event.getPlayer());
        }

        holder.homeMinute.setText(event.getMinute() + "'");
        holder.homePlayer.setText(event.getPlayer());
        String actionType = null != event.getActionType() ? event.getActionType() : "";
        loadIcon(event.getEventType(), actionType, holder.homeIcon);
        holder.awayPlayer.setText("");
        holder.awayMinute.setVisibility(View.GONE);
        holder.awayIcon.setImageResource(0);
    }

    private String findSubstitutionOutPlayerName(MatchPOJO.Event event) {
        String substitutedPlayer = "";
        int teamId = event.getTeam();
        int minute = event.getMinute();

        for (MatchPOJO.Event substitutionEvent : mSubstitutionsOut) {
            if (substitutionEvent.getTeam() == teamId && substitutionEvent.getMinute() == minute) {
                substitutedPlayer = substitutionEvent.getPlayer();
            }
        }
        return substitutedPlayer;
    }

    private void loadIcon(String type, String actionType, ImageView view) {
        if (type.equals(EVENT_GOAL)) {
            try {
                InputStream is = mActivity.getAssets().open("goal.png");
                Bitmap bmp = BitmapFactory.decodeStream(is);
                view.setImageBitmap(bmp);

            } catch (IOException e) {
                e.printStackTrace();
            }
            //  view.setImageResource(R.drawable.ic_menu_item_1_active);
        } else if (type.equals(EVENT_ASSIST)) {
            view.setImageResource(R.drawable.ic_assist);
        } else if (type.equals(EVENT_SUBSTITUTION)) {
            view.setImageResource(R.drawable.player_substitution);
        } else if (type.equals(EVENT_YELLOWCARD)) {
            /*try{

                InputStream is = mActivity.getAssets().open("icons/yellow_card.png");
                Bitmap bmp = BitmapFactory.decodeStream(is);
                view.setImageBitmap(bmp);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            Picasso.with(mActivity).load("file:///android_asset/icons/yellow_card.png").into(view);
        } else if (type.equals(EVENT_REDCARD)) {
            /*try{
                InputStream is = mActivity.getAssets().open("icons/red_card.png");
                Bitmap bmp = BitmapFactory.decodeStream(is);
                view.setImageBitmap(bmp);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            Picasso.with(mActivity).load("file:///android_asset/icons/red_card.png").into(view);

        } else if (type.equals(EVENT_YELLOWREDCARD)) {
            /*try{
                InputStream is = mActivity.getAssets().open("icons/red_card.png");
                Bitmap bmp = BitmapFactory.decodeStream(is);
                view.setImageBitmap(bmp);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            Picasso.with(mActivity).load("file:///android_asset/icons/yellow_red_card.png").into(view);

        }

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER)
            return new HeaderVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.title_item, parent, false));
        else
            return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.event_layout, parent, false));
    }


    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.homePlayer) TextView homePlayer;
        @BindView(R.id.homeMinute) TextView homeMinute;
        @BindView(R.id.homeEventIcon) ImageView homeIcon;

        @BindView(R.id.awayPlayer) TextView awayPlayer;
        @BindView(R.id.awayMinute) TextView awayMinute;
        @BindView(R.id.awayEventIcon) ImageView awayIcon;

        @BindView(R.id.ll_event) LinearLayout ll_event;
        @BindView(R.id.awayPlayerSubstLayout) LinearLayout awayPlayerSubstLayout;
        @BindView(R.id.homePlayerSubstLayout) LinearLayout homePlayerSubstLayout;
        @BindView(R.id.awayPlayerSubst1) TextView awayPlayerSubst1;
        @BindView(R.id.homePlayerSubst1) TextView homePlayerSubst1;
        @BindView(R.id.awayPlayerSubst2) TextView awayPlayerSubst2;
        @BindView(R.id.homePlayerSubst2) TextView homePlayerSubst2;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ll_event.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int[] positions = getSectionIndexAndRelativePosition(getAdapterPosition());
            MatchPOJO.Event event = mEventsList.get(positions[0]).getEvents().get(positions[1]);
            if (event.player != null) {
                Utilities.openPlayerDetails(mActivity, event.player);
            }
        }
    }

    public class HeaderVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.titleLabel) TextView label;

        HeaderVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
        }
    }

}

