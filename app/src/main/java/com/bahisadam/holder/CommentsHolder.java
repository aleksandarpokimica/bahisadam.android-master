package com.bahisadam.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bahisadam.R;
import com.txusballesteros.widgets.FitChart;

public class CommentsHolder extends CardHolderBase {
    public @BindView(R.id.noComments) TextView noComments;
    public @BindView(R.id.comments)
    RecyclerView commentsView;

    public CommentsHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);

    }
}
