package com.example.recensement.Model.Pointer;

public class PointerModel {

    private String status;
    private String localisation;

    public PointerModel(String location, String status) {
        this.localisation = location;
        this.status = status;
    }

    public String getLocation() {
        return localisation;
    }

    public String getStatus() {
        return status;
    }
}
