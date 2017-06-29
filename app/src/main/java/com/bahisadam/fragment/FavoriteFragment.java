package com.bahisadam.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bahisadam.R;
import com.bahisadam.interfaces.RestClient;
import com.bahisadam.model.Favorites;
import com.bahisadam.model.MatchModel;
import com.bahisadam.model.TeamModel;
import com.bahisadam.model.requests.ListDeviceFavoritesRequest;
import com.bahisadam.utility.Utilities;
import com.bahisadam.view.HomeActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private FavoriteTeamsListener team_listener;
    private FavoriteMatchesListener match_listener;
    public Favorites mFavorites;

    @BindView(R.id.container) ViewPager mViewPager;
    @BindView(R.id.tabs) TabLayout mTabLayout;

    public FavoriteFragment() {
        this.team_listener = null;
        this.match_listener = null;
    }
    
    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this,view);

        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        getFavorites();

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){

                    ((HomeActivity)getActivity()).loadHomePage();

                    return true;

                }

                return false;
            }
        });

        return view;
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
    public void setFavoriteTeamsListener(FavoriteTeamsListener listener) {
        this.team_listener = listener;
    }
    public void setFavoriteMatchesListener(FavoriteMatchesListener listener) {
        this.match_listener = listener;
    }

    public interface FavoriteTeamsListener {
        void onTeamDataLoaded(ArrayList<TeamModel> data);
    }

    public interface FavoriteMatchesListener {
        void onMatchDataLoaded(ArrayList<MatchModel> data);
    }

    private void getFavorites(){

        RestClient client = Utilities.buildRetrofit();
        Call<ListDeviceFavoritesRequest.Response> call;
        final ListDeviceFavoritesRequest request = new ListDeviceFavoritesRequest(Utilities.getDeviceId(getContext()));
        call = client.listFavorites(request);

        call.enqueue(new Callback<ListDeviceFavoritesRequest.Response>() {
            @Override
            public void onResponse(Call<ListDeviceFavoritesRequest.Response> call, Response<ListDeviceFavoritesRequest.Response> response) {
                ListDeviceFavoritesRequest.Response body = response.body();
                if(body.isSuccess){
                    mFavorites = body.data;
                    team_listener.onTeamDataLoaded(mFavorites.teams);
                    match_listener.onMatchDataLoaded(mFavorites.matches);
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
}
