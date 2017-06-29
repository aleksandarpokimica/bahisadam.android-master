package com.bahisadam.services;

import com.bahisadam.model.predictionleague.PredictionLeagueModel;

/**
 * @author GorkemKarayel on 27.04.2017.
 */

public class PredictionBusService extends ServiceEvent {

    public PredictionLeagueModel predictionDataModel;

    public PredictionBusService(Exception exception , PredictionLeagueModel predictionDataModel) {
        super(exception);
        this.predictionDataModel = predictionDataModel;
    }
}
