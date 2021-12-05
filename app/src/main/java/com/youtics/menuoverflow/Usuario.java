package com.youtics.menuoverflow;

import java.util.Date;

public class Usuario {

    private String userId;
    private String password;
    private String loginStatus;
    private Date registerDate;

    public Usuario(){}

    public Usuario(String userId, String password, String loginStatus, Date registerDate) {
        this.userId = userId;
        this.password = password;
        this.loginStatus = loginStatus;
        this.registerDate = registerDate;
    }

    public boolean isNull()
    {
        if(userId.equals("") && password.equals("") && loginStatus.equals("") && registerDate.equals(""))
        {
            return false;
        }else
        {
            return true;
        }
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", loginStatus='" + loginStatus + '\'' +
                ", registerDate=" + registerDate +
                '}';
    }
}
