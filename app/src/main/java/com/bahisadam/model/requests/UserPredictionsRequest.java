package com.bahisadam.model.requests;

import com.bahisadam.model.predictionleague.UserPredictionModel;

import java.util.List;

/**
 * Created by atata on 04/01/2017.
 */

public class UserPredictionsRequest {

    public String user_id;

    public String device_id;

    public UserPredictionsRequest() {
    }

    public class Response {
        public String error;
        public String errorType;
        public List<UserPredictionModel> data;
        public boolean isSuccess;
    }
}
