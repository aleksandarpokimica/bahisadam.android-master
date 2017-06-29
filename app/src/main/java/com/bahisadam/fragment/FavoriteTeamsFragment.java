package com.bahisadam.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bahisadam.R;
import com.bahisadam.adapter.FavoriteMatchesAdapter;
import com.bahisadam.adapter.FavoriteTeamsAdapter;
import com.bahisadam.model.Favorites;
import com.bahisadam.model.MatchModel;
import com.bahisadam.model.TeamModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTeamsFragment extends Fragment {
    private  FavoriteTeamsAdapter mAdapter;
    @BindView(R.id.no_fav_team)
    RelativeLayout noFavTeam;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    public FavoriteTeamsFragment() {
        // Required empty public constructor
    }
    
    public static FavoriteTeamsFragment newInstance() {
        FavoriteTeamsFragment fragment = new FavoriteTeamsFragment();
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
        View view = inflater.inflate(R.layout.fragment_favorite_teams, container, false);
        ButterKnife.bind(this,view);

        mAdapter = new FavoriteTeamsAdapter(new ArrayList<TeamModel>(), this.getActivity(), this);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerView.setAdapter(mAdapter);


        ((FavoriteFragment)this.getParentFragment()).setFavoriteTeamsListener(new FavoriteFragment.FavoriteTeamsListener() {
            @Override
            public void onTeamDataLoaded(ArrayList<TeamModel> data) {
                mAdapter.data = data;
                if(data == null || data.size() == 0) {
                    noFavTeam.setVisibility(View.VISIBLE);
                } else {
                    noFavTeam.setVisibility(View.GONE);
                }
                mAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    private void loadData() {
        

    }
}
