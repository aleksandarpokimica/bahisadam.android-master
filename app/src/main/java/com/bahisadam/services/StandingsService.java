package com.bahisadam.services;

import com.bahisadam.R;
import com.bahisadam.fragment.StandingsFragment;
import com.bahisadam.interfaces.Constant;
import com.bahisadam.interfaces.RestClient;
import com.bahisadam.model.StandingsModel;
import com.bahisadam.utility.Utilities;
import com.crashlytics.android.Crashlytics;
import com.google.gson.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by fatih on 07.05.2017.
 */
public class StandingsService {
    private Retrofit retrofit;
    private RestClient restClientAPI;
    RestClient mClient = retrofit.create(RestClient.class);
    StandingsLoadCompletedListener mStandingsCompletedListener;
    StandingsLoadFailedListener mStandingsLoadFailedListener;

    public StandingsService(){
        this.mStandingsCompletedListener = null;
        this.mStandingsLoadFailedListener = null;
    }

    public void loadStandings(int leagueId){
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.ROOT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        // prepare call in Retrofit 2.0
        mClient=  retrofit.create(RestClient.class);
        Call<StandingsModel> call = mClient.leagueStandingsRequest(leagueId);

        call.enqueue(new Callback<StandingsModel>() {
            @Override
            public void onResponse(Call<StandingsModel> call, Response<StandingsModel> response) {
                if(mStandingsCompletedListener != null) {
                    mStandingsCompletedListener.StandingsLoadCompleted(response.body());
                }
            }

            @Override
            public void onFailure(Call<StandingsModel> call, Throwable t) {
                Crashlytics.logException(t);
                if(mStandingsLoadFailedListener != null) {
                    mStandingsLoadFailedListener.StandingsLoadFailed(t.getMessage());
                }
            }
        });
    }

    public interface StandingsLoadCompletedListener {
        void StandingsLoadCompleted(StandingsModel model);
    }

    public interface StandingsLoadFailedListener {
        void StandingsLoadFailed(String error);
    }

    // Assign the listener implementing events interface that will receive the events
    public void setStandingsLoadCompletedListener(StandingsLoadCompletedListener listener) {
        this.mStandingsCompletedListener = listener;
    }
    public void setStandingsLoadFailedListener(StandingsLoadFailedListener listener) {
        this.mStandingsLoadFailedListener = listener;
    }
}
