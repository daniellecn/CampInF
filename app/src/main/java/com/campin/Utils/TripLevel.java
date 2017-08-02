package com.campin.Utils;

import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Danielle Cohen on 28/07/2017.
 */

public class TripLevel {
    private int Code;
    private String Level;
    private double lastUpdated;

    public TripLevel() {
    }

    public TripLevel(int code, String description) {
        this.Code = code;
        this.Level = description;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        this.Code = code;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String description) {
        this.Level = description;
    }

    public double getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(double lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("Code", getCode());
        result.put("Level", getLevel());
        result.put("lastUpdated",  ServerValue.TIMESTAMP);
        return result;
    }
}
