package com.bahisadam.view;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import com.bahisadam.R;
import com.bahisadam.adapter.PredictionFragmentPagerAdapter;
import com.bahisadam.interfaces.RestClient;
import com.bahisadam.model.predictionleague.PredictionLeagueModel;
import com.bahisadam.services.PredictionBusService;
import com.google.firebase.analytics.FirebaseAnalytics;
import org.greenrobot.eventbus.EventBus;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author GorkemKarayel on 27.04.2017.
 */

public class PredictionLeagueActivity extends BaseActivity {

    @BindView(R.id.prediction_tabs)TabLayout mTabLayout;
    @BindView(R.id.prediction_viewpager)ViewPager mViewPager;
    @BindView(R.id.toolbar)Toolbar mToolbar;
    private RestClient restClient;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void api(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    protected void onCreate(Bundle instant) {
        super.onCreate(instant);
        setContentView(R.layout.prediction_activity);
        getPredictionData();
        ButterKnife.bind(this);
        setupViewPager(mViewPager);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.tahmin_ligi);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setCurrentScreen(this, getString(R.string.tahmin_ligi), null /* class override */);

    }

    private void getPredictionData() {
        restClient.predictionLeague().enqueue(new Callback<PredictionLeagueModel>() {
            @Override
            public void onResponse(Call<PredictionLeagueModel> call, Response<PredictionLeagueModel> response) {
                EventBus.getDefault().post(new PredictionBusService(null, response.body()));
            }

            @Override
            public void onFailure(Call<PredictionLeagueModel> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        PredictionFragmentPagerAdapter adapter = new PredictionFragmentPagerAdapter(getSupportFragmentManager(),
                getResources().getStringArray(R.array.tl_tabtitte));
        viewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(viewPager);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, PredictionLeagueActivity.class);
        context.startActivity(starter);
    }
}
