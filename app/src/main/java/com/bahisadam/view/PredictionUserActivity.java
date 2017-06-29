package com.bahisadam.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bahisadam.R;
import com.bahisadam.fragment.*;
import com.bahisadam.interfaces.Constant;
import com.bahisadam.interfaces.RestClient;
import java.lang.ref.WeakReference;

public class PredictionUserActivity extends BaseActivity implements Constant {

    private static WeakReference<PredictionUserActivity> mContext;
    private RestClient restClient;
    private String userId;

    public static final String ARG_BUNDLE = "bundle";
    @BindView(R.id.toolbar)Toolbar mToolbar;

    // HomeActivity Instance
    public static PredictionUserActivity getInstance() {
        return mContext.get();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_predictions);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.activity_user_predictions_title);

        userId = this.getIntent().getStringExtra(BaseActivity.ID);
        loadFragment(UserPredictionsFragment.newInstance(userId));
    }

    public void loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void api(RestClient restClient) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, PredictionUserActivity.class);
        context.startActivity(starter);
    }
}
