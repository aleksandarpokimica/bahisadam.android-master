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
    @JsonProperty("isSuccess")
    private Boolean isSuccess;
    @JsonProperty("errorType")
    private String error;
    @JsonProperty("error")
    private String errorType;
    @JsonProperty("data")
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

    /*public List<PlayerRolesModel> getPlayerRoles() {
        return playerRoles;
    }*/

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public void setData(PlayerModel data) {
        this.data = data;
    }

    /*public void setPlayerRoles(List<PlayerRolesModel> playerRoles) {
        this.playerRoles = playerRoles;
    }*/
}
