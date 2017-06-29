package com.bahisadam.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bahisadam.Cache;
import com.bahisadam.Listeners.OnHorizontalAdapterItemClickListener;
import com.bahisadam.MyApplication;
import com.bahisadam.R;
import com.bahisadam.adapter.FootballAdapter;
import com.bahisadam.adapter.HorizontalAdapter;
import com.bahisadam.interfaces.Constant;
import com.bahisadam.interfaces.RestClient;
import com.bahisadam.model.LeagueListComparator;
import com.bahisadam.model.LeagueMatchList;
import com.bahisadam.model.MatchPOJO;
import com.bahisadam.utility.Preferences;
import com.bahisadam.utility.Utilities;
import com.bahisadam.view.DetailPageActivity;
import com.bahisadam.view.HomeActivity;
import com.crashlytics.android.Crashlytics;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.*;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FootballFragment extends Fragment implements Callback<MatchPOJO>, Constant {

    private static WeakReference<FootballFragment> mContext;
    private FootballAdapter adapter;
    private HorizontalAdapter horizontalAdapter;
    private String dateSelected;
    public Date currentDate;
    private Socket mSocket;
    private List<LeagueMatchList> list;
    private Set<String> favoriteMatches;
    private Set<String> favoriteTeams;
    private LeagueMatchList leagueFavMatchList;
    private boolean displayOnlyLive = false;
    private boolean displayOnlyFavorites = false;
    private boolean loading = false;
    Handler mHandler;

    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.horizontal_recycler_view) RecyclerView horizontal_recycler_view;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.no_live_match) RelativeLayout noLiveMatchRow;
    @BindView(R.id.no_fav_match) RelativeLayout noFavMatchRow;
    //@BindView(R.id.text_collapsing) ImageView collapsing;


    public static FootballFragment newInstance(Boolean fav) {

        Bundle args = new Bundle();
        args.putBoolean("fav",fav);
        FootballFragment fragment = new FootballFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public FootballFragment() {
        // Required empty public constructor
    }

    // HomeActivity Instance
    public static FootballFragment getInstance() {
        return mContext.get();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = new WeakReference<>(FootballFragment.this);
        mHandler = new Handler();
    }

    private Emitter.Listener onLiveScoreUpdate = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Gson gson = new GsonBuilder().create();

            //Type type = new TypeToken<Map<String, String>>(){}.getType();
            //Map<String, String> myMap = gson.fromJson(args[0].toString(), type);
            try {
                JSONObject scores = ((JSONObject)args[0]).getJSONObject("scores");
                Type type = new TypeToken<Set<MatchPOJO.Match>>(){}.getType();
                //var adapter = mContext.recyclerView.getAdapter().get;
                Iterator<?> keys = scores.keys();
                while(keys.hasNext()){
                    String key = (String)keys.next();
                    JSONObject league = scores.getJSONObject(key);
                    Set<MatchPOJO.Match> matches = gson.fromJson(league.getJSONArray("matches").toString(), type);

                    for(LeagueMatchList l  : list) {
                        for(MatchPOJO.Match lm : l.getData()) {
                            for(MatchPOJO.Match m : matches) {
                                try {
                                    if(lm.getHomeTeam() != null && lm.getAwayTeam() != null && lm.getHomeTeam().getTeamNameTr().equals(m.getHomeTeam().getTeamNameTr()) && lm.getResultType().equals(PLAYING)
                                            && lm.getAwayTeam().getTeamNameTr().equals(m.getAwayTeam().getTeamNameTr())) {
                                        lm.setLiveMinute(m.getLiveMinute());
                                        lm.setResultType(m.getResultType());
                                        lm.setHomeGoals(m.getHomeGoals());
                                        lm.setAwayGoals(m.getAwayGoals());
                                        lm.setIsHalfTime(m.getIsHalfTime());
                                        break;
                                    }
                                } catch (Exception e) {
                                    Log.e("error ", e.getMessage());
                                }

                            }
                        }
                    }
                }


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });

            } catch (Exception e) {
                Log.e("error parsing response", e.getMessage());
            }

            /*while(scores.keys().hasNext()) {
                scores.keys().next();
            }*/


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
        if(!loading) {
            getMatchRequest(dateSelected, Utilities.toJSONDateString(Utilities.addHour(currentDate, 30)), false);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_football, container, false);
        ButterKnife.bind(this, rootView);



        Calendar cal = Calendar.getInstance();
        cal.setTime(Utilities.addHour(cal.getTime(), -3));
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set( Calendar.AM_PM, Calendar.AM );

        currentDate = cal.getTime();
        Log.e("CurrentdateTime", "CurrentdateTime??" + currentDate);
        dateSelected = Utilities.toJSONDateString(Utilities.addHour(currentDate, 6));


        setAdapterHorizontal();
        progressBar.setVisibility(View.GONE);

        list = new ArrayList<>();

        favoriteMatches = Preferences.getFavoriteMatches();
        adapter = new FootballAdapter(list, getActivity(), this);

        adapter.setMatchClickListener(new FootballAdapter.OnMatchClickListener() {
            @Override
            public void onFavoriteClick(int section, int relativePosition,int absolutePosition) {
                MatchPOJO.Match match = adapter.dataToShow.get(section).get(relativePosition);

                boolean add = !favoriteMatches.contains(match.getId());

                toggleFavoriteMatch(match, absolutePosition, add);
            }

            @Override
            public void onMatchClick(int section, int relativePosition) {
                MatchPOJO.Match match = null;
                match = adapter.dataToShow.get(section).get(relativePosition);

                Bundle bundle = new Bundle();
                bundle.putString(DetailPageActivity.MATCH_ID,match.getId());
                bundle.putInt(DetailPageActivity.LEAGUE_ID,match.getLeagueId().getId());
                bundle.putString(DetailPageActivity.ARG_TEAM_HOME_NAME,match.getHomeTeam().getTeamNameTr());
                bundle.putString(DetailPageActivity.ARG_TEAM_AWAY_NAME,match.getAwayTeam().getTeamNameTr());
                bundle.putString(DetailPageActivity.ARG_REUSULT_TYPE,match.getResultType());
                Utilities.openMatchDetails(getActivity(),bundle);

            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        if (Utilities.isNetworkAvailable(getActivity())) {
            setSelectedDate(dateSelected,true);

        } else {
            Utilities.showSnackBarInternet(getActivity(), HomeActivity.getInstance().getCoordinateLayout(), getResources().getString(R.string.no_internet_connection));
        }

        // Inflate the layout for this fragment
        return rootView;
    }

    private void toggleFavoriteMatch(final MatchPOJO.Match match, final int absolutePosition, final boolean add){

        if(add){
            favoriteMatches.add(match.getId());
            if(!favoriteTeams.contains(match.getHomeTeam().getId().toString()) && !favoriteTeams.contains(match.getAwayTeam().getId().toString())) {

                FirebaseMessaging.getInstance().subscribeToTopic(match.getId());
                Utilities.addRemoveMatchToFavorites(match.getId(), false, this.getContext());
            }

            match.setIsFavorite(true);
        }
        else {
            favoriteMatches.remove(match.getId());
            FirebaseMessaging.getInstance().unsubscribeFromTopic( match.getId());
            Utilities.addRemoveMatchToFavorites(match.getId(), true, this.getContext());
            match.setIsFavorite(false);
        }

        adapter.notifyDataSetChanged();

        Preferences.setFavoriteMatches(favoriteMatches);


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void loadBitmatIntoView(ImageView view, String color1, String color2) {

        color1 = color1 == null ? "0" : color1;
        color2 = color2 == null ? "0" : color2;
        Bitmap bitmap = Cache.getBitmap(color1+color2);
        if(bitmap == null) {
            new BitmapWorkerTask(getActivity(),view).execute(color1,color2);

        } else {
            view.setImageBitmap(bitmap);
        }


    }

    private void setAdapterHorizontal() {

        horizontalAdapter = new HorizontalAdapter(getActivity(), Utilities.getCurrentWeek(getActivity(),dateSelected));
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        horizontal_recycler_view.setLayoutManager(horizontalLayoutManagaer);
        horizontalAdapter.setOnCalendarclickListener(new HorizontalAdapter.OnCalendarClickListener() {
            @Override
            public void onClick() {
                SelectDateFragment dialog =  SelectDateFragment.newInstance(FootballFragment.this);
                dialog.show(getFragmentManager(),"DatePicker");


            }
        });
        horizontalAdapter.setOnDateSelectedListener(new HorizontalAdapter.OnDateSelectedListener() {
            @Override
            public void onDateSelected(String date) {
                currentDate = Utilities.parseJSONDate(date);
                setSelectedDate(date,false);
            }
        });

        horizontal_recycler_view.setAdapter(horizontalAdapter);
        horizontal_recycler_view.addOnItemTouchListener(new OnHorizontalAdapterItemClickListener());

    }
/*
    @OnClick(R.id.collapseContainer)
    public void toggleLeaguesCLick(){
        toggleLeagues();
        float rotation = adapter.toggled ? 0 : 90;
        ViewCompat.animate(collapsing).rotation(rotation).start();
    }
    private void toggleLeagues() {
        for(int i= 0; i < adapter.getSectionCount(); i++){
            adapter.data.get(i).getLeauge().setToggled(!adapter.toggled);

        }
        adapter.notifyDataSetChanged();
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /* Make Rest Client Call For get match request */
    public final void getMatchRequest(String startDate, String endDate, boolean showProgressbar) {
        loading = true;
        if(showProgressbar) {
            Utilities.showProgressDialog(getActivity(),progressBar);
        }

        Log.i("StartDate", startDate);
        Log.i("EndDate", endDate);
        String url = Constant.ROOT + Constant.APPENDED_PATH + startDate
                + Constant.SLASH
                + endDate
                + Constant.SLASH;

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RestClient restClientAPI = retrofit.create(RestClient.class);
        Call<MatchPOJO> call = restClientAPI.matchRequest( displayOnlyLive ? 1 : 0);

        // prepare call in Retrofit 2.0
        //asynchronous call
        call.enqueue(this);
    }



    private Runnable updateData = new Runnable(){
        public void run(){
            //call the service here
            getMatchRequest(dateSelected, Utilities.toJSONDateString(Utilities.addHour(currentDate, 30)), false);

        }
    };


    /* Get Response From LogIn */
    @Override
    public void onResponse(Call<MatchPOJO> call, Response<MatchPOJO> response) {
        loading = false;
        MatchPOJO match = response.body();
        int code = response.code();
        if (code == RESPONSE_CODE) {
            Utilities.dismissProgressDialog(getActivity(), progressBar);

            if (match.getIsSuccess() == SUCCESS) {
                int matchCount = match.getData().getMatches().size();
                //totalMatch.setText("Toplam Maç: " + matchCount);
                if (match.getData().getMatches() != null && matchCount > 0) {

                    List<MatchPOJO.LeagueId> leagueIds = new ArrayList<>();
                    //Iterator<MatchPOJO.Match> itr = match.getData().getMatches().iterator();

                    for (MatchPOJO.Match m : match.getData().getMatches()) {

                        MatchPOJO.LeagueId leagueId = m.getLeagueId();
                        leagueId.country = m.getCountry().getCountryNameTr();

                        if(!leagueIds.contains(leagueId)){
                            leagueIds.add(leagueId);
                        }
                    }

                    Collections.sort(leagueIds, LeagueListComparator.leagueOrderComparator);

                    Integer topLeagueCount = 8;
                    if(leagueIds.size() < 9) {
                        topLeagueCount = leagueIds.size();
                    }

                    list.clear();
                    for (int i = 0; i < topLeagueCount; i++) {

                        LeagueMatchList leagueMatchList = new LeagueMatchList(leagueIds.get(i));

                        for (int j = 0; j < match.getData().getMatches().size(); j++) {
                            if (leagueIds.get(i).equals(match.getData().getMatches().get(j).getLeagueId())) {
                                leagueMatchList.add(match.getData().getMatches().get(j));
                                if(favoriteMatches.contains(match.getData().getMatches().get(j).getId())) {
                                    match.getData().getMatches().get(j).setIsFavorite(true);
                                }
                            }
                        }

                        if(leagueMatchList.size() > 0) {
                            list.add(leagueMatchList);
                        }
                    }

                    if(leagueIds.size() > 8) {
                        List<MatchPOJO.LeagueId> others = leagueIds.subList(8, leagueIds.size());
                        Collections.sort(others, LeagueListComparator.countryNameComparator);

                        for (int i = 0; i < others.size(); i++) {

                            LeagueMatchList leagueMatchList = new LeagueMatchList(others.get(i));

                            for (int j = 0; j < match.getData().getMatches().size(); j++) {
                                try {
                                    if (others.get(i).equals(match.getData().getMatches().get(j).getLeagueId())) {
                                        leagueMatchList.add(match.getData().getMatches().get(j));
                                        if(favoriteMatches.contains(match.getData().getMatches().get(j).getId())) {
                                            match.getData().getMatches().get(j).setIsFavorite(true);
                                        }
                                    }
                                } catch (Exception ex) {
                                    Crashlytics.logException(ex);
                                }
                            }

                            if(leagueMatchList.size() > 0) {
                                list.add(leagueMatchList);
                            }
                        }
                    }

                    adapter.dataToShow = list;

                    if(displayOnlyFavorites && list.size() == 0) {
                        noFavMatchRow.setVisibility(View.VISIBLE);
                    } else {
                        noFavMatchRow.setVisibility(View.GONE);
                    }


                    adapter.notifyDataSetChanged();
                } else {
                    if(list.size() == 0) {
                        if(displayOnlyFavorites) {
                            noFavMatchRow.setVisibility(View.VISIBLE);
                            noLiveMatchRow.setVisibility(View.GONE);
                        } else {
                            noFavMatchRow.setVisibility(View.GONE);
                            noLiveMatchRow.setVisibility(View.VISIBLE);
                        }

                    } else {
                        noFavMatchRow.setVisibility(View.GONE);
                        noLiveMatchRow.setVisibility(View.GONE);
                    }
                }

            } else {
                Log.e("Success", "Success??" + match.getIsSuccess());
            }
        }

        mHandler.postDelayed(updateData, 30000);
    }



    @Override
    public void onFailure(Call<MatchPOJO> call, Throwable t) {
        Log.e("onFailure", "onFailure?? " + t.getMessage());
        Utilities.dismissProgressDialog(getActivity(), progressBar);
        Utilities.showSnackBar(getActivity(), HomeActivity.getInstance().getCoordinateLayout(), "Bağlantı Kurulamadı. Lütfen internet bağlantınızı kontrol ediniz.");

    }

    // Set Selected Date...
    public void setSelectedDate(String dateSelected,boolean initWeek) {
        this.dateSelected = dateSelected;
        list.clear();
        if(initWeek){
            horizontalAdapter.clear();
            horizontalAdapter.add(Utilities.getCurrentWeek(getActivity(),dateSelected));
        }

        favoriteMatches = Preferences.getFavoriteMatches();
        favoriteTeams = Preferences.getTeamsFollow();
        getMatchRequest(dateSelected, Utilities.toJSONDateString(Utilities.addHour(currentDate, 30)), true);

    }

    public boolean isDisplayOnlyLive() {
        return displayOnlyLive;
    }

    public void setDisplayOnlyLive(boolean displayOnlyLive) {
        this.displayOnlyLive = displayOnlyLive;
    }

    public boolean isDisplayOnlyFavorites() {
        return displayOnlyFavorites;
    }

    public void setDisplayOnlyFavorites(boolean displayOnlyFavorites) {
        this.displayOnlyFavorites = displayOnlyFavorites;
    }

    class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        private Activity ctx;
        //LruCache<String,Bitmap> cache;

        BitmapWorkerTask(Activity ctx,ImageView imageView) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageViewReference = new WeakReference<>(imageView);
            this.ctx = ctx;
        }

        // Decode image in background.
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected Bitmap doInBackground(String... params) {
            String color1= params[0];
            String color2 = params[1];
            Bitmap bitmap = Utilities.paintTeamIcon(ctx,color1,color2);
            Cache.addBitmap(color1+color2,bitmap);
            return bitmap;
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {

            //if (imageViewReference != null && bitmap != null) {
            if (bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

}
