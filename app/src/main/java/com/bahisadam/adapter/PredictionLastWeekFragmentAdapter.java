package com.bahisadam.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bahisadam.R;
import com.bahisadam.model.predictionleague.PredictionLeagueModel;
import com.bahisadam.model.predictionleague.PredictionUserModel;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bahisadam.utility.Utilities;

/**
 * @author GorkemKarayel on 28.04.2017.
 */

public class PredictionLastWeekFragmentAdapter extends RecyclerView.Adapter<PredictionLastWeekFragmentAdapter.MyViewHolder> {

    private PredictionLeagueModel predictionLeagueModel;
    private Context mContext;
    private Double scoreFilter;
    private PredictionLastWeekFragmentAdapter.OnUserClickListener mListener = null;
    private Activity mActivity;

    public PredictionLastWeekFragmentAdapter(Activity activity, Context mContext, PredictionLeagueModel predictionLeagueModel) {
        this.predictionLeagueModel = predictionLeagueModel;
        this.mContext = mContext;
        mActivity = activity;
    }

    public interface OnUserClickListener {
        void onUserClick(int position);
    }

    public void setOnClickListener(PredictionLastWeekFragmentAdapter.OnUserClickListener listener){
        mListener = listener;
    }

    @Override
    public PredictionLastWeekFragmentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PredictionLastWeekFragmentAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.prediction_user_item, parent, false));
    }

    @Override
    public void onBindViewHolder(PredictionLastWeekFragmentAdapter.MyViewHolder holder, int position) {
        if (position%2 == 0){
            holder.mComponent.setBackgroundColor(mContext.getResources().getColor(R.color.tl_component_color));
        } else {
            holder.mComponent.setBackgroundColor(mContext.getResources().getColor(R.color.md_white_1000));
        }

        final ArrayList<PredictionUserModel> users = predictionLeagueModel.data.previous.users;
        holder.mRank.setText(Integer.toString(position  + 1));
        holder.mUserName.setText(String.valueOf(users.get(position).user_id.nickname));
        holder.mTotalMatch.setText(String.valueOf(users.get(position).total));
        holder.mSuccessRate.setText(String.valueOf(users.get(position).totalSuccess));
        scoreFilter = Math.floor(users.get(position).score * 100) / 100;
        holder.mSkor.setText(String.valueOf(scoreFilter));

        Utilities.fontAwesome(mContext, holder.mThreeDot, R.string.fa_ellipsis_v);


        this.setOnClickListener(new PredictionLastWeekFragmentAdapter.OnUserClickListener() {
            @Override
            public void onUserClick(int position) {
                PredictionUserModel model = users.get(position);
                Utilities.openUserPredictions(mActivity, model._id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return predictionLeagueModel.data.previous.users.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        @BindView(R.id.tl_username)TextView mUserName;
        @BindView(R.id.tl_totalmatch)TextView mTotalMatch;
        @BindView(R.id.tl_successrate)TextView mSuccessRate;
        @BindView(R.id.tl_skor)TextView mSkor;
        @BindView(R.id.tl_component) LinearLayout mComponent;
        @BindView(R.id.tl_rank) TextView mRank;
        @BindView(R.id.tl_threedot) TextView mThreeDot;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,itemView);
            mComponent.setOnClickListener(this);
        }

        public void onClick(View v) {
            if (mListener == null) return;

            mListener.onUserClick(getAdapterPosition());
        }
    }
}