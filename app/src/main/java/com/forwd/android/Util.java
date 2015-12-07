package com.forwd.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.AccessToken;

/**
 * Created by tim on 05/12/15.
 */
class Util {

    static void logFacebookTokenAttributes(String TAG, AccessToken accessToken) {
        Log.d(TAG, "Facebook access token value: " + accessToken.getToken());
        Log.d(TAG, "Facebook access token user id: " + accessToken.getUserId());
        Log.d(TAG, "Facebook access token application id: " + accessToken.getApplicationId());
        Log.d(TAG, "Facebook access token expires: " + accessToken.getExpires());
        Log.d(TAG, "Facebook access token permissions: " + accessToken.getPermissions());
        Log.d(TAG, "Facebook access token permissions declined: " +
                accessToken.getDeclinedPermissions());
    }

    static void preserveRequestedURI(AppCompatActivity activity, Intent intent) {
        if (activity.getIntent().getData() != null) {
            intent.setData(activity.getIntent().getData());
        }
    }
}
