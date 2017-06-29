package com.bahisadam.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bahisadam.R;
import com.bahisadam.adapter.DetailedPageAdapter;
import com.txusballesteros.widgets.FitChart;


public class CardHolderBase extends RecyclerView.ViewHolder{

    public CardHolderBase(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
