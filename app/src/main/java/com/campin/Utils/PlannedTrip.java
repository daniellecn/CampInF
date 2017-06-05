package com.campin.Utils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by noam on 03/06/2017.
 */

public class PlannedTrip {
    private Trip trip;
    private int TripId;
    private String creatorId = "";
    private ArrayList<String> friendInTrip;
    private HashMap<String,String> friendsVoting;
    private boolean isCompleted = false;
    private String firstOption = null;
    private String secOption = null;

    public PlannedTrip (Trip t,String creator, String firstDate, String secDeate)
    {
        trip = t;
        creatorId = creator;
        firstOption = firstDate;
        secOption = secDeate;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public ArrayList<String> getFriendInTrip() {
        return friendInTrip;
    }

    public void setFriendInTrip(ArrayList<String> friendInTrip) {
        this.friendInTrip = friendInTrip;
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

    public int getTripId() {
        return TripId;
    }

    public void setTripId(int tripId) {
        TripId = tripId;
    }
}
