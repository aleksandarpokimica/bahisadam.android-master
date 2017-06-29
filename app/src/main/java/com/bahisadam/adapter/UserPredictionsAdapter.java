package com.bahisadam.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bahisadam.MyApplication;
import com.bahisadam.R;
import com.bahisadam.interfaces.Constant;
import com.bahisadam.model.predictionleague.UserPredictionModel;
import com.bahisadam.utility.FontManager;
import com.bahisadam.utility.Utilities;

import java.util.ArrayList;
import java.util.List;

public class UserPredictionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements  Constant {

    public List<UserPredictionModel> data;
    private OnMatchClickListener mListener = null;
    private Activity activity;
    private UserPredictionViewHolder itemHolder;
    public Activity getActivity() {
        return activity;
    }


    public UserPredictionsAdapter(ArrayList<UserPredictionModel> data, Activity activity) {
        this.data = data;
        this.activity = activity;
    }

    public void refreshUI() {
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new UserPredictionsAdapter.UserPredictionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.prediction_userprediction_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        itemHolder = (UserPredictionViewHolder)holder;
        UserPredictionModel model = data.get(position);

        this.setOnClickListener(new UserPredictionsAdapter.OnMatchClickListener() {
            @Override
            public void onMatchClick(int position) {

            }
        });

        renderDetail(model);
    }

    public void renderDetail(UserPredictionModel match) {
        itemHolder.homeTeam.setText(Utilities.abbreviateTeamName(match.home_team.team_name_tr));
        itemHolder.awayTeam.setText(Utilities.abbreviateTeamName(match.away_team.team_name_tr));
        itemHolder.matchDate.setText(Utilities.formatDate(match.match_id.match_date, "EEE HH:mm"));
        itemHolder.halfTimeHomeScore.setText(Integer.toString(match.match_id.half_time_home_score));
        itemHolder.halfTimeAwayScore.setText(Integer.toString(match.match_id.half_time_away_score));
        itemHolder.forecast.setText(match.forecast);
        itemHolder.homeGoals.setText(Integer.toString(match.match_id.home_goals));
        itemHolder.awayGoals.setText(Integer.toString(match.match_id.away_goals));
        if(match.forecast != null &&  match.match_id.odds != null && match.match_id.odds.getIddaa() != null && match.match_id.odds.getIddaa().containsKey(match.forecast)) {
            itemHolder.odd.setText(Utilities.formatNumber(match.match_id.odds.getIddaa().get(match.forecast), "#.##"));
        }


        Typeface tf = FontManager.getTypeface(activity, FontManager.FONTAWESOME);
        itemHolder.success.setTypeface(tf);

        String text;
        int color;


        if(match.success == true) {
            text = activity.getString(R.string.fa_check);
            color = Color.parseColor("#008B8B");

        } else {
            if(match.match_id.result_type.equals(PLAYED)) {
                text = activity.getString(R.string.fa_close);
                color = Color.parseColor("#ff0000");
            } else {
                color = Color.parseColor("#404040");
                text = activity.getString(R.string.fa_hourglass_o);
            }
        }

        itemHolder.success.setText(text);
        itemHolder.success.setTextColor(color);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnMatchClickListener {
        void onMatchClick(int position);
    }

    public void setOnClickListener(UserPredictionsAdapter.OnMatchClickListener listener){
        mListener = listener;
    }


    class UserPredictionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        @BindView(R.id.prediction_row)
        LinearLayout rootLayout;

        @BindView(R.id.home_team)
        TextView homeTeam;

        @BindView(R.id.away_team)
        TextView awayTeam;

        @BindView(R.id.match_date)
        TextView matchDate;

        @BindView(R.id.success)
        TextView success;

        @BindView(R.id.forecast)
        TextView forecast;

        @BindView(R.id.home_goals)
        TextView homeGoals;

        @BindView(R.id.away_goals)
        TextView awayGoals;

        @BindView(R.id.odd)
        TextView odd;

        @BindView(R.id.half_time_home_score)
        TextView halfTimeHomeScore;

        @BindView(R.id.half_time_away_score)
        TextView halfTimeAwayScore;

        public UserPredictionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            rootLayout.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if(mListener ==null) return;

            try {

            } catch (NullPointerException e) {

                // pass
                e.printStackTrace();
            }
        }
    }
}
