package com.exomatik.desacenranabaru.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by IrfanRZ on 02/08/2019.
 */

public class UserPreferences {
    private SharedPreferences preferences;


    public UserPreferences(Context paramContext) {
        this.preferences = paramContext.getSharedPreferences("UserPref", 0);
    }

    public void setSaveString(String data, String key) {
        Editor prefsEditor = preferences.edit();
        prefsEditor.putString(key, data);
        prefsEditor.apply();
    }

    public String getSaveString(String key) {
        String value = preferences.getString(key, "");

        return value;
    }

}
