package com.bahisadam.view;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;

import com.bahisadam.MyApplication;
import com.bahisadam.R;
import com.bahisadam.fragment.*;
import com.bahisadam.interfaces.Constant;
import com.bahisadam.interfaces.RestClient;
import com.bahisadam.model.LeagueMatchList;

import com.bahisadam.model.news.newsmodel.NewsModel;
import com.bahisadam.model.predictionleague.PredictionLeagueModel;
import com.bahisadam.services.NewsBusService;
import com.bahisadam.services.PredictionBusService;

import com.bahisadam.utility.Preferences;
import com.bahisadam.utility.Utilities;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeActivity extends BaseActivity implements Constant, AppBarLayout.OnOffsetChangedListener {
    private static final int PERCENTAGE_TO_SHOW_IMAGE = 20;
    private final static String TAG = HomeActivity.class.getSimpleName();
    private static WeakReference<HomeActivity> mContext;
    public WebViewFragment wView;
    public LeagueMatchList mFavMatchList;
    public FootballFragment footballFragment;
    private int mMaxScrollSize;
    private boolean mIsImageHidden;
    private Toolbar mToolbar;
    private CoordinatorLayout coordinatorLayout;
    private boolean homePage;
    private DrawerLayout mDrawer;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private RestClient restClient;
    private View calendarContainer;
    public OnQueryTextListener queryListener;
    // Make sure to be using android.support.v7.app.ActionBarDrawerToggle version.
    // The android.support.v4.app.ActionBarDrawerToggle has been deprecated.
    private ActionBarDrawerToggle drawerToggle;
    /**
     * The {@code FirebaseAnalytics} used to record screen views.
     */
    // [START declare_analytics]
    private FirebaseAnalytics mFirebaseAnalytics;

    // HomeActivity Instance
    public static HomeActivity getInstance() {
        return mContext.get();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.queryListener = null;

        if (Utilities.isNetworkAvailable(getApplicationContext())) {
            if (Preferences.isLogged()) Utilities.login(this, false);
        } else {
               Utilities.showSnackBarInternet(this, coordinatorLayout,
                    getString(R.string.failed_to_connect_with_server));
            Log.e(TAG, "Bağlantı Sağlanamadı");
        }

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.i("key", key);
                Log.i("value", value.toString());
                if (key.equals("match_id")) {
                    Bundle bundle = new Bundle();
                    bundle.putString(DetailPageActivity.MATCH_ID, value.toString());
                    String type = getIntent().getExtras().get("type").toString();
                    if (type.equals("lineup")) {
                        bundle.putString(DetailPageActivity.ARG_REUSULT_TYPE, LINEUP);
                    } else {
                        bundle.putString(DetailPageActivity.ARG_REUSULT_TYPE, PLAYING);
                    }

                    Utilities.openMatchDetails(HomeActivity.this, bundle);
                    break;
                } else if (key.equals("news_id")) {
                    Utilities.openNewsDetailActivity(HomeActivity.this, value.toString());
                    break;
                }
            }
        }

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        // Create Remote Config Setting to enable developer mode.
        // Fetching configs from the server is normally limited to 5 requests per hour.
        // Enabling developer mode allows many more requests to be made per hour, so developers
        // can test different config values during development.
        // [START enable_dev_mode]

        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                //        .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);
        // [END enable_dev_mode]


        // Set default Remote Config values. In general you should have in app defaults for all
        // values that you may configure using Remote Config later on. The idea is that you
        // use the in app defaults and when you need to adjust those defaults, you set an updated
        // value in the App Manager console. Then the next time you application fetches from the
        // server, the updated value will be used. You can set defaults via an xml file like done
        // here or you can set defaults inline by using one of the other setDefaults methods.S
        // [START set_default_values]
        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);
        // [END set_default_values]

        fetchConfig();

        setContentView(R.layout.activity_home);

        if (Preferences.isLogged()) Utilities.login(this, false);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        mContext = new WeakReference<>(HomeActivity.this);

        Intent intent = getIntent();
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // ...From section above...
        // Find our drawer view
        NavigationView nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);
        mDrawer.addDrawerListener(drawerToggle);
        nvDrawer.setItemIconTintList(null);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setSupportActionBar(mToolbar);

        drawerToggle = setupDrawerToggle();
        drawerToggle.setDrawerIndicatorEnabled(true);
        // Tie DrawerLayout events to the ActionBarToggle

        initFooterToolbar();

        int resultCode = intent.getIntExtra(PAGE, -1);
        int id = intent.getIntExtra(ID, -1);

        /*calendarContainer = findViewById(R.id.calendarContainer);
        calendarContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (footballFragment != null && homePage) {
                    SelectDateFragment dialog =  SelectDateFragment.newInstance(footballFragment);
                    dialog.show(getSupportFragmentManager(),"DatePicker");
                }
            }
        }); */
        switch (resultCode) {
            case RESULT_LOAD_HOME_PAGE:
                loadHomePage();
                break;
            case RESULT_LOAD_TOURNAMENTS:
                loadTournaments();
                break;
            case RESULT_LOAD_LIVE:
                loadLive();
                break;
            case RESULT_LOAD_FAVORITE:
                loadFavorite();
                break;
            case RESULT_LOAD_TEAM_PAGE:
                loadTeam(id);
                break;
            case RESULT_LOAD_PLAYER:
                String player_id = intent.getStringExtra(PLAYER_ID);
                String player_name = intent.getStringExtra(PLAYER);
                loadPlayer(player_id, player_name);
                break;
            default:
                loadHomePage();
                break;
        }
        //resultCode = -1;
         /* Load Fragment For First Time */
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
//        EventBus.getDefault().register(this);

    }

    /**
     * Fetch welcome message from server.
     */
    private void fetchConfig() {
        long cacheExpiration = 3600 * 12; // 1 hour in seconds.
        // If in developer mode cacheExpiration is set to 0 so each fetch will retrieve values from
        // the server.
        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }

        // [START fetch_config_with_callback]
        // cacheExpirationSeconds is set to cacheExpiration here, indicating that any previously
        // fetched and cached config would be considered expired because it would have been fetched
        // more than cacheExpiration seconds ago. Thus the next fetch would go to the server unless
        // throttling is in progress. The default expiration duration is 43200 (12 hours).
        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            // Once the config is successfully fetched it must be activated before newly fetched
                            // values are returned.
                            mFirebaseRemoteConfig.activateFetched();
                            MyApplication.sUse_Logo = mFirebaseRemoteConfig.getBoolean("is_logo_enabled");
                        } else {

                        }
                    }
                });
        // [END fetch_config_with_callback]
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(Color.WHITE);
        searchAutoComplete.setBackgroundResource(R.drawable.divider);
        searchAutoComplete.setHighlightColor(Color.WHITE);
        searchAutoComplete.setFadingEdgeLength(10);
        searchAutoComplete.setHint(R.string.search_hint);
        searchAutoComplete.setHintTextColor(Color.parseColor("#AAFFFFFF"));
        searchAutoComplete.setTextSize(14);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit");

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                queryListener.onQueryChanged(newText);

                return false;
            }
        });

        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if(item.getItemId() == R.id.search){
                item.setVisible(footballFragment != null && homePage);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
            /*case R.id.action_calendar:
                if (footballFragment != null) {
                    SelectDateFragment dialog =  SelectDateFragment.newInstance(footballFragment);
                    dialog.show(getSupportFragmentManager(),"DatePicker");
                }
                return true;*/
        }

        return super.onOptionsItemSelected(item);
    }

    //from action bar
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE 1: Make sure to override the method with only a single `Bundle` argument
    // Note 2: Make sure you implement the correct `onPostCreate(Bundle savedInstanceState)` method.
    // There are 2 signatures and only `onPostCreate(Bundle state)` shows the hamburger icon.
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, R.string.drawer_open, R.string.drawer_close);
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        //Fragment fragment;
        homePage = false;
        switch (menuItem.getItemId()) {
            case R.id.home_page:
                loadHomePage();
                homePage = true;
                break;
            case R.id.iddaa_bulletin:
                loadFragment(WebViewFragment.newInstance("http://www.skoradam.com/iddaa-bulteni"));
                break;
            case R.id.forecast_league:
                PredictionLeagueActivity.start(this);
                homePage = false;
                break;
            case R.id.news:
                loadNewPage();
                homePage = false;
                break;
            case R.id.transfers:
                loadFragment(WebViewFragment.newInstance("http://www.skoradam.com/transferler"));
                break;
            case R.id.privacy:
                loadFragment(WebViewFragment.newInstance("http://www.skoradam.com/gizlilik-sozlesmesi"));
                break;
            default:
                break;
        }

        setActiveToolbarItem(-1);
        ActionBar currentBar = getSupportActionBar();
        if (currentBar != null) {

            currentBar.setDisplayShowHomeEnabled(true);
        }
        // Highlight the selected item has been done by NavigationView
        //menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }


    /* Get Coordinate Layout */
    public CoordinatorLayout getCoordinateLayout() {
        return coordinatorLayout;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int currentScrollPercentage = (Math.abs(i)) * 100
                / mMaxScrollSize;

        if (currentScrollPercentage >= PERCENTAGE_TO_SHOW_IMAGE) {
            if (!mIsImageHidden) {
                mIsImageHidden = true;

                // ViewCompat.animate(mFab).scaleY(0).scaleX(0).start();
            }
        }

        if (currentScrollPercentage < PERCENTAGE_TO_SHOW_IMAGE) {
            if (mIsImageHidden) {
                mIsImageHidden = false;
                //ViewCompat.animate(mFab).scaleY(1).scaleX(1).start();
            }
        }
    }


    @Override
    public void api(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public void loadHomePage() {

        loadFragment(new FootballFragment(),true);
        setActiveToolbarItem(0);
        mToolbar.setVisibility(View.VISIBLE);
        mToolbar.setTitle(getString(R.string.app_name));
    }

    @Override
    public void loadNewPage() {
        getNewsData();
        homePage = false;
        loadFragment(new NewsFragment());
        setActiveToolbarItem(0);
        mToolbar.setVisibility(View.VISIBLE);
        mToolbar.setTitle(getString(R.string.news));
        mFirebaseAnalytics.setCurrentScreen(this, getString(R.string.news), null /* class override */);
    }

    private void getNewsData() {
        restClient.getNews().enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                EventBus.getDefault().post(new NewsBusService(null,response));
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {

            }
        });
    }

    public void loadPredictionLeague() {
        homePage = false;
        loadFragment(new PredictionLeagueCurrentWeekFragment());
        mToolbar.setVisibility(View.VISIBLE);
        mToolbar.setTitle(getString(R.string.tahmin_ligi));
        mFirebaseAnalytics.setCurrentScreen(this, getString(R.string.tahmin_ligi), null /* class override */);
    }


    @Override
    public void loadLive() {
        homePage = false;
        LiveFragment fragment = new LiveFragment();
        loadFragment(fragment);
        setActiveToolbarItem(2);
        mToolbar.setVisibility(View.VISIBLE);
        mToolbar.setTitle(getString(R.string.live_toolbar));
        mFirebaseAnalytics.setCurrentScreen(this, getString(R.string.live_toolbar), null /* class override */);
    }

    @Override
    public void loadTournaments() {
        homePage = false;
        loadFragment(new TournamentsFragment());
        setActiveToolbarItem(1);
        mToolbar.setVisibility(View.VISIBLE);
        mToolbar.setTitle(getString(R.string.tournaments));
        mFirebaseAnalytics.setCurrentScreen(this, getString(R.string.tournaments), null /* class override */);
    }


    @Override
    public void loadFavorite() {
//        Intent intent = new Intent(this, FavoritesActivity.class);
//        startActivity(intent);
        FavoriteFragment fragment = new FavoriteFragment();
        loadFragment(fragment);
        setActiveToolbarItem(3);
        mToolbar.setVisibility(View.VISIBLE);
        mToolbar.setTitle(getString(R.string.title_activity_favorites));
        mFirebaseAnalytics.setCurrentScreen(this, getString(R.string.title_activity_favorites), null /* class override */);
    }

    public void loadTeam(int id) {
        Utilities.openTeamDetails(this, id);
        setActiveToolbarItem(1);
        mToolbar.setVisibility(View.VISIBLE);
        mToolbar.setTitle("");
        mFirebaseAnalytics.setCurrentScreen(this, "team detail " + Integer.toString(id), null /* class override */);
    }

    //http://www.skoradam.com/oyuncu/lens-jeremain/sr:player:18115
    public void loadPlayer(String id, String player) {
        homePage = false;
        Intent playerIntent = new Intent(HomeActivity.this, PlayerDetailsActivity.class);
        playerIntent.putExtra("ID", id);
        playerIntent.putExtra("Player", player);
        startActivity(playerIntent);
        /*loadFragment(WebViewFragment.newInstance("http://www.skoradam.com/oyuncu/" + player.replace(' ', '-').toLowerCase() +
                "/" + id));*/
        setActiveToolbarItem(-1);
        mToolbar.setVisibility(View.GONE);
    }

    public void loadFragment(Fragment fragment){
        loadFragment(fragment, false);
    }

    public void loadFragment(Fragment fragment, boolean homePage) {
        supportInvalidateOptionsMenu();
        this.homePage = homePage;

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
            if(fragment instanceof FootballFragment) {
                footballFragment = (FootballFragment) fragment;
            } else  {
                footballFragment = null;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (homePage) {
            createDialog(this);
        } else {
            loadHomePage();
            setActiveToolbarItem(0);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

        super.onSaveInstanceState(outState, outPersistentState);
    }

    // Assign the listener implementing events interface that will receive the events
    public void setOnQueryTextListener(OnQueryTextListener listener) {
        this.queryListener = listener;
    }

    public interface OnQueryTextListener {
        void onQueryChanged(String query);
    }

    /*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }*/
}
