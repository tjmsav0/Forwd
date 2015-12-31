package com.forwd.android;

import android.util.Log;

import com.facebook.AccessToken;

class ClientFacebookHelper {

    private static final String TAG = "#!" + ClientFacebookHelper.class.getName();
    private static final int SLEEP_MILLISECONDS = 100;

    static String getValidTokenOnStartup() {

        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        // First attempt
        if (accessToken == null) {

            // Allow time for Facebook SDK to initialize
            try {
                Thread.sleep(SLEEP_MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Second attempt
            accessToken = AccessToken.getCurrentAccessToken();

            if (accessToken == null) {
                return null;
            } else {
                logTokenAttributes(accessToken);
                return accessToken.getToken();
            }
        } else {
            logTokenAttributes(accessToken);
            return accessToken.getToken();
        }
    }

    private static void logTokenAttributes(AccessToken accessToken) {

        Log.d(TAG, "Facebook access token value: " + accessToken.getToken());
        Log.d(TAG, "Facebook access token user id: " + accessToken.getUserId());
        Log.d(TAG, "Facebook access token application id: " + accessToken.getApplicationId());
        Log.d(TAG, "Facebook access token expires: " + accessToken.getExpires());
        Log.d(TAG, "Facebook access token permissions: " + accessToken.getPermissions());
        Log.d(TAG, "Facebook access token permissions declined: " +
                accessToken.getDeclinedPermissions());
    }
}
