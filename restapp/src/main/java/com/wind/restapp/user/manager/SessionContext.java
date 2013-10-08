package com.wind.restapp.user.manager;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.io.Serializable;


@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionContext implements Serializable {
    public static final String SESSION_KEY_USER = "_user";
    @Autowired
    private HttpSession session;

    public String getCurrentEmail() {
        return (String) session.getAttribute(SESSION_KEY_USER);
    }

    public void setCurrentEmail(String email) {
        session.setAttribute(SESSION_KEY_USER, email);
    }
}
