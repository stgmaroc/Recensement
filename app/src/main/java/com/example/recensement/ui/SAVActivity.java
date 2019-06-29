package com.example.recensement.ui;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recensement.Adapters.SpinAdapter;
import com.example.recensement.Adapters.SpinAdapterSavCenter;
import com.example.recensement.Adapters.SpinAdapterSavCity;
import com.example.recensement.Model.SAV.AddIssueRespond;
import com.example.recensement.Model.SAV.FetchSAVCity;
import com.example.recensement.Model.SAV.FetchSavCenter;
import com.example.recensement.Model.SAV.FetchSavProduct;
import com.example.recensement.Model.SAV.IssueModel;
import com.example.recensement.R;
import com.example.recensement.service.Userclient;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SAVActivity extends AppCompatActivity {

    public static final String base_url = "http://154.70.200.106:9009/api/";

    Retrofit.Builder builder = new Retrofit.Builder().baseUrl(base_url).addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit=builder.build();
    Userclient dataInterface= retrofit.create(Userclient.class);

    private Spinner models;
    private Spinner centers;
    private Spinner cities;
    private Button ajouter;

    private EditText imei;
    private EditText name;
    private EditText phone;
    private EditText issue_description;
    private EditText city;
    private String sav_center_id;
    private EditText product_receive_at;
    private String product_model_id;
    private String city_id;
    private DatePickerDialog datePickerDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sav2);

        models= findViewById(R.id.models);
        fetchModelsName();

        imei = findViewById(R.id.imei);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);

        cities = findViewById(R.id.city);
        fetchCitiesName();

        issue_description = findViewById(R.id.issue_description);
        product_receive_at = findViewById(R.id.product_receive_at);
        product_receive_at.setRawInputType(InputType.TYPE_NULL);

        getSupportActionBar().setElevation(0);

        centers= findViewById(R.id.sav_centre);
        fetchCentersName();
        ajouter = findViewById(R.id.add_issue_btn);

        product_receive_at.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(SAVActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                product_receive_at.setText(year  + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                            }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (product_model_id.equals("999")) {
                    Toast.makeText(SAVActivity.this, "Veuillez Selectionner le model du produit",
                            Toast.LENGTH_SHORT).show();
                } else if(city_id=="999" ) {
                    Toast.makeText(SAVActivity.this, "Veuillez Selectionner la ville",
                            Toast.LENGTH_SHORT).show();
                } else if(sav_center_id=="999" ) {
                    Toast.makeText(SAVActivity.this, "Veuillez Selectionner le centre de destination",
                        Toast.LENGTH_SHORT).show();
                }else if(imei.getText().toString()==null) {
                        Toast.makeText(SAVActivity.this, "Veuillez remplir le IMEI",
                                Toast.LENGTH_SHORT).show();
                }else if(name.getText().toString()==null){
                    Toast.makeText(SAVActivity.this, "Veuillez remplir le nom du client",
                            Toast.LENGTH_SHORT).show();
                }else if(phone.getText().toString()==null){
                    Toast.makeText(SAVActivity.this, "Veuillez remplir le numero de téléphone du client",
                            Toast.LENGTH_SHORT).show();
                }else if(issue_description.getText()==null){
                    Toast.makeText(SAVActivity.this, "Veuillez remplir la description du probléme",
                            Toast.LENGTH_SHORT).show();
                }else if(product_receive_at.getText()==null){
                    Toast.makeText(SAVActivity.this, "Veuillez remplir la description du probléme",
                            Toast.LENGTH_SHORT).show();
                } else {
                    IssueModel issueModel = new IssueModel(imei.getText().toString(), name.getText().toString(), phone.getText().toString(), issue_description.getText().toString(), sav_center_id, product_receive_at.getText().toString(), product_model_id, city_id, "");
                    addIssue(issueModel);
                }
            }
        });
    }

    public String loadToken(){
        SharedPreferences sharedPref = getSharedPreferences("Mypref",MODE_PRIVATE);
        return sharedPref.getString("token","");
    }

    //Fetch Models

    private void fetchModelsName(){
        Call<ArrayList<FetchSavProduct>> call = dataInterface.FetchModelsData("bearer "+loadToken());
        final ProgressBar progressBar;
        progressBar=findViewById(R.id.pBar);
        progressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<ArrayList<FetchSavProduct>>() {
            @Override
            public void onResponse(Call<ArrayList<FetchSavProduct>> call, Response<ArrayList<FetchSavProduct>> response) {
                //Toast.makeText(AddClient.this,response.raw().code()+"test",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                ArrayList<FetchSavProduct> models= (ArrayList<FetchSavProduct>) response.body();
                showModelsInSpinner(models);
            }

            @Override
            public void onFailure(Call<ArrayList<FetchSavProduct>> call, Throwable t) {
                Toast.makeText(SAVActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void showModelsInSpinner(ArrayList<FetchSavProduct> result){
        //String array to store all the book names
        result.add(0,new FetchSavProduct("999","Choisissez un produits..."));
        final ArrayAdapter<FetchSavProduct> adapter =
                new SpinAdapter(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, result);
        //adapter.setAda
        models.setAdapter(adapter);
        models.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                FetchSavProduct user = adapter.getItem(position);
                // Here you can do the action you want to...
                //Toast.makeText(SAVActivity.this, "ID: " + user.getId() + "\nName: " + user.getName(),Toast.LENGTH_SHORT).show();
                product_model_id=user.getId()+"";
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });
    }

    // Fetsh Cities

    private void fetchCitiesName(){
        Call<ArrayList<FetchSAVCity>> call = dataInterface.FetchCitiesData("bearer "+loadToken());
        final ProgressBar progressBar;
        progressBar=findViewById(R.id.pBar);
        progressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<ArrayList<FetchSAVCity>>() {
            @Override
            public void onResponse(Call<ArrayList<FetchSAVCity>> call, Response<ArrayList<FetchSAVCity>> response) {
                //Toast.makeText(AddClient.this,response.raw().code()+"test",Toast.LENGTH_SHORT).show();
                ArrayList<FetchSAVCity> models= (ArrayList<FetchSAVCity>) response.body();
                showCitiesInSpinner(models);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ArrayList<FetchSAVCity>> call, Throwable t) {
                Toast.makeText(SAVActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                //progressBar.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void showCitiesInSpinner(ArrayList<FetchSAVCity> result){
        //String array to store all the book names
        result.add(0,new FetchSAVCity("999","Choisissez une Ville ..."));
        final ArrayAdapter<FetchSAVCity> adapter =
                new SpinAdapterSavCity(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, result);
        //adapter.setAda
        cities.setAdapter(adapter);
        cities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                FetchSAVCity user = adapter.getItem(position);
                // Here you can do the action you want to...
                //Toast.makeText(SAVActivity.this, "ID: " + user.getId() + "\nName: " + user.getName(),Toast.LENGTH_SHORT).show();
                city_id=user.getId()+"";
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });
    }

    // Fetch Centers

    private void fetchCentersName(){
        Call<ArrayList<FetchSavCenter>> call = dataInterface.FetchCentersData("bearer "+loadToken());
        final ProgressBar progressBar;
        progressBar=findViewById(R.id.pBar);
        progressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<ArrayList<FetchSavCenter>>() {
            @Override
            public void onResponse(Call<ArrayList<FetchSavCenter>> call, Response<ArrayList<FetchSavCenter>> response) {
                progressBar.setVisibility(View.GONE);
                //Toast.makeText(AddClient.this,response.raw().code()+"test",Toast.LENGTH_SHORT).show();
                ArrayList<FetchSavCenter> models= (ArrayList<FetchSavCenter>) response.body();
                showCentersInSpinner(models);

            }

            @Override
            public void onFailure(Call<ArrayList<FetchSavCenter>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(SAVActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                //progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void showCentersInSpinner(ArrayList<FetchSavCenter> result){
        //String array to store all the book names
        result.add(0,new FetchSavCenter("999","Choisissez un Centre SAV..."));
        final ArrayAdapter<FetchSavCenter> adapter =
                new SpinAdapterSavCenter(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, result);
        //adapter.setAda
        centers.setAdapter(adapter);
        centers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                FetchSavCenter user = adapter.getItem(position);
                // Here you can do the action you want to...
                //Toast.makeText(SAVActivity.this, "ID: " + user.getId() + "\nName: " + user.getName(),Toast.LENGTH_SHORT).show();
                sav_center_id=user.getId()+"";
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });
    }

    // Add issue

    private void addIssue(IssueModel isssue){

        Call<AddIssueRespond> call = dataInterface.addIssue(isssue,"bearer "+loadToken());

        final ProgressBar progressBar;
        progressBar=findViewById(R.id.pBar);
        progressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<AddIssueRespond>() {
            @Override
            public void onResponse(Call<AddIssueRespond> call, Response<AddIssueRespond> response) {
                //Toast.makeText(SAVActivity.this,response.raw().code()+"test",Toast.LENGTH_SHORT).show();
                if (response.raw().code()==200){
                    progressBar.setVisibility(View.GONE);
                    if (response.body().getRes()=="true") {
                        Toast.makeText(SAVActivity.this, "Demande SAV ajouter avec Succés", Toast.LENGTH_SHORT).show();
                    } else if(response.body().getRes()=="false") {
                        Toast.makeText(SAVActivity.this,response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }

                } else if (response.raw().code()==422){
                    Toast.makeText(SAVActivity.this," Veuillez remplir tous les champs !!! ",Toast.LENGTH_SHORT).show();
                }
                //AddIssueRespond isssue= (AddIssueRespond) response.body();
                //showCentersInSpinner(isssue);
            }
            @Override
            public void onFailure(Call<AddIssueRespond> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(SAVActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                //progressBar.setVisibility(View.GONE);
            }
        });
    }


}
