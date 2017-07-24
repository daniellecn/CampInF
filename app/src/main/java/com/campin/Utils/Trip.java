package com.campin.Utils;

import android.content.pm.InstrumentationInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by noam on 25/05/2017.
 * Updated by Igor Bukartyk 7/24/2017.
 */

public class Trip
{
    private String _tripName;
    private String _area = "";
    private int _tripLevel;
    private int _tripID;
    private int _tripCode;
    private List<String> _tripType;
    private List<String> _tripSeason;
    private List<String> _tripEquipment;
    private List<TripComments> _TripCOmments;

    public Trip()
    {
    }

    public Trip(String tripName, String area, int tripLevel, int tripID)
    {
        _tripName = tripName;
        _area =area;
        _tripLevel = tripLevel;
        _tripID = tripID;
    }

    public int getTripId() {
        return _tripID;
    }

    public void setTripId(int tripId) {
        _tripID = tripId;
    }

    public String getArea() {
        return _area;
    }

    public void setArea(String area) {
        this._area = area;
    }

    public String getTripName() {
        return _tripName;
    }

    public void setTripName(String tripName) {
        this._tripName = tripName;
    }

    public int getLevel() {
        return _tripLevel;
    }

    public void setLevel(int tripLevel) {
        this._tripLevel = tripLevel;
    }

    public int get_tripCode() {
        return _tripCode;
    }

    public void set_tripCode(int _tripCode) {
        this._tripCode = _tripCode;
    }

    public List<String> get_tripType() {
        return _tripType;
    }

    public void set_tripType(List<String> _tripType) {
        this._tripType = _tripType;
    }

    public List<String> get_tripSeason() {
        return _tripSeason;
    }

    public void set_tripSeason(List<String> _tripSeason) {
        this._tripSeason = _tripSeason;
    }

    public List<String> get_tripEquipment() {
        return _tripEquipment;
    }

    public void set_tripEquipment(List<String> _tripEquipment) {
        this._tripEquipment = _tripEquipment;
    }

    public List<TripComments> get_TripCOmments() {
        return _TripCOmments;
    }

    public void set_TripCOmments(List<TripComments> _TripCOmments) {
        this._TripCOmments = _TripCOmments;
    }


    public Map<String, Object> toMap()
    {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", getTripId());
        result.put("area", getArea());
        result.put("tripName", getTripName());
        result.put("tripLevel", getLevel());

        return result;
    }

}
