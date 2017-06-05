package com.campin.Utils;

import android.content.pm.InstrumentationInfo;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by noam on 25/05/2017.
 */

public class Trip {
    private String _tripName;
    private String _area = "";

    public Trip(String tripName, String area)
    {
        _tripName = tripName;
        _area =area;
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
}
