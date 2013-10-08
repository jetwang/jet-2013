package com.wind.restapp.user.filter;


import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import org.springframework.stereotype.Component;

import javax.ws.rs.ext.Provider;

@Provider
@Component
public class AuthenticationFilter implements ContainerRequestFilter {
    @Override
    public ContainerRequest filter(ContainerRequest request) {
        request.
        return null;
    }
}
