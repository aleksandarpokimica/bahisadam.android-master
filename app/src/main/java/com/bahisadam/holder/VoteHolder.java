package com.bahisadam.holder;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bahisadam.R;
import com.bahisadam.adapter.DetailedPageAdapter;
import com.txusballesteros.widgets.FitChart;

public class VoteHolder extends CardHolderBase {
    public @BindView(R.id.chart1)
    FitChart chart1;
    public @BindView(R.id.chartx) FitChart chartx;
    public @BindView(R.id.chart2) FitChart chart2;
    public @BindView(R.id.labelX)
    TextView labelX;
    public @BindView(R.id.label1) TextView label1;
    public @BindView(R.id.label2) TextView label2;
    public @BindView(R.id.chartsHomeTeam) TextView chartsHomeTeam;
    public @BindView(R.id.chartsAwayTeam) TextView chartsAwayTeam;
    public @BindView(R.id.vote1)
    FrameLayout vote1;
    public @BindView(R.id.vote2) FrameLayout vote2;
    public @BindView(R.id.voteX) FrameLayout voteX;
    public VoteHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);

    }
}
