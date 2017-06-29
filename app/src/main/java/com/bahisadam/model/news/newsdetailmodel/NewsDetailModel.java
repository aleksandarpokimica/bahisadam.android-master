package com.bahisadam.model.news.newsdetailmodel;

import com.google.gson.annotations.SerializedName;

/**
 * @author GorkemKarayel on 8.05.2017.
 */

public class NewsDetailModel {

    @SerializedName("isSuccess")
    public boolean isSuccess;

    @SerializedName("errorType")
    public String errorType;

    @SerializedName("data")
    public NewsDetailDataModel data;

}
