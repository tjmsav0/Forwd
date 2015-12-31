package com.forwd.android;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.forwd.backend.forwdApi.ForwdApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "#!" + MainActivity.class.getName();
    private static ForwdApi myApiService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Activity created");
        Log.d(TAG, "Initializing Facebook SDK");
        FacebookSdk.sdkInitialize(getApplicationContext());

        String facebookToken = ClientFacebookHelper.getValidTokenOnStartup();

        if (facebookToken == null) {
            Log.d(TAG, "No Facebook token found, starting Login Activity");
            Intent intent = new Intent(this, LoginActivity.class);
            forwardURI(this, intent);
            startActivity(intent);
            finish();
        } else {
            Log.d(TAG, "Facebook token found");
            setContentView(R.layout.activity_main);
            new GetUserDataTask().execute(new Pair<Context, String>(this,
                    facebookToken));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Activity destroyed");
    }

    protected void forwardURI(AppCompatActivity activity, Intent intent) {
        if (activity.getIntent().getData() != null) {
            intent.setData(activity.getIntent().getData());
        }
    }


    private class GetUserDataTask extends AsyncTask<Pair<Context, String>, Void, String> {

        private Context context;

        @Override
        protected String doInBackground(Pair<Context, String>... params) {
            if (myApiService == null) {  // Only do this once
                ForwdApi.Builder builder = new ForwdApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)

                        .setRootUrl("https://forwd-app.appspot.com/_ah/api/");

                myApiService = builder.build();
            }

            context = params[0].first;
            String token = params[0].second;

            try {
                return myApiService.getUserData(token).execute().getData();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        }
    }
}