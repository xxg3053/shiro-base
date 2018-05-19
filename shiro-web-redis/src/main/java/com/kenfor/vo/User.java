package com.kenfor.vo;

/**
 * @author kenfo
 * @version V1.0
 * @Package com.kenfo.vo
 * @Description: TODO
 * @date 2018/5/18 上午12:06
 */
public class User {

    private String username;
    private String password;
    private boolean rememberMe;

    public boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
