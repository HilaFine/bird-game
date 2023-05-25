package com.example.birdsGameEx2.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class MySP {
    private static final String DB_FILE = "DB_FILE";
    private static MySP instance = null;
    private SharedPreferences sharedPreferences;
    private static MySP mySP;

    private MySP(Context context){
        if(instance == null){
            instance = new MySP(context);
        }
    }
    public static MySP getInstance(Context context) {
        if (mySP == null){
            mySP = new MySP(context);
        }
        return mySP;
    }

    public String getString(String key, String value) {

        return sharedPreferences.getString(key, value);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public int getInt(String key, int value) {
        return sharedPreferences.getInt(key, value);
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }
    public int getIntSP(String key, int value) {
        return sharedPreferences.getInt(key, value);
    }

    public void putIntSP(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

}
