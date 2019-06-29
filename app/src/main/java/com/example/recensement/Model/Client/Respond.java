package com.example.recensement.Model.Client;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Respond {
    /*private String status;
    private List<Client> data=null;


    public String getStatus() {
        return status;
    }

    public List<Client> getData() {
        return data;
    }

    public void setUserArray(List<Client> userArray) {
        this.data = userArray;
    }*/
    private String first_name;
    private String last_name;
    private String full_name;
    private String type;
    private String code;
    private String city;
    private String phone;
    private String magazin_name;
    private String city_id;
    private String localisation;
    private String address;
    //@SerializedName("")
    //private List<Client> data;
    /*private String[] first_name;
    private String[] last_name;
    private String[] full_name;
    private String[] type;
    private String[] code;
    private String[] city;
    private String[] phone;
    private String[] magazin_name;
    private String[] city_id;*/
    //private String address;
    //private String phone;
    //private String type;
    //private String city_id;
    //private String address;
    //private String  access_token;
   // private List<User> user=null;
    //private String  token_type;
    //private String  expires_in;


    public String getCity() {
        return city;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public String getPhone() {
        return phone;
    }

    public String getMagazin_name() {
        return magazin_name;
    }

    public String getCity_id() {
        return city_id;
    }

    public String getLocalisation() {
        return localisation;
    }

    public String getAddress() {
        return address;
    }
}
