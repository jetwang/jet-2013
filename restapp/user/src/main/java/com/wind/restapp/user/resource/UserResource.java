package com.wind.restapp.user.resource;

import com.sun.jersey.api.NotFoundException;
import com.sun.jersey.api.ParamException;
import com.wind.restapp.common.framework.ServiceException;
import com.wind.restapp.user.form.LoginForm;
import com.wind.restapp.user.manager.NotNeedToLogin;
import com.wind.restapp.user.manager.SessionContext;
import com.wind.restapp.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("user")
@Service
@NotNeedToLogin
public class UserResource {
    @Autowired
    private SessionContext sessionContext;

    @POST
    @Path("/login")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + Constants.CHARSET})
    public Response login(LoginForm loginForm) {
        String email = loginForm.getEmail();
        if (!StringUtils.hasText(loginForm.getPassword())) {
            throw new ParamException.FormParamException(new ServiceException("password is empty"), "password", "");

        }
        if (StringUtils.hasText(email) && email.startsWith("jetwang")) {
            sessionContext.setCurrentEmail(email);
            return Response.ok(email).build();
        } else {
            throw new NotFoundException("Can not find matched email and password");
        }
    }

    @GET
    @Path("/current")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + Constants.CHARSET})
    public String current() {
        String currentEmail = sessionContext.getCurrentEmail();
        if (StringUtils.hasText(currentEmail)) {
            return currentEmail;
        } else {
            throw new NotFoundException("Can't find current logged in email");
        }
    }
}
