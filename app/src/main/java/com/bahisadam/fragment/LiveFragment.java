package com.bahisadam.fragment;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.bahisadam.Listeners.RecyclerItemClickListener;
import com.bahisadam.MyApplication;
import com.bahisadam.R;
import com.bahisadam.adapter.LiveMatchAdapter;
import com.bahisadam.interfaces.Constant;
import com.bahisadam.interfaces.RestClient;
import com.bahisadam.model.LiveResponse;
import com.bahisadam.utility.Utilities;
import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class LiveFragment extends Fragment implements Callback<LiveResponse> {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.no_live_match)
    RelativeLayout noMatch;
    LiveMatchAdapter mAdapter;
    RestClient restClient;
    private static WeakReference<LiveFragment> mContext;
    Gson gson;
    Handler mHandler;

    @BindView(R.id.progressBar) ProgressBar progressBar;

    public LiveFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = new WeakReference<>(LiveFragment.this);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_live, container, false);
        ButterKnife.bind(this,view);
        mHandler = new Handler();


        mAdapter = new LiveMatchAdapter();
        rv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        rv.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mAdapter.allocateHeaders();
                if(mAdapter.isHeader(position)){
                    int section = mAdapter.getSection(position);
                    int leagueId = mAdapter.data.get(section).getId();
                    String leagueName = mAdapter.data.get(section).getLeague_name_tr();
                    String leagueIcon = mAdapter.data.get(section).getFlag();
                    Utilities.openLeagueDetails(getActivity(),leagueId,leagueName,leagueIcon);
                }

            }
        }));
        rv.setAdapter(mAdapter);
        //getLiveScores();
        return view;
    }

    private Runnable updateData = new Runnable(){
        public void run(){
            //call the service here
            getLiveScores();

        }
    };


    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onResume() {
        super.onResume();
        getLiveScores();
    }

    public final void getLiveScores() {
        gson = new GsonBuilder()
                .registerTypeAdapter(LiveResponse.class, new JsonDeserializer<LiveResponse>() {

                    @Override
                    public LiveResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

                        LiveResponse response = new LiveResponse();
                        Set<Map.Entry<String, JsonElement>> entries   =  ((JsonObject) json).entrySet();
                        Iterator<Map.Entry<String, JsonElement>> iterator = entries.iterator();
                        while(iterator.hasNext()){
                            try {
                                Map.Entry<String,JsonElement> it = iterator.next();

                                response.items.add(new Gson().fromJson(it.getValue(),LiveResponse.LiveItem.class));
                            }
                            catch (Exception ex) {
                                Crashlytics.logException(ex);
                            }
                        }
                        return response;
                    }
                })
                .create();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.ROOT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        // prepare call in Retrofit 2.0
        restClient =  retrofit.create(RestClient.class);
        Call<LiveResponse> call = restClient.liveMatches();
        call.enqueue(this);
    }

    /*
    Emitter.Listener onLiveScoreUpdate1  = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
        List<LiveResponse.LiveItem> items = gson.fromJson(args[0].toString(), LiveResponse.class).items;
        updateScore(items);
        }
    };*/

    private void updateScore(List<LiveResponse.LiveItem> items){
        if(items!=null && mAdapter != null) {
            Collections.sort(items, new Comparator<LiveResponse.LiveItem>() {
                @Override
                public int compare(LiveResponse.LiveItem liveItem, LiveResponse.LiveItem t1) {
                    return liveItem.getOrder() - t1.getOrder();
                }
            });
            mAdapter.data.clear();
            mAdapter.data.addAll(items);
            Activity activity = getActivity();
            if(activity!=null) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(mAdapter != null)
                            mAdapter.notifyDataSetChanged();
                    }
                });
            }
        }
    }

    @Override
    public void onResponse(Call<LiveResponse> call, Response<LiveResponse> response) {
        Utilities.dismissProgressDialog(getActivity(), progressBar);
        LiveResponse res = response.body();
        if(res == null) {
            return;
        }

        if(res.items == null){
            return;
        }

        List<LiveResponse.LiveItem> items = res.items;
        updateScore(items);

        if(items ==null || items.size() == 0) {
            noMatch.setVisibility(View.VISIBLE);
        } else {
            noMatch.setVisibility(View.GONE);
        }

        mHandler.postDelayed(updateData, 20000);
    }

    @Override
    public void onFailure(Call<LiveResponse> call, Throwable t) {
        int x=0;
        Crashlytics.logException(t);
    }

}
