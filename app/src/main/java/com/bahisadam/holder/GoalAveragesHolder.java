package com.bahisadam.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bahisadam.R;

public class GoalAveragesHolder extends CardHolderBase {
    public @BindView(R.id.goalHomeTeam) TextView goalHomeTeam;
    public @BindView(R.id.goalHomeAG) TextView goalHomeAG;
    public @BindView(R.id.homeTeamLogo) ImageView homeTeamLogo;
    public @BindView(R.id.goalHomeYG) TextView goalHomeYG;
    public @BindView(R.id.awayTeamLogo) ImageView awayTeamLogo;
    public @BindView(R.id.goalAwayTeam) TextView goalAwayTeam;
    public @BindView(R.id.goalAwayAG) TextView goalAwayAG;
    public @BindView(R.id.goalAwayYG) TextView goalAwayYG;
    public GoalAveragesHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}

