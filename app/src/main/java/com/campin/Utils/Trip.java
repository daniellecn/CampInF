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
    private int id;
    private String name;
    private int area;
    private List<String> seasons;
    private List<Integer> types;
    private List<String> equipment;
    private List<TripComments> comments;
    private int friendsNum;
    private String details;
    private int level;
    private double lastUpdated;

    public Trip(){
    }

    public Trip(int id, String name, int area, List<String> seasons, List<Integer> types,
                List<String> equipment, List<TripComments> comments, int friendsNum, String details, int level) {
        this.id = id;
        this.name = name;
        this.area = area;
        this.seasons = seasons;
        this.types = types;
        this.equipment = equipment;
        this.comments = comments;
        this.friendsNum = friendsNum;
        this.details = details;
        this.level = level;
    }

    public Trip(int id, String name, int area, int friendsNum, String details, int level) {
        this.id = id;
        this.name = name;
        this.area = area;
        this.friendsNum = friendsNum;
        this.details = details;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public List<String> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<String> seasons) {
        this.seasons = seasons;
    }

    public List<Integer> getTypes() {
        return types;
    }

    public void setTypes(List<Integer> types) {
        this.types = types;
    }

    public List<String> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<String> equipment) {
        this.equipment = equipment;
    }

    public List<TripComments> getComments() {
        return comments;
    }

    public void setComments(List<TripComments> comments) {
        this.comments = comments;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(double lastUpdated) {
        this.lastUpdated = lastUpdated;
    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", getId());
        result.put("name", getName());
        result.put("area", getArea());
        result.put("seasons", getSeasons());
        result.put("types", getTypes());
        result.put("equipment", getEquipment());
        result.put("comments", getComments());
        result.put("friendsNum", getFriendsNum());
        result.put("details", getDetails());
        result.put("level", getLevel());
        result.put("lastUpdated", ServerValue.TIMESTAMP);
        return result;
    }
}
