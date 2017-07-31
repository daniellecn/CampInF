package com.campin.Utils;

import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Danielle Cohen on 28/07/2017.
 */

public class Area {
    private int code;
    private String description;
    private double lastUpdated;

    public Area() {
    }

    public Area(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(double lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("code", getCode());
        result.put("description", getDescription());
        result.put("lastUpdated", ServerValue.TIMESTAMP);
        return result;
    }
}
