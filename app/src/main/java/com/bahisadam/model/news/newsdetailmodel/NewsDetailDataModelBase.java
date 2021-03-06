package com.bahisadam.model.news.newsdetailmodel;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/**
 * @author GorkemKarayel on 8.05.2017.
 */

public class NewsDetailDataModelBase {

    @SerializedName("_id")
    public String _id;

    @SerializedName("title")
    public String title;

    @SerializedName("resource")
    public String resource;

    @SerializedName("summary")
    public String summary;

    @SerializedName("content")
    public String content;

    @SerializedName("category")
    public String category;

    @SerializedName("asset_id")
    public String asset_id;

    @SerializedName("player")
    public Player player;

    @SerializedName("inserted_date")
    public String inserted_date;

    @SerializedName("tags")
    public ArrayList<String> tags;

    @SerializedName("__v")
    public int __v;

    public class Player {
        public String player_name;
        public String player_id;
    }
}
