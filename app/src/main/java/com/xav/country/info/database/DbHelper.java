package com.xav.country.info.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Skhan4 on 6/13/2017.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String TAG = DbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "Demo.db";
    private static final int DATABASE_VERSION = 1;
    private static DbHelper instance;
    private static Context context;


    public static void init(Context context) {
        instance = new DbHelper(context);
    }

    public static synchronized DbHelper getInstance() {
        if (instance == null)
            instance = new DbHelper(context);
        return instance;
    }

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        CountryTable.init(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG,
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data.");
        CountryTable.onUpgrade(db);

    }

    public static void deleteDatabase() {
        context.deleteDatabase(DATABASE_NAME);
    }
}
