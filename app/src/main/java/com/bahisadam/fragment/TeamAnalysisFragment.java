package com.bahisadam.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bahisadam.R;
import com.bahisadam.model.MatchPOJO;
import com.bahisadam.model.teamInfo.Period;
import com.bahisadam.utility.Utilities;
import com.bahisadam.view.TeamDetailsActivity;
import com.squareup.picasso.Picasso;
import com.txusballesteros.widgets.FitChart;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ali on 6/8/17.
 */
public class TeamAnalysisFragment extends Fragment {

    @BindView(R.id.tv_goal_scored_1) TextView tv_sc_1;
    @BindView(R.id.tv_goal_scored_2) TextView tv_sc_2;
    @BindView(R.id.tv_goal_scored_3) TextView tv_sc_3;
    @BindView(R.id.tv_goal_scored_4) TextView tv_sc_4;
    @BindView(R.id.tv_goal_scored_5) TextView tv_sc_5;
    @BindView(R.id.tv_goal_scored_6) TextView tv_sc_6;

    @BindView(R.id.tv_goal_rec_1) TextView tv_rc_1;
    @BindView(R.id.tv_goal_rec_2) TextView tv_rc_2;
    @BindView(R.id.tv_goal_rec_3) TextView tv_rc_3;
    @BindView(R.id.tv_goal_rec_4) TextView tv_rc_4;
    @BindView(R.id.tv_goal_rec_5) TextView tv_rc_5;
    @BindView(R.id.tv_goal_rec_6) TextView tv_rc_6;

    @BindView(R.id.team_name) TextView teamName;
    @BindView(R.id.team_img) ImageView teamImg;

    @BindView(R.id.chartPos)FitChart posChart;
    @BindView(R.id.tv_pos) TextView tv_pos;

    @BindView(R.id.tv_scored) TextView tv_scored;
    @BindView(R.id.tv_received) TextView tv_received;
    @BindView(R.id.tv_scored_foot) TextView tv_scored_foot;
    @BindView(R.id.tv_scored_head) TextView tv_scored_head;
    @BindView(R.id.tv_shots_total) TextView tv_shots_total;
    @BindView(R.id.tv_shots_on_target) TextView tv_shots_on_target;
    @BindView(R.id.tv_shots_off_target) TextView tv_shots_off_target;
    @BindView(R.id.tv_save) TextView tv_save;
    @BindView(R.id.tv_freekick) TextView tv_freekick;
    @BindView(R.id.tv_corner) TextView tv_corner;

    private TeamDetailsActivity mActivity;

    public static TeamAnalysisFragment newInstance() {

        Bundle args = new Bundle();

        TeamAnalysisFragment fragment = new TeamAnalysisFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TeamAnalysisFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (TeamDetailsActivity)getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_team_analiz, container, false);
        ButterKnife.bind(this,view);

        if (mActivity.mStats != null && mActivity.mStats.getGoaltime_statistics() != null) {
            Period[] scoredList = mActivity.mStats.getGoaltime_statistics().getScored().getPeriod();
            Period[] concededList = mActivity.mStats.getGoaltime_statistics().getConceded().getPeriod();

            for (Period period : scoredList) {
                if (period.getName().equals("0-15")) {
                    tv_sc_1.setText(period.getValue());
                } else if (period.getName().equals("16-30")) {
                    tv_sc_2.setText(period.getValue());
                } else if (period.getName().equals("31-45")) {
                    tv_sc_3.setText(period.getValue());
                } else if (period.getName().equals("46-60")) {
                    tv_sc_4.setText(period.getValue());
                } else if (period.getName().equals("61-75")) {
                    tv_sc_5.setText(period.getValue());
                } else if (period.getName().equals("76-90")) {
                    tv_sc_6.setText(period.getValue());
                }
            }

            for (Period period : concededList) {
                if (period.getName().equals("0-15")) {
                    tv_rc_1.setText(period.getValue());
                } else if (period.getName().equals("16-30")) {
                    tv_rc_2.setText(period.getValue());
                } else if (period.getName().equals("31-45")) {
                    tv_rc_3.setText(period.getValue());
                } else if (period.getName().equals("46-60")) {
                    tv_rc_4.setText(period.getValue());
                } else if (period.getName().equals("61-75")) {
                    tv_rc_5.setText(period.getValue());
                } else if (period.getName().equals("76-90")) {
                    tv_rc_6.setText(period.getValue());
                }
            }
        }


        teamName.setText(TeamDetailsActivity.teamName);
        Utilities.loadLogoToView(teamImg, TeamDetailsActivity.mTeamId);

        try {
            Integer pos = Integer.parseInt(mActivity.mStats.getBall_possession());
            posChart.setValue(pos);
            tv_pos.setText(pos+"");
        } catch (Exception ex ) {
            posChart.setVisibility(View.GONE);
        }

        tv_scored.setText(mActivity.mStats.getGoals_scored()+"");
        tv_received.setText(mActivity.mStats.getGoals_conceded()+"");
        tv_scored_foot.setText(mActivity.mStats.getGoals_by_foot()+"");
        tv_scored_head.setText(mActivity.mStats.getGoals_by_head()+"");
        tv_shots_total.setText(mActivity.mStats.getGoal_attempts()+"");
        tv_shots_on_target.setText(mActivity.mStats.getShots_on_goal()+"");
        tv_shots_off_target.setText(mActivity.mStats.getShots_off_goal()+"");
        tv_save.setText(mActivity.mStats.getShots_blocked()+"");
        tv_freekick.setText(mActivity.mStats.getFree_kicks()+"");
        tv_corner.setText(mActivity.mStats.getCorner_kicks()+"");

        return view;
    }
}
