package com.example.recensement;

import com.example.recensement.Model.Client.Client;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("getClient.php")
    Call<List<Client>> getClientsInfo();


}
