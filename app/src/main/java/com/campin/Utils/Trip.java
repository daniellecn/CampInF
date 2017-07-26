package com.campin.Utils;

import android.content.pm.InstrumentationInfo;

import com.google.firebase.database.ServerValue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by noam on 25/05/2017.
 * Updated by Igor Bukartyk 7/24/2017.
 * Updated by Danielle on 25/07/2017
 */

public class Trip
{
    private int _tripID;
    private String _tripName;
    private int _areaID;
    private List<String> _tripSeasons;
    private List<Integer> _tripTypes;
    private List<String> _tripEquipment;
    private List<TripComments> _tripComments;
    private int _friendsNum;
    private String _details;

    private double _lastUpdated;

    public Trip(){
    }

    public Trip(int _tripID, String _tripName, int _areaID, List<String> _tripSeasons,
                List<Integer> _tripTypes, List<String> _tripEquipment, List<TripComments> _tripComments,
                int _friendsNum, String _details) {
        this._tripID = _tripID;
        this._tripName = _tripName;
        this._areaID = _areaID;
        this._tripSeasons = _tripSeasons;
        this._tripTypes = _tripTypes;
        this._tripEquipment = _tripEquipment;
        this._tripComments = _tripComments;
        this._friendsNum = _friendsNum;
        this._details = _details;
    }

    public Trip(int _tripID, String _tripName, int _areaID, int _friendsNum, String _details) {
        this._tripID = _tripID;
        this._tripName = _tripName;
        this._areaID = _areaID;
        this._friendsNum = _friendsNum;
        this._details = _details;
    }

    public int getTripID() {
        return _tripID;
    }

    public void setTripID(int _tripID) {
        this._tripID = _tripID;
    }

    public String getTripName() {
        return _tripName;
    }

    public void setTripName(String _tripName) {
        this._tripName = _tripName;
    }

    public int getAreaID() {
        return _areaID;
    }

    public void setAreaID(int _areaID) {
        this._areaID = _areaID;
    }

    public List<String> getTripSeasons() {
        return _tripSeasons;
    }

    public void setTripSeasons(List<String> _tripSeasons) {
        this._tripSeasons = _tripSeasons;
    }

    public List<Integer> getTripTypes() {
        return _tripTypes;
    }

    public void setTripTypes(List<Integer> _tripTypes) {
        this._tripTypes = _tripTypes;
    }

    public List<String> getTripEquipment() {
        return _tripEquipment;
    }

    public void setTripEquipment(List<String> _tripEquipment) {
        this._tripEquipment = _tripEquipment;
    }

    public List<TripComments> getTripComments() {
        return _tripComments;
    }

    public void setTripComments(List<TripComments> _tripComments) {
        this._tripComments = _tripComments;
    }

    public int getFriendsNum() {
        return _friendsNum;
    }

    public void setFriendsNum(int _friendsNum) {
        this._friendsNum = _friendsNum;
    }

    public String getDetails() {
        return _details;
    }

    public void setDetails(String _details) {
        this._details = _details;
    }

    public double getLastUpdated() {
        return _lastUpdated;
    }

    public void setLastUpdated(double _lastUpdated) {
        this._lastUpdated = _lastUpdated;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", getTripID());
        result.put("name", getTripName());
        result.put("area", getAreaID());
        result.put("seasons", getTripSeasons());
        result.put("types", getTripTypes());
        result.put("equipment", getTripEquipment());
        result.put("comments", getTripComments());
        result.put("friends", getFriendsNum());
        result.put("details", getDetails());
        result.put("lastUpdated", ServerValue.TIMESTAMP);
        return result;
    }


//    private String _tripName;
//    private String _area = "";
//    private int _tripLevel;
//    private int _tripID;
//    private int _tripCode;
//    private List<String> _tripType;
//    private List<String> _tripSeason;
//    private List<String> _tripEquipment;
//    private List<TripComments> _TripComments;
//
//    public Trip()
//    {
//    }
//
//    public Trip(String tripName, String area, int tripLevel, int tripID)
//    {
//        _tripName = tripName;
//        _area =area;
//        _tripLevel = tripLevel;
//        _tripID = tripID;
//    }
//
//    public int getTripId() {
//        return _tripID;
//    }
//
//    public void setTripId(int tripId) {
//        _tripID = tripId;
//    }
//
//    public String getArea() {
//        return _area;
//    }
//
//    public void setArea(String area) {
//        this._area = area;
//    }
//
//    public String getTripName() {
//        return _tripName;
//    }
//
//    public void setTripName(String tripName) {
//        this._tripName = tripName;
//    }
//
//    public int getLevel() {
//        return _tripLevel;
//    }
//
//    public void setLevel(int tripLevel) {
//        this._tripLevel = tripLevel;
//    }
//
//    public int get_tripCode() {
//        return _tripCode;
//    }
//
//    public void set_tripCode(int _tripCode) {
//        this._tripCode = _tripCode;
//    }
//
//    public List<String> get_tripType() {
//        return _tripType;
//    }
//
//    public void set_tripType(List<String> _tripType) {
//        this._tripType = _tripType;
//    }
//
//    public List<String> get_tripSeason() {
//        return _tripSeason;
//    }
//
//    public void set_tripSeason(List<String> _tripSeason) {
//        this._tripSeason = _tripSeason;
//    }
//
//    public List<String> get_tripEquipment() {
//        return _tripEquipment;
//    }
//
//    public void set_tripEquipment(List<String> _tripEquipment) {
//        this._tripEquipment = _tripEquipment;
//    }
//
//    public List<TripComments> get_TripCOmments() {
//        return _TripComments;
//    }
//
//    public void set_TripComments(List<TripComments> _TripCOmments) {
//        this._TripComments = _TripCOmments;
//    }
//
//
//    public Map<String, Object> toMap()
//    {
//        HashMap<String, Object> result = new HashMap<>();
//        result.put("id", getTripId());
//        result.put("area", getArea());
//        result.put("tripName", getTripName());
//        result.put("tripLevel", getLevel());
//
//        return result;
//    }

}
