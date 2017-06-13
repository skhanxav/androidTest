package com.xav.country.info;

import android.app.Application;

import com.xav.country.info.database.DbHelper;

/**
 * Created by skhan4 on 6/13/2017.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        /** initialisation of SQLite database */
        DbHelper.init(this);
    }
}
