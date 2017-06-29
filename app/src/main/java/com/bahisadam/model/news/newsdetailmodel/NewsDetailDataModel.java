package com.bahisadam.model.news.newsdetailmodel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @author GorkemKarayel on 8.05.2017.
 */

public class NewsDetailDataModel {

    @SerializedName("news")
    public NewsDetailDataModelBase news;

    @SerializedName("other_news")
    public ArrayList<NewsDetailOtherNews> other_news;

    @SerializedName("related_news")
    public ArrayList<NewsDetailRelatedNews> related_news;
}
