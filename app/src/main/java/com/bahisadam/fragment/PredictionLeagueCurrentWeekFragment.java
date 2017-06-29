package com.bahisadam.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bahisadam.R;
import com.bahisadam.adapter.PredictionCurrentFragmentAdapter;
import com.bahisadam.model.predictionleague.PredictionLeagueModel;
import com.bahisadam.services.PredictionBusService;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author GorkemKarayel on 27.04.2017.
 */

public class PredictionLeagueCurrentWeekFragment extends Fragment{

    private PredictionLeagueModel predictionLeagueModel;
    @BindView(R.id.tl_recyclerview)RecyclerView tlRecyclerView;


    public PredictionLeagueCurrentWeekFragment(){
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PredictionBusService event) {
        if (event.exception == null) {
            predictionLeagueModel = event.predictionDataModel;
            initial();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_prediction_currentweek, container, false);
        ButterKnife.bind(this,mView);
        return mView;
    }

    private void initial() {
        PredictionCurrentFragmentAdapter predictionCurrentFragmentAdapter = new PredictionCurrentFragmentAdapter(getContext(), predictionLeagueModel, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        tlRecyclerView.setLayoutManager(mLayoutManager);
        tlRecyclerView.setItemAnimator(new DefaultItemAnimator());
        tlRecyclerView.setAdapter(predictionCurrentFragmentAdapter);
    }
}
