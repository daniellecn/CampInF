package com.campin.Utils;

import com.google.firebase.database.ServerValue;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by noam on 22/05/2017.
 */

public class User implements Serializable {

    public static User _user = null;
    public static  boolean isSignUp = false;
    private String id;
    private String name;
    private String email;
    private String birthday;
    private String location;
    private String friends;
    private boolean isCar = false;
    private List<Integer> preferedAreas = new ArrayList<>();
    private List<Integer> preferedTypes = new ArrayList<>();
    private int level;
    private byte[] profileImage;
    private String urlCover;
    private boolean isShowFriends = false;
    private double lastUpdated;


    public static User getInstance()
    {
        if (_user == null)
        {
            _user = new User();
        }

        return  _user;
    }

    public User(String userId, String fullName, String email, String birthday, String location,
                String friends, boolean isCar, List<Integer> preferedAreas, List<Integer> preferedTypes,
                int level, byte[] profileImage, String urlCover, boolean isShowFriends) {

        this.id = userId;
        this.name = fullName;
        this.email = email;
        this.birthday = birthday;
        this.location = location;
        this.friends = friends;
        this.isCar = isCar;
        this.preferedAreas = preferedAreas;
        this.preferedTypes = preferedTypes;
        this.level = level;
        this.profileImage = profileImage;
        this.urlCover = urlCover;
        this.isShowFriends = isShowFriends;
    }

    public User(){
    }

    public User(String userId, String fullName, String email, String birthday, String location,
                String friends, boolean isCar, int level, String urlCover,
                boolean isShowFriends) {
        this.id = userId;
        this.name = fullName;
        this.email = email;
        this.birthday = birthday;
        this.location = location;
        this.friends = friends;
        this.isCar = isCar;
        this.level = level;
//        this.profileImage = profileImage;
        this.urlCover = urlCover;
        this.isShowFriends = isShowFriends;
    }

    public static boolean isSignUp() {
        return isSignUp;
    }

    public static void setIsSignUp(boolean isSignUp) {
        User.isSignUp = isSignUp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean getIsCar() {
        return isCar;
    }

    public void setIsCar(boolean car) {
        isCar = car;
    }

    public List<Integer> getPreferedAreas() {
        return preferedAreas;
    }

    public void setPreferedAreas(List<Integer> preferedAreas) {
        this.preferedAreas = preferedAreas;
    }

    public List<Integer> getPreferedTypes() {
        return preferedTypes;
    }

    public void setPreferedTypes(List<Integer> preferedTypes) {
        this.preferedTypes = preferedTypes;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public String getUrlCover() {
        return urlCover;
    }

    public void setUrlCover(String urlCover) {
        this.urlCover = urlCover;
    }

    public boolean isShowFriends() {
        return isShowFriends;
    }

    public void setShowFriends(boolean showFriends) {
        isShowFriends = showFriends;
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
        result.put("email", getEmail());
        result.put("birthday", getBirthday());
        result.put("email", getEmail());
        result.put("location", getLocation());
        result.put("friends", getFriends());
        result.put("isCar", getIsCar());
        result.put("preferedAreas", getPreferedAreas());
        result.put("preferedTypes", getPreferedTypes());
        result.put("level", getLevel());
//        result.put("profileImage", getProfileImage());
        result.put("urlCover", getUrlCover());
        result.put("isShowFriends", isShowFriends());
        result.put("lastUpdated", ServerValue.TIMESTAMP);
        return result;
    }
}

