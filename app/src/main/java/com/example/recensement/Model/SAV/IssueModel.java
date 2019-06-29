package com.example.recensement.Model.SAV;

public class IssueModel {

    private String imei;
    private String name;
    private String phone;
    private String issue_description;
    private String sav_center_id;
    private String product_receive_at;
    private String product_model_id;
    private String city_id;
    private String distribution_ticket;

    public IssueModel(String imei, String name, String phone, String issue_description, String sav_center_id, String product_receive_at, String product_model_id, String city_id, String distribution_ticket) {
        this.imei = imei;
        this.name = name;
        this.phone = phone;
        this.issue_description = issue_description;
        this.sav_center_id = sav_center_id;
        this.product_receive_at = product_receive_at;
        this.product_model_id = product_model_id;
        this.city_id = city_id;
        this.distribution_ticket = distribution_ticket;
    }

    public String getImei() {
        return imei;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getIssue_description() {
        return issue_description;
    }

    public String getSav_center_id() {
        return sav_center_id;
    }

    public String getProduct_receive_at() {
        return product_receive_at;
    }

    public String getProduct_model_id() {
        return product_model_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public String getDistribution_ticket() {
        return distribution_ticket;
    }
}
