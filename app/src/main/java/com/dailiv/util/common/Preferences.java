package com.dailiv.util.common;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.dailiv.internal.data.local.pojo.Location;
import com.dailiv.internal.data.remote.response.location.LocationResponse;
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

    public static void setAccessTokenAndSlug(String accessToken, String slug) {
        SharedPreferences.Editor editor = getPrefsEditor();

        editor.putString(IConstants.ACCESS_TOKEN, accessToken);
        editor.putString(IConstants.ACCOUNT_SLUG, slug);
        editor.apply();
    }

    public static String getAccessToken() {
        return getPrefs().getString(IConstants.ACCESS_TOKEN, null);
    }

    public static String getAccountSlug() {

        return getPrefs().getString(IConstants.ACCOUNT_SLUG, null);
    }

    public static boolean isAccessTokenAvailable() {
        return getAccessToken() != null;
    }

    public static void deleteAccessTokenAndSlug(){
        setAccessTokenAndSlug(null, null);
    }

    public static void deleteLocation() {
        setLocation(0, null, 0);
    }

    public static void setFinishOnboard() {
        SharedPreferences.Editor editor = getPrefsEditor();

        editor.putBoolean(IConstants.FINIHS_ONBOARD, true);
        editor.apply();
    }

    public static boolean isFinishOnboard() {
        return getPrefs().getBoolean(IConstants.FINIHS_ONBOARD, false);
    }

    public static void setLocation(LocationResponse location) {

        setLocation(location.id, location.address, location.storeId);
    }

    private static void setLocation(int id, String address, int storeId) {

        SharedPreferences.Editor editor = getPrefsEditor();

        editor.putInt(IConstants.LOCATION_ID, id);
        editor.putString(IConstants.LOCATION_NAME, address);
        editor.putInt(IConstants.STORE_ID, storeId);

        editor.apply();
    }

    public static Location getLocation() {

        return new Location(
                getPrefs().getInt(IConstants.LOCATION_ID, 0),
                getPrefs().getString(IConstants.LOCATION_NAME, null),
                getPrefs().getInt(IConstants.STORE_ID, 0)
        );
    }


}
