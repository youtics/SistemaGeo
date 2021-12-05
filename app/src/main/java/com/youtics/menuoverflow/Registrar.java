package com.youtics.menuoverflow;

import java.util.Date;

public class Registrar {

    private String userId;
    private String password;
    private String loginStatus;
    private Date registerDate;

    public Registrar() {
    }

    public Registrar(String userId, String password, String loginStatus, Date registerDate) {
        this.userId = userId;
        this.password = password;
        this.loginStatus = loginStatus;
        this.registerDate = registerDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }
}
