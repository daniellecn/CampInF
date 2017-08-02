package com.campin.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by noam on 03/06/2017.
 */

public class PlannedTrip {
    private String id = "";
    private String creator = "";
    private int tripId;
    private ArrayList<String> friends;
    private HashMap<String,String> friendsVoting;
    private boolean isCompleted = false;
    private String firstOption = null;
    private String secOption = null;

    public PlannedTrip(String creator, int tripId, String firstOption, String secOption) {

        this.creator = creator;
        this.tripId = tripId;
        this.firstOption = firstOption;
        this.secOption = secOption;
    }

    public PlannedTrip ()
    {

    }



    public String getCreator() {
        return creator;
    }

    public void setCreator(String creatorId) {
        this.creator = creatorId;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<String> friendInTrip) {
        this.friends = friendInTrip;
    }

    public HashMap<String, String> getFriendsVoting() {
        return friendsVoting;
    }

    public void setFriendsVoting(HashMap<String, String> friendsVoting) {
        this.friendsVoting = friendsVoting;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getFirstOption() {
        return firstOption;
    }

    public void setFirstOption(String firstOption) {
        this.firstOption = firstOption;
    }

    public String getSecOption() {
        return secOption;
    }

    public void setSecOption(String secOption) {
        this.secOption = secOption;
    }

    public String getId() {
        return id;
    }

    public void setId(String tripId) {
        id = tripId;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", getId());
        result.put("creator", getCreator());
        result.put("tripId", getTripId());
        result.put("firstOption", getFirstOption());
        result.put("secOption", getSecOption());
        result.put("friends", getFriends());
        result.put("isCompleted", isCompleted());
        return result;
    }
}
