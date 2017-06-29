package com.bahisadam;

import com.bahisadam.model.PlayerRolesList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Aleksandar on 6/28/2017.
 */

public interface ApiService {

    /*
    Retrofit get annotation with our URL
    And our method that will return us the List of ContactList
    */
    @GET("www.skoradam.com/api/player/profile/sr:player:1")
    Call<PlayerRolesList> getMyJSON();
}
