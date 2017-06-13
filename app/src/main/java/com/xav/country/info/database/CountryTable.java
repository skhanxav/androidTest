package com.xav.country.info.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xav.country.info.model.CountryModel;
import com.xav.country.info.model.CurrencyModel;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Skhan4 on 6/13/2017.
 */

public class CountryTable {

    public static final String TABLE_NAME = "country";
    //Columns
    public static final String NUMERIC_CODE = "numeric_code";
    public static final String NAME = "name";
    public static final String FLAG = "flag";
    public static final String CURRENCY = "currency";

    public static void init(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NUMERIC_CODE + " TEXT,"
                + NAME + " TEXT,"
                + FLAG + " TEXT,"
                + CURRENCY + " TEXT)");
    }

    public static void onUpgrade(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public static final void insertCountry(CountryModel country) {
        SQLiteDatabase db = DbHelper.getInstance().getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(NUMERIC_CODE, country.getNumericCode());
            values.put(NAME, country.getName());
            values.put(FLAG, country.getFlag());
            values.put(CURRENCY, new Gson().toJson(country.getCurrencies()));
            /**
             * Handling duplicate case.
             * when duplicate it will update previous one
             * otherwise insert new row
             */
            int row = db.update(TABLE_NAME, values, NAME + "=?", new String[]{country.getName()});
            if (row < 1) {
                db.insert(TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("DB Insertion Exception ", e.toString());
        } finally {
            db.endTransaction();
        }
    }

    public static final HashMap<String, CountryModel> getCountries() {
        HashMap<String, CountryModel> map = new HashMap<>();
        String query = "SELECT *  FROM " + TABLE_NAME + ";";
        SQLiteDatabase db = DbHelper.getInstance().getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                CountryModel country = new CountryModel();
                country.setNumericCode(cursor.getString(cursor.getColumnIndex(NUMERIC_CODE)));
                country.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                country.setFlag(cursor.getString(cursor.getColumnIndex(FLAG)));
                country.setCurrencies(convertToCurrencyList(cursor.getString(cursor.getColumnIndex(CURRENCY))));
                map.put(cursor.getString(cursor.getColumnIndex(NUMERIC_CODE)), country);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return map;
    }

    public static final CountryModel getSelectedCountry(String name) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + NAME + "='" + name + "'";
        SQLiteDatabase db = DbHelper.getInstance().getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            CountryModel country = new CountryModel();
            country.setNumericCode(cursor.getString(cursor.getColumnIndex(NUMERIC_CODE)));
            country.setName(cursor.getString(cursor.getColumnIndex(NAME)));
            country.setFlag(cursor.getString(cursor.getColumnIndex(FLAG)));
            country.setCurrencies(convertToCurrencyList(cursor.getString(cursor.getColumnIndex(CURRENCY))));
            cursor.close();
            return country;
        } else {
            cursor.close();
        }
        return null;
    }

    //Convert string json currency to ArrayList of appropriate model
    private static List<CurrencyModel> convertToCurrencyList(String data) {
        TypeToken<List<CurrencyModel>> token = new TypeToken<List<CurrencyModel>>() {
        };
        List<CurrencyModel> currency = new Gson().fromJson(data, token.getType());
        return currency;
    }
}
