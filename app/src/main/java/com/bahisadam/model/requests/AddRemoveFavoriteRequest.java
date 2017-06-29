package com.bahisadam.model.requests;

/**
 * Created by atata on 04/01/2017.
 */

public class AddRemoveFavoriteRequest {

    public String match_id;
    public Number team_id;
    public String type;
    public boolean remove;
    public String device_id;

    public AddRemoveFavoriteRequest() {
    }

    public class Response {
        public String error;
        public String errorType;
        public Object data;
        public boolean isSuccess;
    }
}
