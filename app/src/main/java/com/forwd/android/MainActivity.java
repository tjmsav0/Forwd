package com.forwd.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

public class MainActivity extends AppCompatActivity {

    // Filter on "MainActivity" in the Android Monitor to easily find debugging info
    private static final String TAG = "MainActivity";

    //Why @Override is used: https://jonathonbevan.wordpress.com/tag/override/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Activity created");
        FacebookSdk.sdkInitialize(getApplicationContext());

        if (AccessToken.getCurrentAccessToken() == null) {
            // No facebook access token cached in user's device, so they must log in
            Log.d(TAG, "No facebook access token in cache, starting Login Activity");
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

            // Destroy the Main Activity so that the user cannot browse back to it
            finish();

        } else {
            // TODO: get user data from server
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Activity destroyed");
    }
}