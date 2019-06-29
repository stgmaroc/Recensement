package com.example.recensement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.recensement.Model.Client.Client;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestApiActivity extends AppCompatActivity {

    private ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_api);

        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);

        Call<List<Client>> call=apiInterface.getClientsInfo();
        call.enqueue(new Callback<List<Client>>() {
            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Client>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getClients(){
        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);

        Call<List<Client>> call=apiInterface.getClientsInfo();
        call.enqueue(new Callback<List<Client>>() {
            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Client>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_SHORT).show();
            }
        });



    }
}
