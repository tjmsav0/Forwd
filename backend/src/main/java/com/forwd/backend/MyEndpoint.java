/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.forwd.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/** An endpoint class we are exposing */
@Api(
  name = "testApi",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "backend.forwd.com",
    ownerName = "backend.forwd.com",
    packagePath=""
  )
)
public class MyEndpoint {

    @Inject

    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String person, HttpServletRequest request) {
        MyBean response = new MyBean();
        response.setData("Hi, " + request.getHeader("You-Are-A"));

        return response;
    }
}