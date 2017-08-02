package com.campin.Utils;

import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Danielle Cohen on 28/07/2017.
 */

public class TripType {
    private int Code;
    private String Type;
    private double lastUpdated;

    public TripType() {
    }

    public TripType(int code, String description) {
        this.Code = code;
        this.Type = description;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        this.Code = code;
    }

    public String getType() {
        return Type;
    }

    public void setType(String description) {
        this.Type = description;
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
        result.put("Type", getType());
        result.put("lastUpdated",  ServerValue.TIMESTAMP);
        return result;
    }
}
