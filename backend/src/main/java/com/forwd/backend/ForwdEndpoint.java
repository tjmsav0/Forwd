package com.forwd.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.repackaged.com.google.gson.JsonParseException;

import javax.inject.Named;

import java.io.IOException;
import java.util.logging.Logger;

@Api(
  name = "forwdApi",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "backend.forwd.com",
    ownerName = "backend.forwd.com",
    packagePath=""
  )
)

public class ForwdEndpoint {

    private static final Logger log = Logger.getLogger(ForwdEndpoint.class.getName());

    @ApiMethod(name = "getUserData", path = "get_user_data")
    public Bean getUserData(@Named("token") String token) {

        Bean response = new Bean();
        FacebookAuthToken authToken;

        authToken = FacebookAuthToken.getAuthToken(token);

        if (authToken == null) {

        }

        response.setData("This happened " + authToken.getAppId());
        return response;
    }
}