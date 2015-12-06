package com.forwd.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "#!MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Activity created");
        Log.d(TAG, "Initializing Facebook SDK");
        FacebookSdk.sdkInitialize(getApplicationContext());

        if (AccessToken.getCurrentAccessToken() == null) {
            Log.d(TAG, "No Facebook access token found, starting Login Activity - " +
                    "Main Activity will be destroyed");
            Intent intent = new Intent(this, LoginActivity.class);

            if (getIntent().getData() != null) {
                intent.setData(getIntent().getData());
            }
            startActivity(intent);
            finish();

        } else {
            Log.d(TAG, "Facebook access token found");
            Helper.logFBTokenAttributes(TAG, AccessToken.getCurrentAccessToken());
            setContentView(R.layout.activity_main);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Activity destroyed");
    }
}