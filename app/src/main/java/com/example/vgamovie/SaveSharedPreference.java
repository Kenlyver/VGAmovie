package com.example.vgamovie;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {
    private SharedPreferences sharedPreferences;

    static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }


    public static void setLoggedIn(Context context, boolean loggedIn) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean("logged_in_status", loggedIn);
        editor.apply();
    }

    public static boolean getLoggedStatus(Context context) {
        return getPreferences(context).getBoolean("logged_in_status", false);
    }

    public static void saveData(Context context, String key,String value) {
        SharedPreferences.Editor prefsEditor = getPreferences(context).edit();
        prefsEditor .putString(key, value);
        prefsEditor.commit();
    }




    public static String getData(Context context, String key) {
        if (getPreferences(context)!= null) {
            return getPreferences(context).getString(key, "");
        }
        return "";
    }

}
