package com.bahisadam.services;

import com.bahisadam.model.news.newsdetailmodel.NewsDetailModel;

import retrofit2.Response;

/**
 * @author GorkemKarayel on 10.05.2017.
 */

public class NewsBusDetailService extends ServiceEvent {

    public Response<NewsDetailModel> mNewsDetailModel;

    public NewsBusDetailService(Exception exception,Response<NewsDetailModel> mNewsDetailModel) {
        super(exception);
        this.mNewsDetailModel = mNewsDetailModel;
    }
}
