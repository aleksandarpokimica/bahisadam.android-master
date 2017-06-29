package com.bahisadam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Aleksandar on 6/28/2017.
 */

public class PlayerRolesList {

    @SerializedName("roles")
    @Expose
    private ArrayList<PlayerRolesModel> players = new ArrayList<>();

    /**
     * @return The contacts
     */
    public ArrayList<PlayerRolesModel> getPlayerRoles() {
        return players;
    }

    /**
     * @param players The player roles
     */
    public void setPlayerRoles(ArrayList<PlayerRolesModel> players) {
        this.players = players;
    }
}
