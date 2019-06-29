package com.example.recensement.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recensement.Adapters.ListViewClass;
import com.example.recensement.Adapters.MyRecycleViewAdapter;
import com.example.recensement.Model.Client.Respond;
import com.example.recensement.R;
import com.example.recensement.TokenStorage;
import com.example.recensement.service.Userclient;

import java.io.FileInputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.recensement.toolBar;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static String LOG_TAG = "RecyclerViewActivity";

    private DrawerLayout drawer;

    private toolBar toolbar;
    private Toolbar toolBar;


    private String Jwt;

    ArrayList<Respond> results = new ArrayList<Respond>();

    //Context contextApp = getApplicationContext();
    //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(contextApp);

    public static final String base_url_list = "http://192.168.1.90:8000/api/";

    public static String contains = "";

    Retrofit.Builder builder = new Retrofit.Builder().baseUrl(base_url_list).addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit=builder.build();
    Userclient client= retrofit.create(Userclient.class);

    Context context = ListActivity.this;

    private SwipeRefreshLayout swipeContainer;

    private TokenStorage tokenStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //MyRecycleViewAdapter.DataObjectHolder dataObjectHolder;
        tokenStorage = new TokenStorage("Token");
        //Recycler view declare
        setContentView(R.layout.activity_list);
        recyclerView= findViewById(R.id.client_list);
        getSupportActionBar().setElevation(0);
        //Activate toolbar
        //toolBar= findViewById(R.id.toolbar);
        //drawer= findViewById(R.id.drawer_layout);

        //toolbar =new toolBar(drawer,toolBar);
        //setSupportActionBar(toolBar);
        //toolbar.toolbarActivate(drawer,toolBar);


        ShowRecyclerView();

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.refresh_list);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //results.deleteAllItem();
                //ShowRecyclerView();
                if(swipeContainer.isRefreshing()){

                    ShowRecyclerView();
                    swipeContainer.setRefreshing(false);
                    //handler.postDelayed(this, 1000);
                }else{
                    //swipeContainer.setRefreshing(false);
                    //mainActivity.forceUpdate();
                    //setLayout();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((MyRecycleViewAdapter) mAdapter).setOnItemClickListener(
                new MyRecycleViewAdapter.MyClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {
                        Log.i(LOG_TAG, " Clicked on Item " + position);
                    }
                });
    }


//Activation toolbar
    /*public void toolbarActivate (){

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open_nav,R.string.close_nav);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }*/
//Action du boutton revenir en arriére



    public void ShowRecyclerView(){
        mAdapter= new MyRecycleViewAdapter(getDataSet());
        ListViewClass recyclerViewClass = new ListViewClass(mAdapter,recyclerView,context);


    }

    private ArrayList<Respond> getDataSet() {

        final ProgressBar progressBar=findViewById(R.id.pBarList);
        progressBar.setVisibility(View.VISIBLE);
        //Toast.makeText(ListActivity.this, " token : " + loadToken(), Toast.LENGTH_LONG).show();
        //Intent myIntent = getIntent();
        //Jwt = myIntent.getStringExtra("jwt");


        final TextView empty = findViewById(R.id.empty_list);
        //String jj="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9sb2NhbGhvc3Q6ODAwNSIsImF1ZCI6Imh0dHA6XC9cL2xvY2FsaG9zdDo4MDA1IiwiaWF0IjoxMzU2OTk5NTI0LCJuYmYiOjEzNTcwMDAwMDAsImRhdGEiOnsiYWNjZXNzIjoiYWRtaW4iLCJ1c2VyIjoiYWRtaW4ifX0.DFnVt-DnSzjseFxfmm-oRRWfbyMr3hbDRdtwv9w8fKA";
        //Token jwt = new Token(readFile());
        //String token = "bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC8xNTQuNzAuMjAwLjEwNjo5MDA5XC9hcGlcL2xvZ2luIiwiaWF0IjoxNTU5NTc2MDk4LCJuYmYiOjE1NTk1NzYwOTgsImp0aSI6InFYM3hST0xIek9VQjNUTHYiLCJzdWIiOjEyLCJwcnYiOiIyM2JkNWM4OTQ5ZjYwMGFkYjM5ZTcwMWM0MDA4NzJkYjdhNTk3NmY3In0.JxwP3GoIsap0rYENdXTmWxCfSish1250xGSkoV-RoL4";
        //String editor=prefs.getString("token","");
        String token = "bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC8xOTIuMTY4LjEuOTA6ODAwMFwvYXBpXC9sb2dpbiIsImlhdCI6MTU2MDQyMTE4MywibmJmIjoxNTYwNDIxMTgzLCJqdGkiOiJGZUhyNHFqaEtITnpRT29SIiwic3ViIjo5LCJwcnYiOiIyM2JkNWM4OTQ5ZjYwMGFkYjM5ZTcwMWM0MDA4NzJkYjdhNTk3NmY3In0.x0R1FRhLeYGxFeY31QAtuiCbhjyqKPUgJ6g2ACrUZ5s";

        //String token=loadToken();

        //Call<ArrayList<Respond>> call = client.getlist("bearer "+loadToken());
        Call<ArrayList<Respond>> call = client.getlist(token);

        call.enqueue(new Callback<ArrayList<Respond>>() {
            @Override
            public void onResponse(Call<ArrayList<Respond>> call, Response<ArrayList<Respond>> response) {
                empty.setVisibility(View.GONE);
                ArrayList<Respond> jsonResult2 = (ArrayList<Respond>) response.body();
                //Toast.makeText(ListActivity.this, " test : " + response.body().toString(), Toast.LENGTH_LONG).show();
                showData(jsonResult2);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ArrayList<Respond>> call, Throwable t) {
               Toast.makeText(ListActivity.this,"Vérifiez votre connexion",Toast.LENGTH_SHORT).show();
               //Log.d("test", t.getMessage());
               progressBar.setVisibility(View.GONE);
               empty.setVisibility(View.VISIBLE);
               //Log.i("result",t.getMessage());
            }
        });
        return results;
    }

    private void showData(ArrayList<Respond> jsonResult) {
        mAdapter= new MyRecycleViewAdapter(jsonResult);
        recyclerView.setAdapter(mAdapter);
    }



    public String readFile(){
        try {
            String filename = "Token";
            //String fileContents = "Hello world!";
            FileInputStream inputStream;
            inputStream = openFileInput(filename);
            int size = inputStream.available();
            byte[]  buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String text= new String(buffer);
            contains = new String(text);
            Log.d("file:",text);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return contains;
    }

    public String loadToken(){
        SharedPreferences sharedPref = getSharedPreferences("Mypref",MODE_PRIVATE);
        return sharedPref.getString("token","");
    }


}
