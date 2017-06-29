package com.bahisadam.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bahisadam.R;
import com.bahisadam.adapter.FavoriteMatchesAdapter;
import com.bahisadam.adapter.StatsAdapter;
import com.bahisadam.interfaces.RestClient;
import com.bahisadam.model.Favorites;
import com.bahisadam.model.MatchModel;
import com.bahisadam.model.MatchPOJO;
import com.bahisadam.utility.Utilities;
import com.bahisadam.view.FavoritesActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class
FavoriteMatchesFragment extends Fragment {
    private  FavoriteMatchesAdapter mAdapter;
    @BindView(R.id.no_fav_match) RelativeLayout noFavMatch;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    public FavoriteMatchesFragment() {

    }

    public static FavoriteMatchesFragment newInstance() {
        FavoriteMatchesFragment fragment = new FavoriteMatchesFragment();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_matches, container, false);
        ButterKnife.bind(this, view);
        mAdapter = new FavoriteMatchesAdapter(new ArrayList<MatchModel>(), this.getActivity(), this);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerView.setAdapter(mAdapter);


        ((FavoriteFragment)this.getParentFragment()).setFavoriteMatchesListener(new FavoriteFragment.FavoriteMatchesListener() {
            @Override
            public void onMatchDataLoaded(ArrayList<MatchModel> data) {
                mAdapter.data = data;
                if(data == null || data.size() == 0) {
                    noFavMatch.setVisibility(View.VISIBLE);
                } else {
                    noFavMatch.setVisibility(View.GONE);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        return view;

    }


}



