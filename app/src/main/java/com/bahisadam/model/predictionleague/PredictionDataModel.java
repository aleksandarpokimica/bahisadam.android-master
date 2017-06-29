package com.bahisadam.model.predictionleague;

import com.google.gson.annotations.SerializedName;

/**
 * @author GorkemKarayel on 27.04.2017.
 */

public class PredictionDataModel {

    @SerializedName("previous")
    public Previous previous;

    @SerializedName("current")
    public Current current;
}
