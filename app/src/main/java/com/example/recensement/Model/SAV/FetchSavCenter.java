package com.example.recensement.Model.SAV;

import android.support.annotation.VisibleForTesting;

public class FetchSavCenter {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public FetchSavCenter(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
