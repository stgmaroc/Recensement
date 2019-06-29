package com.example.recensement.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.recensement.Model.Login.Login;
import com.example.recensement.Model.Login.User;
import com.example.recensement.R;
import com.example.recensement.TokenStorage;
import com.example.recensement.service.Userclient;

import java.io.FileOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText name;
    private EditText password;
    private Button btn;

    //Context context = getApplicationContext();

    private TokenStorage tokenStorage;

    //public static final String base_url = "http://154.70.200.106:8005/api/login.php/";
    public static final String base_url = "http://192.168.1.90:8000/api/";

    public static final String filename = "token";

    Retrofit.Builder builder = new Retrofit.Builder().baseUrl(base_url).addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit=builder.build();

    Userclient userclient= retrofit.create(Userclient.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //toolbarActivate();
        tokenStorage = new TokenStorage("Token");
        name = (EditText)findViewById(R.id.loginName);
        password = (EditText)findViewById(R.id.loginPassword);
        btn = (Button)findViewById(R.id.restTest);
        btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                //close Keyboard
                View view = LoginActivity.this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                //create object Login
                Login login= new Login(name.getText().toString(),password.getText().toString());
                //Call inteface
                Call<User> call = userclient.login(login);
                //File file = new File(this, filename);

                final ProgressBar progressBar;

                progressBar=findViewById(R.id.pBar);
                progressBar.setVisibility(View.VISIBLE);

                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        int code =response.raw().code();
                        if(code == 200){

                            Toast.makeText(LoginActivity.this,"Succés",Toast.LENGTH_SHORT).show();


                           String Jwt = response.body().getAccess_token();
                           progressBar.setVisibility(View.GONE);
                            //savefile("Token",Jwt);

                            saveToken(Jwt,response.body().getUser().getName());

                            Intent intent = new Intent(LoginActivity.this, Pointer.class);
                            //intent.putExtra("jwt",Jwt);
                            startActivity(intent);


                        } else if(code == 401) {
                            Toast.makeText(LoginActivity.this,"Login/Mot de passe Erroné",Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }


                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(LoginActivity.this,"Vérifiez votre connexion",Toast.LENGTH_SHORT).show();
                        //Log.d("test : ", t.toString());
                        progressBar.setVisibility(View.GONE);
                    }
                });

            }
        });

    }

    public void request(View view){

        Login login= new Login(name.getText().toString(),password.getText().toString());
        Call<User> call = userclient.login(login);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                //Toast.makeText(LoginActivity.this,response.message(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"Login Nok",Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void savefile(String filename,String TokenFilecontents){
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(TokenFilecontents.getBytes());
            outputStream.close();
            //readFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onBackPressed() {
        finish();

    }

    public void saveToken(String token,String name){
        SharedPreferences sharedPref = getSharedPreferences("Mypref",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("token",token);
        editor.putString("name",name);
        editor.apply();
        //Toast.makeText(LoginActivity.this,"Token"+sharedPref.getString("token",""),Toast.LENGTH_LONG).show();
    }
}
