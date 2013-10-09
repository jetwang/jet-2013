package com.wind.restapp.user.manager;

import jetwang.framework.util.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@Component
public class AuthenticationChecker {
    @Autowired
    private SessionContext sessionContext;

    public Object check(ProceedingJoinPoint call) throws Throwable {
        Class<?> clazz = call.getTarget().getClass();
        if (clazz.getAnnotation(NotNeedToLogin.class) == null) {
            if (StringUtils.isEmpty(sessionContext.getCurrentEmail())) {
                throw new WebApplicationException(Response.Status.UNAUTHORIZED);
            }
        }
        return call.proceed();
    }
}
