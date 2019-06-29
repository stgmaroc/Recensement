package com.example.recensement.ui;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.recensement.Adapters.SpinAdapter;
import com.example.recensement.Adapters.SpinAdapterClient;
import com.example.recensement.Model.Client.Client;
import com.example.recensement.Model.Client.FetchClients;
import com.example.recensement.Model.Client.Respond;
import com.example.recensement.Model.Login.Login;
import com.example.recensement.Model.SAV.FetchSavProduct;
import com.example.recensement.Model.Visite.VisitModel;
import com.example.recensement.Model.Visite.VisitRespond;
import com.example.recensement.R;
import com.example.recensement.service.Userclient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;

public class Visites extends AppCompatActivity {
    ImageView imageView;
    Spinner clients;
    Button btnUpload;
    Bitmap bitmap;

    private int client_id;

    public static final String base_url = "http://154.70.200.106:9009/api/";

    private static final String[] PROJECTION = new String[]{MediaStore.MediaColumns.DATA};
    private static final ContentValues CONTENT_VALUES = new ContentValues(1);
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    Retrofit.Builder builder = new Retrofit.Builder().baseUrl(base_url).addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit=builder.build();
    Userclient userclient= retrofit.create(Userclient.class);

    File photoFile = null;
    static final int CAPTURE_IMAGE_REQUEST = 1;


    String mCurrentPhotoPath;
    private static final String IMAGE_DIRECTORY_NAME = "VLEMONN";
    final Userclient client= retrofit.create(Userclient.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visites);
        getSupportActionBar().setElevation(0);

        final ProgressBar progressBar=findViewById(R.id.pBar);

        Button btnCamera = findViewById(R.id.pictures);
        imageView = findViewById(R.id.image);
        clients = findViewById(R.id.clients);
        fetchClientsName();

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/form-data"),photoFile);
                MultipartBody.Part file = MultipartBody.Part.createFormData("photo",photoFile.getName(),requestBody);
                //int client_id=clients.getId();
                //Toast.makeText(Visites.this,client_id+"test",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.VISIBLE);

                VisitModel visitModel= new VisitModel(file,client_id);

                Call<VisitRespond> call = client.Upload(visitModel.getFile(),client_id,"bearer "+loadToken());
                call.enqueue(new Callback<VisitRespond>() {
                    @Override
                    public void onResponse(Call<VisitRespond> call, Response<VisitRespond> response) {
                        progressBar.setVisibility(View.GONE);
                        //Toast.makeText(Visites.this,response.raw().code()+"test",Toast.LENGTH_SHORT).show();
                        if (response.raw().code() == 200){
                            Toast.makeText(Visites.this,"Visite enregistrée",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(Visites.this,"Une Erreur est survenu Merci de rééssayer",Toast.LENGTH_SHORT).show();
                        }
                        //Toast.makeText(AddClient.this,"Error "+response.raw().code(),Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<VisitRespond> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(Visites.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                        //progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    captureImage();
                } else
                {
                    captureImage2();
                }
            }
        });

    }

    /* Capture Image function for 4.4.4 and lower. Not tested for Android Version 3 and 2 */
    private void captureImage2() {

        try {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            photoFile = createImageFile4();
            if(photoFile!=null)
            {
                displayMessage(getBaseContext(),photoFile.getAbsolutePath());
                Log.i("Mayank",photoFile.getAbsolutePath());
                Uri photoURI  = Uri.fromFile(photoFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, CAPTURE_IMAGE_REQUEST);
            }
        }
        catch (Exception e)
        {
            displayMessage(getBaseContext(),"Camera is not available."+e.toString());
        }
    }

    private void captureImage() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }
        else
        {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                try {

                    photoFile = createImageFile();
                    //displayMessage(getBaseContext(),photoFile.getAbsolutePath());
                    Log.i("Mayank",photoFile.getAbsolutePath());

                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(this,
                                "com.vlemonn.blog.captureimage.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, CAPTURE_IMAGE_REQUEST);
                    }
                } catch (Exception ex) {
                    // Error occurred while creating the File
                    displayMessage(getBaseContext(),ex.getMessage().toString());
                }


            }else
            {
                displayMessage(getBaseContext(),"Nullll");
            }
        }



    }

    private File createImageFile4() {
        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                displayMessage(getBaseContext(),"Unable to create directory.");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");

        return mediaFile;

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void displayMessage(Context context, String message) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Bundle extras = data.getExtras();
        //Bitmap imageBitmap = (Bitmap) extras.get("data");
        //imageView.setImageBitmap(imageBitmap);

        if (requestCode == CAPTURE_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);


        }
        else
        {
            displayMessage(getBaseContext(),"Request cancelled or something went wrong.");
        }
    }

    public String loadToken(){
        SharedPreferences sharedPref = getSharedPreferences("Mypref",MODE_PRIVATE);
        return sharedPref.getString("token","");
    }

    private void fetchClientsName(){
        Call<ArrayList<FetchClients>> call = client.getlistClients("bearer "+loadToken());
        final ProgressBar progressBar;
        progressBar=findViewById(R.id.pBar);
        //progressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<ArrayList<FetchClients>>() {
            @Override
            public void onResponse(Call<ArrayList<FetchClients>> call, Response<ArrayList<FetchClients>> response) {
                //Toast.makeText(AddClient.this,response.raw().code()+"test",Toast.LENGTH_SHORT).show();
                ArrayList<FetchClients> models= (ArrayList<FetchClients>) response.body();
                showClientsInSpinner(models);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ArrayList<FetchClients>> call, Throwable t) {
                Toast.makeText(Visites.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void showClientsInSpinner(ArrayList<FetchClients> result){
        //String array to store all the book names
        result.add(0,new FetchClients(999,"Choisissez un produits...",""));
        final ArrayAdapter<FetchClients> adapter =
                new SpinAdapterClient(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, result);
        //adapter.setAda
        clients.setAdapter(adapter);
        clients.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                FetchClients client = adapter.getItem(position);
                // Here you can do the action you want to...
                //Toast.makeText(Visites.this, "ID: " + client.getId() + "\nName: " + client.getFirst_name(),Toast.LENGTH_SHORT).show();
                client_id=client.getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });
    }




}