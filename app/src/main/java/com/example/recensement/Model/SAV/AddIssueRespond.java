package com.example.recensement.Model.SAV;

public class AddIssueRespond {

    private String res;
    private String msg;
    //private String[] phone;

    public String getRes() {
        return res;
    }

    public AddIssueRespond(String res, String msg) {
        this.res = res;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

   /* public String[] getPhone() {
        return phone;
    }*/
}
