package com.example.recensement.Model.Client;

public class addClient {

    private String first_name;
    private String last_name;
    private String phone;
    private String type;
    private String magazin_name;
    private String city_id;
    private String address;
    private String localisation;

    public addClient(String first_name, String last_name, String phone, String type, String city_id, String address,String magazin,String localisation) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
        this.type = type;
        this.city_id = city_id;
        this.address = address;
        this.magazin_name = magazin;
        this.localisation=localisation;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getPhone() {
        return phone;
    }

    public String getType() {
        return type;
    }

    public String getCity_id() {
        return city_id;
    }

    public String getAddress() {
        return address;
    }

    public String getMagazin_name() {
        return magazin_name;
    }

    public String getLocalisation() {
        return localisation;
    }

    /*private String fname;
    private String lname;
    private String localisation;
    private String jwt;

    public addClient(String fname, String lname, String localisation, String jwt) {
        this.fname = fname;
        this.lname = lname;
        this.localisation = localisation;
        this.jwt = jwt;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getLocalisation() {
        return localisation;
    }

    public String getJwt() {
        return jwt;
    }*/
}

