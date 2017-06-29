package com.bahisadam.holder;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bahisadam.R;
import com.bahisadam.adapter.DetailedPageAdapter;
import com.txusballesteros.widgets.FitChart;

public class StandingsHolder extends CardHolderBase {
    public @BindView(R.id.homeTeamNum) TextView homeTeamNum;
    public @BindView(R.id.awayTeamNum) TextView awayTeamNum;
    public @BindView(R.id.homeTeamName) TextView homeTeamName;
    public @BindView(R.id.awayTeamName) TextView awayTeamName;
    public @BindView(R.id.homeTeamOm) TextView homeTeamOm;
    public @BindView(R.id.homeTeamG) TextView homeTeamG;
    public @BindView(R.id.homeTeamB) TextView homeTeamB;
    public @BindView(R.id.homeTeamPTS) TextView homeTeamPTS;
    public @BindView(R.id.homeTeamM) TextView homeTeamM;
    public @BindView(R.id.awayTeamOm) TextView awayTeamOm;
    public @BindView(R.id.awayTeamG) TextView awayTeamG;
    public @BindView(R.id.awayTeamB) TextView awayTeamB;
    public @BindView(R.id.awayTeamPTS) TextView awayTeamPTS;
    public @BindView(R.id.awayTeamM) TextView awayTeamM;
    public @BindView(R.id.homeTeamLogoStandings)
    ImageView homeTeamLogo;
    public @BindView(R.id.awayTeamLogoStandings) ImageView awayTeamLogo;
    public @BindView(R.id.homeTeamLayout)
    LinearLayout homeTeamLayout;
    public @BindView(R.id.awayTeamLayout) LinearLayout awayTeamLayout;
    public @BindView(R.id.standingsRoot) LinearLayout rootLayout;
    public @BindView(R.id.standings) LinearLayout standingsLayout;
    public @BindView(R.id.awayStandings) LinearLayout awayTitleLayout;
    public @BindView(R.id.homeStandings) LinearLayout homeTitleLayout;
    public @BindView(R.id.generalStandings) LinearLayout generalTitleLayout;
    public @BindView(R.id.generalStandingsUnderline) View generalStandingsUnderline;
    public @BindView(R.id.homeStandingsUnderline) View homeStandingsUnderline;
    public @BindView(R.id.awayStandingsUnderline) View awayStandingsUnderline;
    public @BindView(R.id.generalStandingsText) TextView generalTv;
    public @BindView(R.id.homeStandingsText) TextView homeTv;
    public @BindView(R.id.awayStandingsText) TextView awayTv;
    public TabHost tabHost;
    public StandingsHolder(View view){
        super(view);
        ButterKnife.bind(this,view);
        tabHost = (TabHost) view.findViewById(android.R.id.tabhost);

        tabHost.setup();
        TabHost.TabSpec tabSpec;

        Context ctx = tabHost.getContext();

        tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setIndicator(ctx.getString(R.string.main));
        tabSpec.setContent(R.id.tab1);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setIndicator(ctx.getString(R.string.homeTeam));
        tabSpec.setContent(R.id.tab1);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag3");
        tabSpec.setContent(R.id.tab1);
        tabSpec.setIndicator(ctx.getString(R.string.awayTeam));
        tabHost.addTab(tabSpec);



        ViewGroup.LayoutParams params = tabHost.getTabWidget().getChildAt(0).getLayoutParams();

        params.height = (int) (params.height * 0.7);
        for(int i =0 ;i < tabHost.getTabWidget().getChildCount(); i++) {
            View child = tabHost.getTabWidget().getChildAt(i);
            child.setLayoutParams(params);
            child.setBackgroundResource(R.drawable.tab_selector);
            TextView tv =  (TextView) child.findViewById(android.R.id.title);
            tv.setAllCaps(false);
        }
        tabHost.setCurrentTabByTag("tag1");


    }
}
