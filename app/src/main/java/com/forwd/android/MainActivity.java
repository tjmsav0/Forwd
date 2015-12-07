package com.forwd.android;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.forwd.backend.testApi.TestApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "#!MainActivity";
    private static final int SLEEP_MILLISECONDS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Activity created");
        Log.d(TAG, "Initializing Facebook SDK");
        FacebookSdk.sdkInitialize(getApplicationContext());

        if (!facebookTokenExists()) {
            Log.d(TAG, "No Facebook access token found, starting Login Activity");
            Intent intent = new Intent(this, LoginActivity.class);
            Util.preserveRequestedURI(this, intent);
            startActivity(intent);
            finish();
        } else {
            Log.d(TAG, "Facebook access token found");
            Util.logFacebookTokenAttributes(TAG, AccessToken.getCurrentAccessToken());
            setContentView(R.layout.activity_main);
            new userAuthenticationTask().execute(new Pair<Context, String>(this,
                    AccessToken.getCurrentAccessToken().getToken()));
        }
    }

    protected boolean facebookTokenExists() {

        // First attempt
        if (AccessToken.getCurrentAccessToken() == null) {

            // Allow time for Facebook SDK to initialize
            try {
                Thread.sleep(SLEEP_MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Second attempt
            if (AccessToken.getCurrentAccessToken() == null) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Activity destroyed");
    }
}

class userAuthenticationTask extends AsyncTask<Pair<Context, String>, Void, String> {
    private static TestApi myApiService = null;
    private Context context;

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        if(myApiService == null) {  // Only do this once
            TestApi.Builder builder = new TestApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest request) throws IOException {

                    HttpHeaders headers = new HttpHeaders();
                    headers.set("You-Are-A", "Genius");
                    request.setHeaders(headers);
                }
            })
                    .setRootUrl("https://forwd-app.appspot.com/_ah/api/");

            myApiService = builder.build();
        }

        context = params[0].first;
        String name = params[0].second;

        try {
            return myApiService.sayHi(name).execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }
}