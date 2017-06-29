package com.bahisadam.model.predictionleague;

import com.google.gson.annotations.SerializedName;

/**
 * @author GorkemKarayel on 27.04.2017.
 */

public class PredictionUserModel {

    @SerializedName("_id")
    public String _id;

    @SerializedName("total")
    public int total;

    @SerializedName("totalSuccess")
    public int totalSuccess;

    @SerializedName("totalFail")
    public int totalFail;

    @SerializedName("score")
    public double score;

    @SerializedName("user_id")
    public PredictionUserNameModel user_id;
}
