package com.bahisadam.model.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fatih on 14.06.2017.
 */
public class Manager{

    @SerializedName("id")
    @Expose
    private String id = "";

    @SerializedName("name")
    @Expose
    private String name = "";

    @SerializedName("nationality")
    @Expose
    private String nationality = "";

    @SerializedName("country_code")
    @Expose
    private String country_code = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }
}

