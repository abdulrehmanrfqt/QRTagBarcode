package com.example.qrtagbarcode;

import android.os.Bundle;
import android.os.Environment;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.security.Permission;
import java.util.ArrayList;

public class ActivityMain extends AppCompatActivity {

    ListView lv_pdf;
    public static ArrayList<File> fileList = new ArrayList<>();
    PDFAdapter obj_adapter;
    public static int REQUEST_PERMISSION = 1;
    boolean bolean_permission;
    File dir;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv_pdf = (ListView)findViewById(R.id.listView_pdf);

        dir = new File(Environment.getExternalStorageDirectory().toString());

        permission_fn();
    }

    private void permission_fn(){


    }
}
