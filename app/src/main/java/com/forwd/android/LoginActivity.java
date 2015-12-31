package com.forwd.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "#!" + LoginActivity.class.getName();
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Activity created");
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);

        loginButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {

                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG, "Facebook Login 'onSuccess' invoked");
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        forwardURI(LoginActivity.this, intent);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "Facebook Login 'onCancel' invoked");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.d(TAG, "Facebook Login 'onError' invoked with exception " + exception);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
}