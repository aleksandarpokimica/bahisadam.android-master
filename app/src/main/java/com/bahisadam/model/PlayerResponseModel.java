package com.bahisadam.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.bahisadam.model.*;

import java.util.List;

/**
 * Created by Aleksandar on 6/26/2017.
 */

public class PlayerResponseModel {
    @SerializedName("isSuccess")
    @Expose
    private Boolean isSuccess;
    @SerializedName("errorType")
    @Expose
    private String errorType;
    @SerializedName("data")
    @Expose
    private PlayerModel data;
    /*@SerializedName("roles")
    @Expose
    private List<PlayerRolesModel> playerRoles = null;*/

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getErrorType() {
        return errorType;
    }

    public PlayerModel getData() {
        return data;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public void setData(PlayerModel data) {
        this.data = data;
    }
}
