package com.example.recensement.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.recensement.GPSTracker;
import com.example.recensement.Model.Client.Respond;
import com.example.recensement.Model.Client.addClient;
import com.example.recensement.R;
import com.example.recensement.service.Userclient;
import com.example.recensement.toolBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddClient extends AppCompatActivity {

    private String Jwt;
    public static final String base_url = "http://154.70.200.106:9009/api/";

    private EditText fname;
    private EditText lname;
    private EditText store;
    private AutoCompleteTextView city;
    private Spinner type;
    private EditText adresse;
    private EditText phone;
    private String location;
    private String jwt;
    private Button btn;

    private toolBar toolbar;
    private Toolbar toolBar;
    private DrawerLayout drawer;

    Retrofit.Builder builder = new Retrofit.Builder().baseUrl(base_url).addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit=builder.build();
    Userclient userclient= retrofit.create(Userclient.class);

    GPSTracker gps;
    int status = 0;

    private static final String[] cities = { "Aïn Harrouda", "Aïn Harrouda", "Ben Yakhlef", "Bouskoura", "Casablanca", "Médiouna", "Mohammédia", "Tit Mellil", "Ben Yakhlef", "Bejaâd", "Ben Ahmed", "Benslimane",
            "Berrechid", "Boujniba", "Boulanouare", "Bouznika", "Deroua", "El Borouj", "El Gara", "Guisser", "Hattane", "Khouribga", "Loulad", "Oued Zem", "Oulad Abbou", "Oulad HRiz Sahel",
            "Oulad Mrah", "Oulad Saïd", "Oulad Sidi Ben Daoud", "Ras El Aïn", "Settat", "Sidi Rahhal Chataï", "Soualem", "Azemmour", "Bir Jdid", "Bouguedra", "Echemmaia", "El Jadida",
            "Hrara", "Ighoud", "Jamâat Shaim", "Jorf Lasfar", "Khemis Zemamra", "Laaounate", "Moulay Abdallah", "Oualidia", "Oulad Amrane", "Oulad Frej", "Oulad Ghadbane", "Safi",
            "Sebt El Maârif", "Sebt Gzoula", "Sidi Ahmed", "Sidi Ali Ban Hamdouche", "Sidi Bennour", "Sidi Bouzid", "Sidi Smaïl", "Youssoufia", "Fès", "Aïn Cheggag", "Bhalil", "Boulemane",
            "El Menzel", "Guigou", "Imouzzer Kandar", "Imouzzer Marmoucha", "Missour", "Moulay Yaâcoub", "Ouled Tayeb", "Outat El Haj", "Ribate El Kheir", "Séfrou", "Skhinate", "Tafajight",
            "Arbaoua", "Aïn Dorij", "Dar Gueddari", "Had Kourt", "Jorf El Melha", "Kénitra", "Khenichet", "Lalla Mimouna", "Mechra Bel Ksiri", "Mehdia", "Moulay Bousselham",
            "Sidi Allal Tazi", "Sidi Kacem", "Sidi Slimane", "Sidi Taibi", "Sidi Yahya El Gharb", "Souk El Arbaa", "Akka", "Assa", "Bouizakarne", "El Ouatia", "Es-Semara", "Fam El Hisn",
            "Foum Zguid", "Guelmim", "Taghjijt", "Tan-Tan", "Tata", "Zag", "Marrakech", "Ait Daoud", "Amizmiz", "Assahrij", "Aït Ourir", "Ben Guerir", "Chichaoua", "El Hanchane",
            "El Kelaâ des Sraghna", "Essaouira", "Fraïta", "Ghmate", "Ighounane", "Imintanoute", "Kattara", "Lalla Takerkoust", "Loudaya", "Lâattaouia", "Moulay Brahim", "Mzouda",
            "Ounagha", "Sid L Mokhtar", "Sid Zouin", "Sidi Abdallah Ghiat", "Sidi Bou Othmane", "Sidi Rahhal", "Skhour Rehamna", "Smimou", "Tafetachte", "Tahannaout", "Talmest",
            "Tamallalt", "Tamanar", "Tamansourt", "Tameslouht", "Tanalt", "Zeubelemok", "Meknès‎", "Khénifra", "Agourai", "Ain Taoujdate", "MyAliCherif", "Rissani", "Amalou Ighriben",
            "Aoufous", "Arfoud", "Azrou", "Aïn Jemaa", "Aïn Karma", "Aïn Leuh", "Aït Boubidmane", "Aït Ishaq", "Boudnib", "Boufakrane", "Boumia", "El Hajeb", "Elkbab", "Er-Rich",
            "Errachidia", "Gardmit", "Goulmima", "Gourrama", "Had Bouhssoussen", "Haj Kaddour", "Ifrane", "Itzer", "Jorf", "Kehf Nsour", "Kerrouchen", "M haya", "M rirt", "Midelt",
            "Moulay Ali Cherif", "Moulay Bouazza", "Moulay Idriss Zerhoun", "Moussaoua", "N Zalat Bni Amar", "Ouaoumana", "Oued Ifrane", "Sabaa Aiyoun", "Sebt Jahjouh", "Sidi Addi",
            "Tichoute", "Tighassaline", "Tighza", "Timahdite", "Tinejdad", "Tizguite", "Toulal", "Tounfite", "Zaouia d Ifrane", "Zaïda", "Ahfir", "Aklim", "Al Aroui", "Aïn Bni Mathar",
            "Aïn Erreggada", "Ben Taïeb", "Berkane", "Bni Ansar", "Bni Chiker", "Bni Drar", "Bni Tadjite", "Bouanane", "Bouarfa", "Bouhdila", "Dar El Kebdani", "Debdou", "Douar Kannine",
            "Driouch", "El Aïoun Sidi Mellouk", "Farkhana", "Figuig", "Ihddaden", "Jaâdar", "Jerada", "Kariat Arekmane", "Kassita", "Kerouna", "Laâtamna", "Madagh", "Midar", "Nador",
            "Naima", "Oued Heimer", "Oujda", "Ras El Ma", "Saïdia", "Selouane", "Sidi Boubker", "Sidi Slimane Echcharaa", "Talsint", "Taourirt", "Tendrara", "Tiztoutine", "Touima",
            "Touissit", "Zaïo", "Zeghanghane", "Rabat", "Salé", "Ain El Aouda", "Harhoura", "Khémisset", "Oulmès", "Rommani", "Sidi Allal El Bahraoui", "Sidi Bouknadel", "Skhirat",
            "Tamesna", "Témara", "Tiddas", "Tiflet", "Touarga", "Agadir", "Agdz", "Agni Izimmer", "Aït Melloul", "Alnif", "Anzi", "Aoulouz", "Aourir", "Arazane", "Aït Baha", "Aït Iaâza",
            "Aït Yalla", "Ben Sergao", "Biougra", "Boumalne-Dadès", "Dcheira El Jihadia", "Drargua", "El Guerdane", "Harte Lyamine", "Ida Ougnidif", "Ifri", "Igdamen", "Ighil n Oumgoun",
            "Imassine", "Inezgane", "Irherm", "Kelaat-M Gouna", "Lakhsas", "Lakhsass", "Lqliâa", "M semrir", "Massa (Maroc)", "Megousse", "Ouarzazate", "Oulad Berhil", "Oulad Teïma",
            "Sarghine", "Sidi Ifni", "Skoura", "Tabounte", "Tafraout", "Taghzout", "Tagzen", "Taliouine", "Tamegroute", "Tamraght", "Tanoumrite Nkob Zagora", "Taourirt ait zaghar",
            "Taroudant", "Temsia", "Tifnit", "Tisgdal", "Tiznit", "Toundoute", "Zagora", "Afourar", "Aghbala", "Azilal", "Aït Majden", "Beni Ayat", "Béni Mellal", "Bin elouidane",
            "Bradia", "Bzou", "Dar Oulad Zidouh", "Demnate", "Dra a", "El Ksiba", "Foum Jamaa", "Fquih Ben Salah", "Kasba Tadla", "Ouaouizeght", "Oulad Ayad", "Oulad M Barek", "Oulad Yaich",
            "Sidi Jaber", "Souk Sebt Oulad Nemma", "Zaouïat Cheikh", "Tanger‎", "Tétouan‎", "Akchour", "Assilah", "Bab Berred", "Bab Taza", "Brikcha", "Chefchaouen", "Dar Bni Karrich",
            "Dar Chaoui", "Fnideq", "Gueznaia", "Jebha", "Karia", "Khémis Sahel", "Ksar El Kébir", "Larache", "M diq", "Martil", "Moqrisset", "Oued Laou", "Oued Rmel", "Ouezzane",
            "Point Cires", "Sidi Lyamani", "Sidi Mohamed ben Abdallah el-Raisuni", "Zinat", "Ajdir‎", "Aknoul‎", "Al Hoceïma‎", "Aït Hichem‎", "Bni Bouayach‎", "Bni Hadifa‎", "Ghafsai‎",
            "Guercif‎", "Imzouren‎", "Inahnahen‎", "Issaguen (Ketama)‎", "Karia (El Jadida)‎", "Karia Ba Mohamed‎", "Oued Amlil‎", "Oulad Zbair‎", "Tahla‎", "Tala Tazegwaght‎", "Tamassint‎",
            "Taounate‎", "Targuist‎", "Taza‎", "Taïnaste‎", "Thar Es-Souk‎", "Tissa‎", "Tizi Ouasli‎", "Laayoune‎", "El Marsa‎", "Tarfaya‎", "Boujdour‎", "Awsard", "Oued-Eddahab", "Stehat",
            "Aït Attab", "Beni Mellal"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        final ProgressBar progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        getSupportActionBar().setElevation(0);

        final Intent intent = getIntent();

        fname = (EditText)findViewById(R.id.first_name);
        lname = (EditText)findViewById(R.id.last_name);
        store = (EditText)findViewById(R.id.store);
        adresse = (EditText)findViewById(R.id.adresse);

        city = (AutoCompleteTextView) findViewById(R.id.city);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cities);
        city.setAdapter(adapter);

        type = (Spinner)findViewById(R.id.type);
        phone = (EditText) findViewById(R.id.phone);

        gps = new GPSTracker(this);
        final double lat=gps.getLatitude();
        final double longi=gps.getLongitude();
        location =""+ lat + ","+longi;

        btn = (Button)findViewById(R.id.add_client_btn);
        btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                View view = AddClient.this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                Intent myIntent = getIntent();
                Jwt = myIntent.getStringExtra("jwt");
                //Token jwt = new Token(Jwt);

                /*find Item position in cities*/
                int index = -1;
                for (int i = 0; i < cities.length; i++) {
                    if (cities[i].equals("" + city.getText())) {
                        index = i + 1;
                        break;
                    }
                }

                if (ActivityCompat.checkSelfPermission(AddClient.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                {
                    //demander la permission
                    ActivityCompat.requestPermissions(AddClient.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    //Log.e("testing", "Permission is revoked");
                }
                else if (ActivityCompat.checkSelfPermission(AddClient.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    //call();
                    ActivityCompat.requestPermissions(AddClient.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                    //Log.e("testing", "Permission is revoked");
                }else {

                    //int position = Arrays.asList(cities).indexOf(city.getText());
                    //Toast.makeText(AddClient.this,index+" ",Toast.LENGTH_SHORT).show();
                    if (longi == 0.00 && lat == 0.00) {
                        Toast.makeText(AddClient.this, "Merci d'activer le GPS", Toast.LENGTH_SHORT).show();

                    } else {
                        addClient client = new addClient(fname.getText().toString(), lname.getText().toString(), phone.getText().toString(), type.getSelectedItem().toString(), "" + index, adresse.getText().toString(), store.getText().toString(), location);
                        String token = "bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC8xOTIuMTY4LjEuOTA6ODAwMFwvYXBpXC9sb2dpbiIsImlhdCI6MTU2MDQyMTE4MywibmJmIjoxNTYwNDIxMTgzLCJqdGkiOiJGZUhyNHFqaEtITnpRT29SIiwic3ViIjo5LCJwcnYiOiIyM2JkNWM4OTQ5ZjYwMGFkYjM5ZTcwMWM0MDA4NzJkYjdhNTk3NmY3In0.x0R1FRhLeYGxFeY31QAtuiCbhjyqKPUgJ6g2ACrUZ5s";

                        Call<Respond> call = userclient.addToList(client, token);


                        progressBar.setVisibility(View.VISIBLE);
                        final GradientDrawable gd = new GradientDrawable();


                        //progressBar=findViewById(R.id.pBar);
                        //progressBar.setVisibility(View.VISIBLE);

                        call.enqueue(new Callback<Respond>() {
                            @Override
                            public void onResponse(Call<Respond> call, Response<Respond> response) {
                                //Toast.makeText(AddClient.this,response.raw().code()+"test",Toast.LENGTH_SHORT).show();
                                //Toast.makeText(AddClient.this,"Error "+response.raw().code(),Toast.LENGTH_SHORT).show();
                                int code = response.raw().code();


                                if (code == 201) {
                                    Toast.makeText(AddClient.this, "Ajouter avec succes", Toast.LENGTH_SHORT).show();
                                    //this.recreate();
                                    finish();
                                    startActivity(getIntent());

                                    //String Jwt = response.body().getJwt();
                                    progressBar.setVisibility(View.GONE);
                                    //Intent intent = new Intent(AddClient.this, ListActivity.class);
                                    //intent.putExtra("jwt",Jwt);
                                    //startActivity(intent);
                                } else if (code == 401) {
                                    Toast.makeText(AddClient.this, "Erreur de conexion", Toast.LENGTH_SHORT).show();
                                } else if (code == 400) {
                                    Toast.makeText(AddClient.this, "Ajout échoué", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                } else if (code == 422) {
                                    BufferedReader reader = null;
                                    JSONObject jsonObject = null;
                                    StringBuilder sb = new StringBuilder();
                                    progressBar.setVisibility(View.GONE);
                                    try {
                                        reader = new BufferedReader(new InputStreamReader(response.errorBody().byteStream(), "UTF-8"));

                                        String line;
                                        try {
                                            while ((line = reader.readLine()) != null) {
                                                sb.append(line);
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                    String finallyError = sb.toString();

                                    try {
                                        jsonObject = new JSONObject(finallyError);
                                    } catch (JSONException err) {
                                        //Log.d("Error", err.toString());
                                    }

                                    if (jsonObject.length() != 0) {
                                        gd.setColor(Color.parseColor("#82EBFE"));


                                        if (jsonObject.has("phone")) {
                                            try {
                                                Toast.makeText(AddClient.this, "" + jsonObject.get("phone").toString(), Toast.LENGTH_SHORT).show();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            // Set the gradient drawable background to transparent
                                            gd.setColor(Color.parseColor("#00ffffff"));
                                            // Set a border for the gradient drawable
                                            gd.setStroke(4, Color.RED);
                                            phone.setBackground(gd);
                                        } else {
                                            final GradientDrawable gddd = new GradientDrawable();
                                            gddd.setColor(Color.parseColor("#82EBFE"));
                                            // Set a border for the gradient drawable
                                            phone.setBackground(gddd);
                                            if (jsonObject.has("first_name")) {
                                                Toast.makeText(AddClient.this, "Veuillez renseigner tous les champs en rouge", Toast.LENGTH_SHORT).show();
                                                //GradientDrawable gd = new GradientDrawable();
                                                // Set the gradient drawable background to transparent
                                                gd.setColor(Color.parseColor("#00ffffff"));
                                                // Set a border for the gradient drawable
                                                gd.setStroke(4, Color.RED);
                                                fname.setBackground(gd);

                                            } else {
                                                final GradientDrawable gdd = new GradientDrawable();
                                                gdd.setColor(Color.parseColor("#82EBFE"));
                                                // Set a border for the gradient drawable
                                                //gd.setStroke(4, Color.RED);
                                                fname.setBackground(gdd);

                                            }
                                            if (jsonObject.has("last_name")) {
                                                //GradientDrawable gd = new GradientDrawable();
                                                // Set the gradient drawable background to transparent
                                                gd.setColor(Color.parseColor("#00ffffff"));
                                                // Set a border for the gradient drawable
                                                gd.setStroke(4, Color.RED);
                                                lname.setBackground(gd);
                                            } else {
                                                final GradientDrawable gdd = new GradientDrawable();
                                                gdd.setColor(Color.parseColor("#82EBFE"));
                                                // Set a border for the gradient drawable
                                                //gd.setStroke(4, Color.RED);
                                                lname.setBackground(gdd);

                                            }
                                            if (jsonObject.has("city_id")) {
                                                Toast.makeText(AddClient.this, "Veuillez renseigner tous les champs en rouge", Toast.LENGTH_SHORT).show();
                                                //GradientDrawable gd = new GradientDrawable();
                                                // Set the gradient drawable background to transparent
                                                gd.setColor(Color.parseColor("#00ffffff"));
                                                // Set a border for the gradient drawable
                                                gd.setStroke(4, Color.RED);
                                                city.setBackground(gd);
                                            } else {
                                                final GradientDrawable gdd = new GradientDrawable();
                                                gdd.setColor(Color.parseColor("#82EBFE"));
                                                // Set a border for the gradient drawable
                                                //gd.setStroke(4, Color.RED);
                                                city.setBackground(gdd);

                                            }
                                            if (jsonObject.has("type")) {
                                                Toast.makeText(AddClient.this, "Veuillez renseigner tous les champs en rouge", Toast.LENGTH_SHORT).show();
                                                //GradientDrawable gd = new GradientDrawable();
                                                // Set the gradient drawable background to transparent
                                                gd.setColor(Color.parseColor("#00ffffff"));
                                                // Set a border for the gradient drawable
                                                gd.setStroke(4, Color.RED);
                                                type.setBackground(gd);
                                            } else {
                                                final GradientDrawable gdd = new GradientDrawable();
                                                gdd.setColor(Color.parseColor("#82EBFE"));
                                                // Set a border for the gradient drawable
                                                //gd.setStroke(4, Color.RED);
                                                type.setBackground(gdd);

                                            }
                                            if (jsonObject.has("adress")) {
                                                Toast.makeText(AddClient.this, "Veuillez renseigner tous les champs en rouge", Toast.LENGTH_SHORT).show();
                                                //GradientDrawable gd = new GradientDrawable();
                                                // Set the gradient drawable background to transparent
                                                gd.setColor(Color.parseColor("#00ffffff"));
                                                // Set a border for the gradient drawable
                                                gd.setStroke(4, Color.RED);
                                                adresse.setBackground(gd);
                                            } else {
                                                final GradientDrawable gdd = new GradientDrawable();
                                                gdd.setColor(Color.parseColor("#82EBFE"));
                                                // Set a border for the gradient drawable
                                                //gd.setStroke(4, Color.RED);
                                                adresse.setBackground(gdd);

                                            }
                                        }

                                    }


                                }

                            }

                            @Override
                            public void onFailure(Call<Respond> call, Throwable t) {
                                Toast.makeText(AddClient.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        });

                    }
                }
                }
            });




    }

    public String loadToken(){
        SharedPreferences sharedPref = getSharedPreferences("Mypref",MODE_PRIVATE);
        return sharedPref.getString("token","");
    }
}

