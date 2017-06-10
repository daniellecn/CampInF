package com.campin.Utils;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by noam on 22/05/2017.
 */

public class User implements Serializable {

    private static User _user = null;
    public static  boolean isSignUp = false;
    private String email = "";
    private String birthday = "";
    private String userId = "";
    private String fullName = "";
    private String urlCover = "";
    private String location = "";
    private String friends = "";
    private ArrayList<String> preferedAreas = new ArrayList<String>();
    private byte[] byteArray;
    private boolean isShowFriends = false;
    private boolean isCar = false;


    public static User getInstance()
    {
        if (_user == null)
        {
            _user = new User();
        }

        return  _user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUrlCover() {
        return urlCover;
    }

    public void setUrlCover(String urlCover) {
        this.urlCover = urlCover;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFriends() {
        return friends;
    }

    public void setFriends(String friends) {
        this.friends = friends;
    }

    public byte[] getByteArray() {
        return byteArray;
    }

    public void setByteArray(byte[] byteArray) {
        this.byteArray = byteArray;
    }

    public boolean isShowFriends() {
        return isShowFriends;
    }

    public void setShowFriends(boolean showFriends) {
        isShowFriends = showFriends;
    }

    public ArrayList<String> getPreferedAreas() {
        return preferedAreas;
    }

    public void setPreferedAreas(ArrayList<String> preferedAreas) {
        this.preferedAreas = preferedAreas;
    }

    public boolean isCar() {
        return isCar;
    }

    public void setCar(boolean car) {
        isCar = car;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", getUserId());
        result.put("name", getFullName());
        result.put("isCar", isCar());
        result.put("preferedAreas", getPreferedAreas());
        return result;
    }
}
