package com.bahisadam.model.teamInfo;

import com.bahisadam.model.common.Manager;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by fatih on 14.06.2017.
 */

public class TeamDetailModel {
    @SerializedName("_id")
    @Expose
    private int id = 0;

    @SerializedName("team_name")
    @Expose
    private String name = "";

    @SerializedName("team_name_tr")
    @Expose
    private String name_tr = "";

    @SerializedName("manager")
    @Expose
    private Manager manager = null;

    @SerializedName("squad")
    @Expose
    private List<PlayerStatistics> squad = null;

    @SerializedName("color1")
    @Expose
    private String color1 = "";

    @SerializedName("color2")
    @Expose
    private String color2 = "";

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public List<PlayerStatistics> getSquad() {
        return squad;
    }

    public void setSquad(List<PlayerStatistics> squad) { this.squad = squad; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_tr() {
        return name_tr;
    }

    public void setName_tr(String name_tr) {
        this.name_tr = name_tr;
    }

    public String getColor1() {
        return color1;
    }

    public void setColor1(String color1) {
        this.color1 = color1;
    }

    public String getColor2() {
        return color2;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

