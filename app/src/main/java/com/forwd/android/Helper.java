package com.forwd.android;

import android.util.Log;

import com.facebook.AccessToken;

/**
 * Created by tim on 05/12/15.
 */
public class Helper {

    public static void logFBTokenAttributes(String TAG, AccessToken accessToken) {
        Log.d(TAG, "Facebook access token value: " + accessToken.getToken());
        Log.d(TAG, "Facebook access token user id: " + accessToken.getUserId());
        Log.d(TAG, "Facebook access token application id: " + accessToken.getApplicationId());
        Log.d(TAG, "Facebook access token expires: " + accessToken.getExpires());
        Log.d(TAG, "Facebook access token permissions: " + accessToken.getPermissions());
        Log.d(TAG, "Facebook access token permissions declined: " +
                accessToken.getDeclinedPermissions());
    }
}
