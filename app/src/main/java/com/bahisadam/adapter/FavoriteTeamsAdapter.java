package com.bahisadam.adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bahisadam.MyApplication;
import com.bahisadam.R;
import com.bahisadam.fragment.FavoriteMatchesFragment;
import com.bahisadam.fragment.FavoriteTeamsFragment;
import com.bahisadam.interfaces.Constant;
import com.bahisadam.model.MatchModel;
import com.bahisadam.model.TeamModel;
import com.bahisadam.utility.FontManager;
import com.bahisadam.utility.Preferences;
import com.bahisadam.utility.Utilities;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FavoriteTeamsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements  Constant {

    public List<TeamModel> data;
    private OnMatchClickListener mListener = null;
    private Activity activity;
    private FavoriteTeamsFragment frag;
    private FavoriteTeamsViewHolder itemHolder;
    public Activity getActivity() {
        return activity;
    }


    public FavoriteTeamsAdapter(ArrayList<TeamModel> data, Activity activity, FavoriteTeamsFragment frag) {
        this.data = data;
        this.activity = activity;
        this.frag = frag;
    }

    public void refreshUI() {
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new FavoriteTeamsAdapter.FavoriteTeamsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_team_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        itemHolder = (FavoriteTeamsViewHolder)holder;
        TeamModel team = data.get(position);

        this.setOnClickListener(new FavoriteTeamsAdapter.OnMatchClickListener() {
            @Override
            public void onFavoriteClick(int position) {
                TeamModel team = data.get(position);
                Set<String> favoriteTeams = Preferences.getTeamsFollow();

                Utilities.addRemoveTeamToFavorites(team._id, true, activity);

                FirebaseMessaging.getInstance().unsubscribeFromTopic("team_" + team._id);
                Utilities.paintFavoriteIconOntoWhiteBg(activity, itemHolder.favIcon, false);
                data.remove(position);

                favoriteTeams.remove(team._id);
                Preferences.setTeamsFollow(favoriteTeams);

                refreshUI();
            }

            @Override
            public void onMatchClick(int position) {
                TeamModel team = data.get(position);
                Utilities.openTeamDetails(getActivity(), team._id);
            }
        });

        renderDetail(team);
    }

    public void renderDetail(TeamModel team) {
        itemHolder.team.setText(team.team_name_tr);
        Utilities.loadLogoToView(itemHolder.logo , team._id);

        Typeface tf = FontManager.getTypeface(activity, FontManager.FONTAWESOME);
        itemHolder.favIcon.setTypeface(tf);
        Utilities.paintFavoriteIconOntoWhiteBg(this.activity, itemHolder.favIcon, true);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnMatchClickListener {
        void onFavoriteClick(int position);
        void onMatchClick(int position);
    }

    public void setOnClickListener(FavoriteTeamsAdapter.OnMatchClickListener listener){
        mListener = listener;
    }


    class FavoriteTeamsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        @BindView(R.id.logo) ImageView logo;
        @BindView(R.id.team) TextView team;
        @BindView(R.id.fav_icon) TextView favIcon;
        @BindView(R.id.fav_icon_container)
        RelativeLayout favIconContainer;
        @BindView(R.id.rootLayout)
        LinearLayout rootLayout;

        public FavoriteTeamsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            rootLayout.setOnClickListener(this);
            favIconContainer.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if(mListener ==null) return;

            try {
                switch (v.getId()) {
                    case R.id.fav_icon_container:
                        mListener.onFavoriteClick(getAdapterPosition());
                        break;
                    default:
                        mListener.onMatchClick(getAdapterPosition());
                        break;
                }
            } catch (NullPointerException e) {

                // pass
                e.printStackTrace();
            }
        }
    }
}
