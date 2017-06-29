package com.bahisadam.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bahisadam.R;
import com.bahisadam.adapter.UserPredictionsAdapter;
import com.bahisadam.interfaces.Constant;
import com.bahisadam.interfaces.RestClient;
import com.bahisadam.model.predictionleague.UserPredictionModel;
import com.bahisadam.model.requests.UserPredictionsRequest;
import com.bahisadam.utility.Utilities;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserPredictionsFragment extends Fragment {
    private UserPredictionsAdapter mAdapter;
    @BindView(R.id.rv) RecyclerView recyclerView;
    private ArrayList<UserPredictionModel> mPredictions;
    private View mView;
    private String mUserId;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    public UserPredictionsFragment() {

    }

    public static UserPredictionsFragment newInstance(String userId) {
        UserPredictionsFragment fragment = new UserPredictionsFragment();
        fragment.mUserId = userId;
        return fragment;
    }


    private void getUserPredictionData(String user_id) {
        Utilities.showProgressDialog(getActivity(), progressBar);
        Gson gson = new GsonBuilder()
                .create();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.ROOT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // prepare call in Retrofit 2.0
        RestClient restClient =  retrofit.create(RestClient.class);
        final Activity activity = getActivity();
        Call<List<UserPredictionModel>> call = restClient.userPredictionsRequest(user_id);
        call.enqueue(new Callback<List<UserPredictionModel>>() {
            @Override
            public void onResponse(Call<List<UserPredictionModel>> call, Response<List<UserPredictionModel>> response) {
                Utilities.dismissProgressDialog(getActivity(), progressBar);
                List<UserPredictionModel> body = response.body();
                if(body != null){
                    mAdapter.data = (ArrayList)body;
                    mAdapter.notifyDataSetChanged();
                } else {
                    Log.e("request error","Unexpected error");
                }

            }

            @Override
            public void onFailure(Call<List<UserPredictionModel>> call, Throwable t) {
                Log.e("error", t.getMessage());
                Utilities.dismissProgressDialog(getActivity(), progressBar);
                Utilities.showSnackBar(activity, mView, getResources().getString(R.string.failed_to_connect_with_server));
            }
        });
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String defaultUser = "58e18d9de91cca00046a6a7a";
        if(mUserId == null)
        {
            mUserId = defaultUser;
        }

        getUserPredictionData(mUserId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_user_predictions, container, false);
        ButterKnife.bind(this, mView);
        mPredictions = new ArrayList<UserPredictionModel>();
        mAdapter = new UserPredictionsAdapter(mPredictions, this.getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerView.setAdapter(mAdapter);
        return mView;

    }


}



