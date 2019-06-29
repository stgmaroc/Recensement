package com.example.recensement;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import static android.support.v4.content.ContextCompat.getSystemService;

public class BackgroundWorker extends AsyncTask<String,String,String> {
    Context context;
    BackgroundWorker (Context ctx){ context =ctx; }

    @Override
    protected String doInBackground(String... strings) {
        String url_insert="http://154.70.200.106:8005/add.php";

        try {
            URL url=new URL(url_insert);
            String name = strings[0];
            String name2 = strings[1];
            String localisation = strings[2];
            /*create connection to */
            HttpURLConnection httpURLConnection= (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String post_data = URLEncoder.encode("fname","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                    URLEncoder.encode("lname","UTF-8")+"="+URLEncoder.encode(name2,"UTF-8")+"&"+
                    URLEncoder.encode("localisation","UTF-8")+"="+URLEncoder.encode(localisation,"UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            String rslt="";
            String line="";
            while((line = bufferedReader.readLine())!= null){
                rslt += line;
            }
            bufferedReader.close();
            httpURLConnection.disconnect();
            return rslt;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        CharSequence text = "Registring!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        CharSequence text = "en cours!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    protected void onProgressUpdate(String... values) {
        //super.onPostExecute(s);
        CharSequence text = "pas de connexion!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


}
