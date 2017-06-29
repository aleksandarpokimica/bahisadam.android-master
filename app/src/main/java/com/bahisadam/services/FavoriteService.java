package com.bahisadam.services;

import android.content.Context;
import com.bahisadam.interfaces.RestClient;
import com.bahisadam.model.requests.ListDeviceFavoritesRequest;
import com.bahisadam.utility.Utilities;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by fatih on 22.05.2017.
 */
public class FavoriteService {

    public FavoriteService (){


    }

    public void getDeviceFavorites(Context context, Callback<ListDeviceFavoritesRequest.Response> callback)
    {

        RestClient client = Utilities.buildRetrofit();
        Call<ListDeviceFavoritesRequest.Response> call;
        final ListDeviceFavoritesRequest request = new ListDeviceFavoritesRequest(Utilities.getDeviceId(context));
        call = client.listFavorites(request);

        call.enqueue(callback);
    }
}
