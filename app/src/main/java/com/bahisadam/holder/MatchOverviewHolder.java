package com.bahisadam.holder;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bahisadam.R;

public class MatchOverviewHolder extends CardHolderBase {
    public @BindView(R.id.stadium_name) TextView stadiumName;
    public @BindView(R.id.stadium_capacity) TextView stadiumCapacity;
    public @BindView(R.id.tv) TextView tv;
    public @BindView(R.id.referee) TextView referee;
    public @BindView(R.id.tv_row) LinearLayout tvRow;
    public @BindView(R.id.referee_row) LinearLayout refereeRow;
    public @BindView(R.id.other_match_row) LinearLayout otherMatchRow;
    public @BindView(R.id.other_match) TextView otherMatch;
    public MatchOverviewHolder(View view){
        super(view);
        ButterKnife.bind(this,view);
    }
}