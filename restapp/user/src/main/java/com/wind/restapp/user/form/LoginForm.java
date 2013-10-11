package com.wind.restapp.user.form;


import com.wind.restapp.util.Constants;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "login-form", namespace = Constants.NS + "/login")
public class LoginForm {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
