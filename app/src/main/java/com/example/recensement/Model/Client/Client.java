package com.example.recensement.Model.Client;

import java.util.List;

public class Client{

    private String first_name;
    private String last_name;
    //private String phone;
    //private String type;
    //private String city_id;
    //private String address;

    public Client(String first_name, String last_name, String phone, String type, String city_id, String address) {
        this.first_name = first_name;
        this.last_name = last_name;
       /* this.phone = phone;
        this.type = type;
        this.city_id = city_id;
        this.address = address;*/
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

   /* public String getPhone() {
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
    }*/

    /* private String id;
    private String fname;
    private String lname;
    private String localisation;

    public Client(String id, String fname, String lname, String localisation) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.localisation = localisation;
    }
    public Client() { }

    public String getId() { return id;}

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getLocalisation() { return localisation;  }*/

}
