package com.mr.sinaustorage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class ExternalStorageActivity extends AppCompatActivity {
    private EditText etContent;
    private TextView tvContent;
    public static final String FILENAME = "example.txt";
    public static final int REQUEST_CODE_STORAGE = 100; //Yang penting lebih dari 0

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_storage);
        etContent = findViewById(R.id.et_content);
        tvContent = findViewById(R.id.tv_content);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_CODE_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }
        }
    }

    public boolean checkStoragePermission(){
        if (Build.VERSION.SDK_INT >= 23){
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE);
                return false;
            }
        } else {
            return true;
        }
    }

    public void createFile(View view) {
        String content = etContent.getText().toString();
        File file = new File(getFilesDir(), FILENAME);

        FileOutputStream fileOutputStream;
        try {
            file.createNewFile();
            fileOutputStream = new FileOutputStream(file, true);
            fileOutputStream.write(content.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editFile(View view){
        String content = etContent.getText().toString();
        File file = new File(getFilesDir(), FILENAME);

        FileOutputStream fileOutputStream;
        try {
            file.createNewFile();
            fileOutputStream = new FileOutputStream(file, false);
            fileOutputStream.write(content.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFile(View view){
        File file = new File(getFilesDir(), FILENAME);
        if(file.exists()){
            StringBuilder text = new StringBuilder();

            try{
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

                String line = bufferedReader.readLine();

                while (line != null){
                    text.append(line);
                    line = bufferedReader.readLine();
                }
                bufferedReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            tvContent.setText(text);
        } else {
            tvContent.setText("File not exist");
        }
    }

    public void deleteFile(View view){
        File file = new File(getFilesDir(), FILENAME);
        if(file.exists()){
            file.delete();
        } else {
            tvContent.setText("File not exist");
        }
    }
}
