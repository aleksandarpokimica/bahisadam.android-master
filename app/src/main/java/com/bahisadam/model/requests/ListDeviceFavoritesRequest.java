package com.bahisadam.model.requests;
import com.bahisadam.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by atata on 04/01/2017.
 */

public class ListDeviceFavoritesRequest {

    public String device_id;

    public ListDeviceFavoritesRequest(String device_id) {
        this.device_id = device_id;
    }

    public class Response {
        public Favorites data;
        public boolean isSuccess;
        public String error;
        public String errorType;
    }







}


