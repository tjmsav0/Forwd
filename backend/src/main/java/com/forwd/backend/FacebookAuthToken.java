package com.forwd.backend;

import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.appengine.repackaged.com.google.gson.GsonBuilder;
import com.google.appengine.repackaged.com.google.gson.JsonDeserializationContext;
import com.google.appengine.repackaged.com.google.gson.JsonDeserializer;
import com.google.appengine.repackaged.com.google.gson.JsonElement;
import com.google.appengine.repackaged.com.google.gson.JsonParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;

class FacebookAuthToken {

    private static final String APP_ID = "431526370376167";

    // TODO: Refresh APP_TOKEN before Go-Live
    private static final String APP_TOKEN = "wSm9w0OQGtku8Xk506vHy7NZAkY";

    private static final String URL_PLACEHOLDER =
            "https://graph.facebook.com/debug_token?input_token=userToken&access_token=accessToken";

    private static final String ACCESS_TOKEN = APP_ID + "|" + APP_TOKEN;

    private String app_id;
    private String application;
    private int expires_at;
    private boolean is_valid;
    private int issued_at;
    private String[] scopes;
    private String user_id;

    public static FacebookAuthToken getAuthToken(String userToken) {

        try {
            URL url = new URL(buildAuthTokenURL(userToken));
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            reader.close();
            String myJson = builder.toString();

            Gson gson = new GsonBuilder().registerTypeAdapter(FacebookAuthToken.class,
                    new AuthTokenDeserializer()).create();

            return gson.fromJson(myJson, FacebookAuthToken.class);

        } catch (IOException | JsonParseException e) {
            //TODO: Log the exception
            return null;
        }
    }

    public String getAppId() {
        return this.app_id;
    }

    public String getApp() {
        return this.application;
    }

    public int getExpiry() {
        return this.expires_at;
    }

    public boolean isValid() {
        return this.is_valid;
    }

    public int getCreated() {
        return this.issued_at;
    }

    public String[] getPermissions() {
        return this.scopes;
    }

    public String getUserID() {
        return this.user_id;
    }

    private static String buildAuthTokenURL(String userToken) {
        String result = URL_PLACEHOLDER.replaceAll("userToken", userToken);
        return result.replaceAll("accessToken", ACCESS_TOKEN);
    }

    private Boolean authTokenIsValid(FacebookAuthToken authToken) {
        return ((authToken.app_id.equals(APP_ID)) &&
                (authToken.expires_at > (System.currentTimeMillis() / 1000L)) &&
                (authToken.is_valid));
    }
}

/* Example response to be deserialized by AuthTokenDeserializer:
{
   "data": {
      "app_id": "431526370376167",
      "application": "Forwd",
      "expires_at": 1455462874,
      "is_valid": true,
      "issued_at": 1450278874,
      "scopes": [
         "public_profile"
      ],
      "user_id": "1660043107601868"
   }
}
 */

class AuthTokenDeserializer implements JsonDeserializer<FacebookAuthToken> {

    @Override
    public FacebookAuthToken deserialize(JsonElement jsonElement, Type type,
                                         JsonDeserializationContext context) {

        JsonElement data = jsonElement.getAsJsonObject().get("data");
        return new Gson().fromJson(data, FacebookAuthToken.class);
    }
}