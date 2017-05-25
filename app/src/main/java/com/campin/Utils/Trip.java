package com.campin.Utils;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by noam on 25/05/2017.
 */

public class Trip {

    private String firstOption = null;
    private String secOption = null;
    private String area = "";
    private ArrayList<String> friends = null;
    private String creatorId = null;

    public Trip(String first, String sec, String areaChosen)
    {
        firstOption = first;
        secOption = sec;
        area =areaChosen;
        creatorId = User.getInstance().getUserId();
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }
}
