package  com.bahisadam.adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
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
import com.bahisadam.fragment.FootballFragment;
import com.bahisadam.interfaces.Constant;
import com.bahisadam.model.MatchModel;
import com.bahisadam.model.TeamModel;
import com.bahisadam.utility.FontManager;
import com.bahisadam.utility.Preferences;
import com.bahisadam.utility.Utilities;
import com.bahisadam.view.DetailPageActivity;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FavoriteMatchesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements  Constant {

    public List<MatchModel> data;
    private OnMatchClickListener mListener = null;
    private Activity activity;
    private FavoriteMatchesFragment frag;
    private FavoriteMatchesViewHolder itemHolder;
    public Activity getActivity() {
        return activity;
    }


    public FavoriteMatchesAdapter(ArrayList<MatchModel> data, Activity activity, FavoriteMatchesFragment frag) {
        this.data = data;
        this.activity = activity;
        this.frag = frag;
    }

    public void refreshUI() {
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new FavoriteMatchesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_match_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        itemHolder = (FavoriteMatchesViewHolder)holder;
        MatchModel match = data.get(position);
        if(match.home_team == null || match.away_team == null) {
            return;
        }

        this.setOnClickListener(new FavoriteMatchesAdapter.OnMatchClickListener() {
            @Override
            public void onFavoriteClick(int position) {
                MatchModel match = data.get(position);
                Set<String> favoriteMatches = Preferences.getFavoriteMatches();

                Utilities.addRemoveMatchToFavorites(match._id, true, activity);

                FirebaseMessaging.getInstance().unsubscribeFromTopic(match._id);
                Utilities.paintFavoriteIconOntoWhiteBg(activity, itemHolder.favIcon, false);
                data.remove(position);

                favoriteMatches.remove(match._id);
                Preferences.setFavoriteMatches(favoriteMatches);

                refreshUI();
            }

            @Override
            public void onMatchClick(int position) {
                MatchModel match = data.get(position);
                if(match.home_team == null || match.away_team ==null)
                {
                    return;
                }

                Bundle bundle = new Bundle();
                bundle.putString(DetailPageActivity.MATCH_ID,match._id);
                bundle.putInt(DetailPageActivity.LEAGUE_ID,match.league_id);
                bundle.putString(DetailPageActivity.ARG_TEAM_HOME_NAME,match.home_team.team_name_tr);
                bundle.putString(DetailPageActivity.ARG_TEAM_AWAY_NAME,match.away_team.team_name_tr);
                bundle.putString(DetailPageActivity.ARG_REUSULT_TYPE,match.result_type);
                Utilities.openMatchDetails(getActivity(), bundle);
            }
        });

        renderTeams(match.home_team, match.away_team);
        renderScore(match);
        renderDetail(match);
        if(match.iddaa_code != null){
            itemHolder.matchCode.setText(match.iddaa_code);
        } else {
            itemHolder.matchCode.setText("-");
        }
    }

    public void renderTeams(TeamModel homeTeam , TeamModel awayTeam) {
        if(MyApplication.sUse_Logo) {
            Utilities.loadLogoToView(itemHolder.homeLogo, homeTeam._id);
            Utilities.loadLogoToView(itemHolder.awayLogo, awayTeam._id);
        }

        itemHolder.homeTeam.setText(Utilities.abbreviateTeamName(homeTeam.team_name_tr));
        itemHolder.awayTeam.setText(Utilities.abbreviateTeamName(awayTeam.team_name_tr));
    }

    public void renderScore(MatchModel match) {
        if(match.result_type.equals(Constant.NOT_PLAYED) || match.result_type.equals(Constant.POSTPONED) || match.result_type.equals(Constant.DELAYED) || match.result_type.equals(Constant.CANCELLED)) {
            itemHolder.matchScore.setText(Utilities.formatDate(match.match_date, "EEE HH:mm"));
        } else {
            itemHolder.matchScore.setText(match.home_goals +" : " + match.away_goals);
        }
    }

    public void renderDetail(MatchModel match) {
        if(match.result_type.equals(Constant.NOT_PLAYED)) {
        }
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

    public void setOnClickListener(FavoriteMatchesAdapter.OnMatchClickListener listener){
        mListener = listener;
    }


    class FavoriteMatchesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        @BindView(R.id.home_logo) ImageView homeLogo;
        @BindView(R.id.away_logo) ImageView awayLogo;
        @BindView(R.id.home_team) TextView homeTeam;
        @BindView(R.id.away_team) TextView awayTeam;
        @BindView(R.id.match_score) TextView matchScore;
        @BindView(R.id.match_code) TextView matchCode;
        @BindView(R.id.fav_icon) TextView favIcon;
        @BindView(R.id.fav_icon_container)
        RelativeLayout favIconContainer;
        @BindView(R.id.rootLayout)
        LinearLayout rootLayout;


        public FavoriteMatchesViewHolder(View itemView) {
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
                    default :
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
