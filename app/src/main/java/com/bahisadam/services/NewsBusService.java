package com.bahisadam.services;

import com.bahisadam.model.news.newsmodel.NewsModel;

import retrofit2.Response;

/**
 * @author GorkemKarayel on 9.05.2017.
 */

public class NewsBusService extends ServiceEvent {

    public Response<NewsModel> mNewsModel;

    public NewsBusService(Exception exception, Response<NewsModel> mNewsModel) {
        super(exception);
        this.mNewsModel = mNewsModel;
    }
}
