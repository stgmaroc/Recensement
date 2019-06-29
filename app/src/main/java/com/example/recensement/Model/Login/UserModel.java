package com.example.recensement.Model.Login;

import java.util.List;

public class UserModel {
    /*private String status;
    private String data;
    private String message;
    private String jwt;

    public String getJwt() {
        return jwt;
    }

    public void getJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getStatus() {
        return status;
    }

    public String getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }*/
    private String  id;
    private String  name;
    private String  username;
    private String  email;
    private String  access;
    private String  active;
    private String  city_id;
    private String  sav_center_id;
    private String  created_at;
    private String  updated_at;

    private List<UserModel> user;
    private String  token_type;
    private String  expires_in;
    /*"id
            "name": "admincom",
            "username": "admincom",
            "email": "admincom@gmail.com",
            "access": "admin_commercial",
            "active": 1,
            "city_id": 314,
            "sav_center_id": null,
            "created_at": "2019-06-03 09:39:22",
            "updated_at": "2019-06-03 09:39:22"*/

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getAccess() {
        return access;
    }

    public String getActive() {
        return active;
    }

    public String getCity_id() {
        return city_id;
    }

    public String getSav_center_id() {
        return sav_center_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public List<UserModel> getUser() {
        return user;
    }

    public String getToken_type() {
        return token_type;
    }

    public String getExpires_in() {
        return expires_in;
    }
}
