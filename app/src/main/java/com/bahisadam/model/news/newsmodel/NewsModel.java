package com.bahisadam.model.news.newsmodel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @author GorkemKarayel on 8.05.2017.
 */

public class NewsModel{

    @SerializedName("isSuccess")
    public boolean isSuccess;

    @SerializedName("errorType")
    public String errorType;

    @SerializedName("data")
    public ArrayList<NewsData> data;

}
