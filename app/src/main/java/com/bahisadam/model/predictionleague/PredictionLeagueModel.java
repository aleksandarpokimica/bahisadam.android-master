package com.bahisadam.model.predictionleague;

import com.google.gson.annotations.SerializedName;

/**
 * @author GorkemKarayel on 27.04.2017.
 */

public class PredictionLeagueModel {

    @SerializedName("isSuccess")
    public boolean isSuccess;

    @SerializedName("errorType")
    public String errorType;

    @SerializedName("data")
    public PredictionDataModel data;
}
