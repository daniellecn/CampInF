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
    private int tripID;
    private String tripName;
    private int areaID;
    private List<String> tripSeasons;
    private List<Integer> tripTypes;
    private List<String> tripEquipment;
    private List<TripComments> tripComments;
    private int friendsNum;
    private String details;
    private int level;
    private double lastUpdated;

    public Trip(){
    }

    public Trip(int tripID, String tripName, int areaID, List<String> tripSeasons,
                List<Integer> tripTypes, List<String> tripEquipment, List<TripComments> tripComments,
                int friendsNum, String details, int level) {
        this.tripID = tripID;
        this.tripName = tripName;
        this.areaID = areaID;
        this.tripSeasons = tripSeasons;
        this.tripTypes = tripTypes;
        this.tripEquipment = tripEquipment;
        this.tripComments = tripComments;
        this.friendsNum = friendsNum;
        this.details = details;
        this.level = level;
    }


    public Trip(int tripID, String tripName, int areaID, int friendsNum, String details, int level) {
        this.tripID = tripID;
        this.tripName = tripName;
        this.areaID = areaID;
        this.friendsNum = friendsNum;
        this.details = details;
        this.level = level;
    }

    public int getTripID() {
        return tripID;
    }

    public void setTripID(int tripID) {
        this.tripID = tripID;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public int getAreaID() {
        return areaID;
    }

    public void setAreaID(int areaID) {
        this.areaID = areaID;
    }

    public List<String> getTripSeasons() {
        return tripSeasons;
    }

    public void setTripSeasons(List<String> tripSeasons) {
        this.tripSeasons = tripSeasons;
    }

    public List<Integer> getTripTypes() {
        return tripTypes;
    }

    public void setTripTypes(List<Integer> tripTypes) {
        this.tripTypes = tripTypes;
    }

    public List<String> getTripEquipment() {
        return tripEquipment;
    }

    public void setTripEquipment(List<String> tripEquipment) {
        this.tripEquipment = tripEquipment;
    }

    public List<TripComments> getTripComments() {
        return tripComments;
    }

    public void setTripComments(List<TripComments> tripComments) {
        this.tripComments = tripComments;
    }

    public int getFriendsNum() {
        return friendsNum;
    }

    public void setFriendsNum(int friendsNum) {
        this.friendsNum = friendsNum;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public double getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(double lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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
        result.put("level", getLevel());
        result.put("lastUpdated", ServerValue.TIMESTAMP);
        return result;
    }
}
