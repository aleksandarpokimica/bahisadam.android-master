package com.bahisadam.model.news.newsdetailmodel;

import com.google.gson.annotations.SerializedName;

/**
 * @author GorkemKarayel on 8.05.2017.
 */

public class NewsDetailRelatedNews {

    @SerializedName("_id")
    public String _id;

    @SerializedName("title")
    public String title;

    @SerializedName("summary")
    public String summary;

    @SerializedName("asset_id")
    public String asset_id;

    @SerializedName("inserted_date")
    public String inserted_date;

}
