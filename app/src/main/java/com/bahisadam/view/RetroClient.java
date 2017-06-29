package com.bahisadam.view;

import com.bahisadam.ApiService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Aleksandar on 6/28/2017.
 */

public class RetroClient {

    /********
     * URLS
     *******/
    private static final String ROOT_URL = "http://www.skoradam.com/api/player/profile/";

    /**
     * Get Retrofit Instance
     */

    static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    /**
     * Get API Service
     *
     * @return API Service
     */
    public static ApiService getApiService() {
        return getRetrofitInstance().create(ApiService.class);
    }
}
