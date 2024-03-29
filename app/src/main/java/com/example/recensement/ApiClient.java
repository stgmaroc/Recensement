package com.example.recensement;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String Base_Url = "";
    public static Retrofit retrofit;

    public static Retrofit getRetrofit(){
        if(retrofit==null){
            retrofit=new Retrofit.Builder().baseUrl(Base_Url).
                    addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit;
    }
}
