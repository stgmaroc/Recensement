package com.example.recensement.Model.Visite;

import okhttp3.MultipartBody;

public class VisitModel {

    private MultipartBody.Part file;
    private int clientid;

    public VisitModel(MultipartBody.Part file, int clientid) {
        this.file = file;
        this.clientid = clientid;
    }

    public MultipartBody.Part getFile() {
        return file;
    }

    public int getClientid() {
        return clientid;
    }
}
