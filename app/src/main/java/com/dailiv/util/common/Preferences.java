package com.dailiv.util.common;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.dailiv.util.IConstants;

import static com.dailiv.App.getContext;

/**
 * Created by aldo on 3/17/18.
 */

public class Preferences {

    private static SharedPreferences getPrefs(){
        return PreferenceManager.getDefaultSharedPreferences(getContext());
    }

    private static SharedPreferences.Editor getPrefsEditor() {
        return getPrefs().edit();
    }

    public static void setAccessToken(String accessToken) {
        SharedPreferences.Editor editor = getPrefsEditor();

        editor.putString(IConstants.ACCESS_TOKEN, accessToken);
        editor.apply();
    }

    public static String getAccessToken() {
        return getPrefs().getString(IConstants.ACCESS_TOKEN, null);
    }

    public static boolean isAccessTokenAvailable() {
        return getAccessToken() != null;
    }

    public static void deleteAccessToken(){
        setAccessToken(null);
    }

    public static void setFinishOnboard() {
        SharedPreferences.Editor editor = getPrefsEditor();

        editor.putBoolean(IConstants.FINIHS_ONBOARD, true);
        editor.apply();
    }

    public static boolean isFinishOnboard() {
        return getPrefs().getBoolean(IConstants.FINIHS_ONBOARD, false);
    }
}
