package com.bahisadam.model.predictionleague;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/**
 * @author GorkemKarayel on 28.04.2017.
 */

public class Previous {

    @SerializedName("users")
    public ArrayList<PredictionUserModel> users;

    @SerializedName("dateRange")
    public String dateRange;

}
