package com.example.recensement.service;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import static android.content.Context.MODE_PRIVATE;

public class FilesManager {
    private static final String FILE_NAME="";
    Context context;
    private String jwt;
    String filename = "myfile";
    String fileContents = "Hello world!";
    FileOutputStream outputStream;

    private File file = new File(context.getFilesDir(), filename);


    void writeTokenInFile(String jwt) {

        FileInputStream fos = null;

        if (fos != null) {
         //  fos.openFileOutPut(FILE_NAME,MODE_PRIVATE);
        }

    }

}
