package com.bahisadam;

import android.app.Application;
import android.content.Context;

import android.content.res.Configuration;
import android.util.Log;
import com.bahisadam.interfaces.Constant;

import com.bahisadam.interfaces.RestClient;
import com.bahisadam.model.Favorites;
import com.bahisadam.model.TeamModel;
import com.bahisadam.model.requests.ListDeviceFavoritesRequest;
import com.bahisadam.services.FavoriteService;
import com.bahisadam.utility.Preferences;
import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.lang.ref.WeakReference;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ihsanbal.logging.LoggingInterceptor;

import io.fabric.sdk.android.Fabric;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MyApplication extends Application {

    private static WeakReference<MyApplication> sInstance;
    private static WeakReference<Context> context;
    public static String sDefSystemLanguage;
    public static Calendar sCalendar;
    private Tracker mTracker;
    public static boolean sUse_Logo = true;
    public static MyApplication mApplication;

    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }

    public static MyApplication getInstance() {
        return sInstance.get();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        Fabric.with(this, new Crashlytics());
        sInstance = new WeakReference<>(this);
        MyApplication.context = new WeakReference<>(getApplicationContext());
        FirebaseMessaging.getInstance().subscribeToTopic("SkorAdam");

        FavoriteService favService = new FavoriteService();
        favService.getDeviceFavorites(getAppContext(), new Callback<ListDeviceFavoritesRequest.Response>() {
            @Override
            public void onResponse(Call<ListDeviceFavoritesRequest.Response> call, Response<ListDeviceFavoritesRequest.Response> response) {
                ListDeviceFavoritesRequest.Response body = response.body();
                if(body.isSuccess){
                    Favorites favorites = body.data;
                    if(favorites != null) {
                        for(TeamModel team : favorites.teams) {
                            String topic = "team_" + Integer.toString(team._id);
                            Log.w("team", topic);
                            FirebaseMessaging.getInstance().subscribeToTopic(topic);
                        }
                    }
                } else {
                    Crashlytics.log(body.error);
                }
            }

            @Override
            public void onFailure(Call<ListDeviceFavoritesRequest.Response> call, Throwable t) {
                Crashlytics.logException(t);
            }
        });

        sInstance.get().initializeInstance();
        sDefSystemLanguage = Locale.getDefault().getLanguage();
        sCalendar = Calendar.getInstance(Locale.getDefault());
    }

    private void initializeInstance() {

        // Initialize Fresco
        Fresco.initialize(this.getApplicationContext());
    }

    @Override
    public void onTerminate() {
        // Do your application wise Termination task
        super.onTerminate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        sDefSystemLanguage = newConfig.locale.getLanguage();
    }

    public static Context getAppContext() {
        return MyApplication.context.get();
    }

    public RestClient getApi(){
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
        okBuilder.addInterceptor(new LoggingInterceptor.Builder()
                .loggable(BuildConfig.DEBUG)
                .build());

        Retrofit.Builder retrofit = new Retrofit.Builder();
        retrofit.client(okBuilder.build())
                .baseUrl(Constant.ROOT)
                .addConverterFactory(GsonConverterFactory.create());
        return retrofit.build().create(RestClient.class);
    }
}
