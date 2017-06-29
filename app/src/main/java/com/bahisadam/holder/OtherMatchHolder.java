package com.bahisadam.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bahisadam.R;
import com.txusballesteros.widgets.FitChart;

public class OtherMatchHolder extends RecyclerView.ViewHolder {
    /// public @BindView(R.id.other_match_home_score) TextView otherMatchHomeScore;
    //  public @BindView(R.id.other_match_away_score) TextView otherMatchAwayScore;
    public OtherMatchHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
