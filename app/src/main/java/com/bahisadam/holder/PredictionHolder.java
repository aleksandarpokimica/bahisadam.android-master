package com.bahisadam.holder;

import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bahisadam.R;
import com.txusballesteros.widgets.FitChart;

public class PredictionHolder extends CardHolderBase {
    public @BindView(R.id.oddsIdda1) TextView oddsIdda1;
    public @BindView(R.id.oddsIddaX) TextView oddsIddaX;
    public @BindView(R.id.oddsIdda2) TextView oddsIdda2;
    public @BindView(R.id.IY1) TextView IY1;
    public @BindView(R.id.IYX) TextView IYX;
    public @BindView(R.id.IY2) TextView IY2;
    public @BindView(R.id.idda1x) TextView idda1x;
    public @BindView(R.id.idda12) TextView idda12;
    public @BindView(R.id.iddax2) TextView iddax2;
    public @BindView(R.id.h1) TextView h1;
    public @BindView(R.id.hx) TextView hx;
    public @BindView(R.id.h2) TextView h2;
    public @BindView(R.id.iyalt15) TextView iyalt15;
    public @BindView(R.id.iyust15) TextView iyust15;
    public @BindView(R.id.alt) TextView alt;
    public @BindView(R.id.ust) TextView ust;
    public @BindView(R.id.alt15) TextView alt15;
    public @BindView(R.id.ust15) TextView ust15;
    public @BindView(R.id.alt35) TextView alt35;
    public @BindView(R.id.ust35) TextView ust35;
    public @BindView(R.id.kgv) TextView kgv;
    public @BindView(R.id.kgy) TextView kgy;
    public @BindView(R.id.gs01) TextView gs01;
    public @BindView(R.id.gs23) TextView gs23;
    public @BindView(R.id.gs46) TextView gs46;
    public @BindView(R.id.gs7p) TextView gs7p;
    public @BindView(R.id.sf1x) TextView sf1x;
    public @BindView(R.id.sfx1) TextView sfx1;
    public @BindView(R.id.sf11) TextView sf11;
    public @BindView(R.id.sf12) TextView sf12;
    public @BindView(R.id.sf21) TextView sf21;
    public @BindView(R.id.sf22) TextView sf22;
    public @BindView(R.id.sf2X) TextView sf2x;
    public @BindView(R.id.sfx2) TextView sfx2;
    public @BindView(R.id.sfxx) TextView sfxx;

    public @BindView(R.id.submit) TextView submit;
    public @BindView(R.id.reason)
    EditText reasonET;
    public View view;
    public PredictionHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        view = itemView;
    }
}