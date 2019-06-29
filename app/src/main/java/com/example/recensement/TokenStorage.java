package com.example.recensement;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class TokenStorage extends Activity {

    private String tokenFile;
    private String TokenFilecontents;

    public TokenStorage(String tokenFile) {
        this.tokenFile = tokenFile;
        //TokenFilecontents = tokenFilecontents;
    }

    public String getTokenFile() {
        return tokenFile;
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

    public String readFile(){
        String text="";
        try {
            //String filename = "Token";
            //String fileContents = "Hello world!";
            FileInputStream inputStream;
            inputStream = openFileInput(getTokenFile());
            int size = inputStream.available();
            byte[]  buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            text= new String(buffer);
            //Log.d("file:",text);
            return text;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }

}
