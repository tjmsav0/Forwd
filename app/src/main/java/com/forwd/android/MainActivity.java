package com.forwd.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

public class MainActivity extends AppCompatActivity {

    // Filter on "#!" in the Android Monitor to easily find debugging info
    private static final String TAG = "#!MainActivity";

    //Why @Override is used: https://jonathonbevan.wordpress.com/tag/override/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Activity created");
        Log.d(TAG, "Initializing Facebook SDK");
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        if (AccessToken.getCurrentAccessToken() == null) {
            Log.d(TAG, "No Facebook access token found, starting Login Activity - " +
                    "Main Activity will be destroyed");
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

            // Destroy the Main Activity so that the user cannot browse back to it
            finish();

        } else {
            // TODO: get user data from server
            Log.d(TAG, "Facebook access token found");
            Helper.logFBTokenAttributes(TAG, AccessToken.getCurrentAccessToken());

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Activity destroyed");
    }
}