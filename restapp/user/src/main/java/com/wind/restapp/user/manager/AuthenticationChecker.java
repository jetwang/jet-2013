package com.wind.restapp.user.manager;

import com.wind.restapp.common.framework.util.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@Component
@Aspect
public class AuthenticationChecker {
    @Autowired
    private SessionContext sessionContext;

    @Around("@annotation(org.springframework.stereotype.Service)")
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
