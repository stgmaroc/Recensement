package com.example.recensement.Model.Login;

public class User {
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
    private String  access_token;
    private UserModel user;
    //private List<UserModel> user;
    //private String  token_type;
    //private String  expires_in;
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

    public String getAccess_token() {
        return access_token;
    }

    public UserModel getUser() {
        return user;
    }
    /*public List<UserModel> getUser() {
        return user;
    }

    public String getToken_type() {
        return token_type;
    }

    public String getExpires_in() {
        return expires_in;
    }*/
}
