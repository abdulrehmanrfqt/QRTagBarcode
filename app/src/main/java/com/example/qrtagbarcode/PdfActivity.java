package com.example.qrtagbarcode;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Movie;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonIOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PdfActivity extends AppCompatActivity  {

    EditText edtURL;
    Button buttonOpenlink;

    public static JSONArray fileList;
    ListView PDFListView;
    MyAdapterPDF adapterPDF;
    public static ArrayList<PdfReportList> PDFArrayList = new ArrayList<>();
    PdfReportList PDF;

    public static int REQUEST_PERMISSION = 1;
    boolean bolean_permission;
    PdfReportList dir;

//    String url = "http://58.27.255.29/Reports/Report_PackingTagDetails.aspx?spId=3384";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pdf);

        edtURL = findViewById(R.id.edtURL);
        buttonOpenlink = findViewById(R.id.buttonOpenlink);

        PDFListView = findViewById(R.id.PDFListView);
        adapterPDF = new MyAdapterPDF(this, PDFArrayList);
        PDFListView.setAdapter(adapterPDF);
        PDFListView.setClickable(true);

        buttonOpenlink.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://58.27.255.29/Reports/Report_PackingTagDetails.aspx?spId="+edtURL.getText().toString()));
                startActivity(browserIntent);
            }
        });

        PDFListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String st = PDFArrayList.get(position).PDFReport();
                Log.d("Testing", "in onItemClick: " + st);
//                edtURL.setText(st);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://58.27.255.29/Reports/Report_PackingTagDetails.aspx?spId="+st));
                startActivity(browserIntent);

            }
        });

        retrieveData();


////    public void retrievePDF(){
//
//        String url ="http://58.27.255.29:90/api/QR/TagBarcodeReportList?id=5";
//
//        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                PDFArrayList.clear();
//                try {
//
//                    JSONObject jsonObject = new JSONObject(response);
////                    String success = jsonObject.getString("Success");
//                    JSONArray jsonArray = jsonObject.getJSONArray("TagBarcodeReportList");
//
////                    if(success.equals("1")){
//
//                    for (int i=0; i<jsonArray.length(); i++){
//
//                        JSONObject object = jsonArray.getJSONObject(i);
//                        String PDFReport =  object.getString("PDFReport");
//
//                        PDF = new PdfReportList(PDFReport);
//                        PDFArrayList.add(PDF);
//                        adapterPDF.notifyDataSetChanged();
//
//                    }
////                    }
//
//                }
//                catch (JsonIOException | JSONException e){
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(PdfActivity.this, error.getMessage(),Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(request);
////    }

    }


    public void retrieveData(){

        String url ="http://58.27.255.29:90/api/QR/QRDetailByMaster?Id=5";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                PDFArrayList.clear();
                try {

                    JSONObject jsonObject = new JSONObject(response);
//                    String success = jsonObject.getString("Success");
                    JSONArray jsonArray = jsonObject.getJSONArray("QRDetailList");

//                    if(success.equals("1")){

                    for (int i=0; i<jsonArray.length(); i++){

                        JSONObject object = jsonArray.getJSONObject(i);
                        String QRDetail_Code =  object.getString("QRDetail_Code");

                        PDF = new PdfReportList(QRDetail_Code);
                        PDFArrayList.add(PDF);
                        adapterPDF.notifyDataSetChanged();

                    }
//                    }

                }
                catch (JsonIOException | JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PdfActivity.this, error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
