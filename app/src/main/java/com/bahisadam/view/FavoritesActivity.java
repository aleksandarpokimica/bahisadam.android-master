package com.bahisadam.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bahisadam.R;
import com.bahisadam.fragment.*;
import com.bahisadam.interfaces.Constant;
import com.bahisadam.interfaces.RestClient;
import com.bahisadam.model.*;
import com.bahisadam.model.requests.ListDeviceFavoritesRequest;
import com.bahisadam.utility.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesActivity extends BaseActivity  {
    private FavoritesActivity.SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    public Favorites mFavorites;
    private FavoritesListener listener;

    @Override
    public void api(RestClient restClient) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.listener = null;
        mFavorites = new Favorites();
        setContentView(R.layout.activity_favorites);

        Intent intent = getIntent();
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActiveToolbarItem(3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        getFavorites();
    }
    private void getFavorites(){

        RestClient client = Utilities.buildRetrofit();
        Call<ListDeviceFavoritesRequest.Response> call;

        final ListDeviceFavoritesRequest request = new ListDeviceFavoritesRequest(Utilities.getDeviceId(getApplicationContext()));

        call = client.listFavorites(request);

        call.enqueue(new Callback<ListDeviceFavoritesRequest.Response>() {
            @Override
            public void onResponse(Call<ListDeviceFavoritesRequest.Response> call, Response<ListDeviceFavoritesRequest.Response> response) {
                ListDeviceFavoritesRequest.Response body = response.body();
                if(body.isSuccess){
                    mFavorites = body.data;
                    listener.onDataLoaded(mFavorites);
                } else {
                    Log.e("Unexpected error", body.error);
                }

            }

            @Override
            public void onFailure(Call<ListDeviceFavoritesRequest.Response> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0)  {
                return new FavoriteMatchesFragment();
            }

            return new FavoriteTeamsFragment();
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getString(R.string.tab_favorite_matches);
                default:
                    return  getResources().getString(R.string.tab_favorite_teams);
            }
        }
    }

    // Assign the listener implementing events interface that will receive the events
    public void setFavoritesListener(FavoritesListener listener) {
        this.listener = listener;
    }

    public interface FavoritesListener {
        public void onDataLoaded(Favorites data);
    }
}
