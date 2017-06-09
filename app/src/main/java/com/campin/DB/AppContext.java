package com.campin.DB;

import android.content.Context;
import android.app.Application;

/**
 * Created by noam on 03/06/2017.
 */

public class AppContext extends Application{
    private static Context context;

    public void onCreate() {
        super.onCreate();
        AppContext.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return AppContext.context;
    }
}
