package com.example.recensement.service;

import com.example.recensement.Model.Client.Client;
import com.example.recensement.Model.Client.FetchClients;
import com.example.recensement.Model.Login.Login;
import com.example.recensement.Model.Pointer.PointageListModel;
import com.example.recensement.Model.Pointer.PointerModel;
import com.example.recensement.Model.Client.Respond;
import com.example.recensement.Model.Login.User;
import com.example.recensement.Model.Client.addClient;
import com.example.recensement.Model.Pointer.pointerRespond;
import com.example.recensement.Model.SAV.AddIssueRespond;
import com.example.recensement.Model.SAV.FetchSAVCity;
import com.example.recensement.Model.SAV.FetchSavCenter;
import com.example.recensement.Model.SAV.FetchSavProduct;
import com.example.recensement.Model.SAV.IssueModel;
import com.example.recensement.Model.Visite.VisitModel;
import com.example.recensement.Model.Visite.VisitRespond;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Userclient {

    @POST("login")
    Call<User> login(@Body Login login);

    @POST("create.php")
    Call<User> create(@Body addClient client);

    @GET("clients")
    Call<ArrayList<Respond>> getlist(@Header("Authorization") String jwt);

    @GET("clients")
    Call<ArrayList<FetchClients>> getlistClients(@Header("Authorization") String jwt);

    @GET("pointage/today")
    Call<ArrayList<PointageListModel>> getPointageList(@Header("Authorization") String jwt,@Header("Content-Type") String type);

    @POST("clients")
    Call<Respond> addToList(@Body addClient Client,@Header("Authorization") String jwt);

    @POST("pointage")
    Call<pointerRespond> addPointer(@Body PointerModel Pointer, @Header("Authorization") String jwt,@Header("Content-Type") String type);

    @POST("add-issue")
    Call<AddIssueRespond> addIssue(@Body IssueModel issue, @Header("Authorization") String jwt);

    @GET("products")
    Call<ArrayList<FetchSavProduct>> FetchModelsData(@Header("Authorization") String jwt);

    @GET("sav_center")
    Call<ArrayList<FetchSavCenter>> FetchCentersData(@Header("Authorization") String jwt);

    @GET("cities")
    Call<ArrayList<FetchSAVCity>> FetchCitiesData(@Header("Authorization") String jwt);

    @Multipart
    @POST("file/upload")
    Call<VisitRespond> Upload(@Part MultipartBody.Part file,@Part("clientid") int id, @Header("Authorization") String jwt);
}
