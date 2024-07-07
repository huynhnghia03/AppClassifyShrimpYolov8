package com.example.myyolov8app.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

public class Users implements Serializable {
    private String email;
    private  Boolean auth;
    private String password;
    private String username;
    private String avatar;
    private Boolean admin;
    private String date;
    private Boolean detected;
    private String access_token;

    public Users(Boolean auth) {
        this.auth = auth;
    }

    public Users(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Users(String email, String username, String avatar) {
        this.email = email;
        this.username = username;
        this.avatar = avatar;
    }


    public Users(String email,Boolean auth,String username, String avatar,String date, String access_token) {
        this.email = email;
        this.auth=auth;
        this.username = username;
        this.avatar = avatar;
        this.date = date;
        this.access_token = access_token;
    }

    public Users(String email, String username, String avatar, Boolean admin,String date, Boolean detected) {
        this.email = email;
        this.username = username;
        this.avatar = avatar;
        this.admin = admin;
        this.date = date;
        this.detected = detected;

    }

    public Users(String email, Boolean auth, String username, String avatar, Boolean admin, String date) {
        this.email = email;
        this.auth = auth;
        this.username = username;
        this.avatar = avatar;
        this.admin = admin;
        this.date = date;
    }

    public Users(String email, String username, String avatar, Boolean admin, String date) {
        this.email = email;
        this.username = username;
        this.avatar = avatar;
        this.admin = admin;
        this.date = date;
    }

    public Users(String email, Boolean auth, String password, String username, String avatar, Boolean admin,String date, String access_token) {
        this.email = email;
        this.auth = auth;
        this.password = password;
        this.username = username;
        this.avatar = avatar;
        this.admin = admin;
        this.date = date;
        this.access_token = access_token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAuth() {
        return auth;
    }

    public void setAuth(Boolean auth) {
        this.auth = auth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getDetected() {
        return detected;
    }

    public void setDetected(Boolean detected) {
        this.detected = detected;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    @Override
    public String toString() {
        return "Users{" +
                "email='" + email + '\'' +
                ", auth=" + auth +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", admin=" + admin +
                ", date=" + date +
                ", detected=" + detected +
                ", access_token='" + access_token + '\'' +
                '}';
    }
}
