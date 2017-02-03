package com.cinnamon.moon.puzzle.Data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by moonp on 2017-01-08.
 */

public class PreferencesUtils {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public PreferencesUtils(Context context) {
        prefs = context.getSharedPreferences("mainPref", context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setLoginInfo(boolean value) {
        editor.putBoolean("loginInfo", value);
        editor.apply();
    }

    public boolean getLoginInfo() {
        return prefs.getBoolean("loginInfo", false);
    }

    public void setUserflow(ArrayList<String> userflow) {
        JSONArray array = new JSONArray();
        for (String anUserflow : userflow) array.put(anUserflow);
        if (!userflow.isEmpty())
            editor.putString("userflow", array.toString());
        else
            editor.putString("userflow", null);

        editor.apply();
    }

    public void setPage(String mainid, ArrayList<String> pageflow) {
        JSONArray array = new JSONArray();
        for (String anUserflow : pageflow) array.put(anUserflow);
        if (!pageflow.isEmpty())
            editor.putString(mainid, array.toString());
        else
            editor.putString(mainid, null);

        editor.apply();
    }


    public ArrayList<String> getData(String id) {
        ArrayList<String> list = new ArrayList<>();
        String json = prefs.getString(String.valueOf(id), null);
        if (json != null) {
            try {
                JSONArray array = new JSONArray(json);
                for (int num = 0; num < array.length(); num++) {
                    list.add(array.optString(num));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
