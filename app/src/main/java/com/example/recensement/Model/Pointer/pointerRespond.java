package com.example.recensement.Model.Pointer;

import java.util.ArrayList;

public class pointerRespond {

    private String enter_exit_time;
    private String status;
    private String localisation;
    private String user_id;
    private String id;

    public String getStatus() {
        return status;
    }

    public String getLocalisation() {
        return localisation;
    }

    public String getEnter_exit_time() {
        return enter_exit_time;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getId() {
        return id;
    }

    public pointerRespond(String status, String localisation, String user_id, String id, String enter_exit_time) {
        this.enter_exit_time=enter_exit_time;
        this.status = status;
        this.localisation = localisation;
        this.user_id = user_id;
        this.id = id;
    }
}
