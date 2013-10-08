package com.wind.restapp.user.restws;

import com.sun.jersey.api.NotFoundException;
import com.wind.restapp.user.manager.SessionContext;
import com.wind.restapp.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("user")
@Service
public class UserRestService {
    @Autowired
    private SessionContext sessionContext;

    @GET
    @Path("/login/{email}/{password}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + Constants.CHARSET})
    public String login(@PathParam("email") String email, @PathParam("password") String password) {
        if (StringUtils.hasText(email) && email.startsWith("jetwang")) {
            sessionContext.setCurrentEmail(email);
            return email;
        } else {
            throw new NotFoundException();
        }
    }

    @GET
    @Path("/current")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + Constants.CHARSET})
    public String current() {
        return sessionContext.getCurrentEmail();
    }
}
