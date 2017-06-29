package com.bahisadam.model.news.newsmodel;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author GorkemKarayel on 8.05.2017.
 */

public class NewsData implements Serializable {

    @SerializedName("_id")
    public String _id;

    @SerializedName("title")
    public String title;

    @SerializedName("resource")
    public String resource;

    @SerializedName("summary")
    public String summary;

    @SerializedName("category")
    public String category;

    @SerializedName("asset_id")
    public String asset_id;

    @SerializedName("inserted_date")
    public String inserted_date;

    @SerializedName("tags")
    public ArrayList<String> tags;

}
