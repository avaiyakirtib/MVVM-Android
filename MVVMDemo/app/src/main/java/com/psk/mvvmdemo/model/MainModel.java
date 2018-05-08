package com.psk.mvvmdemo.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MainModel {

    @SerializedName("results")
    private ArrayList<People> results = null;

    public ArrayList<People> getResults() {
        return results;
    }

    public void setResults(ArrayList<People> results) {
        this.results = results;
    }

}
