package com.example.qrtagbarcode;

import android.IntentResult;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.widget.AdapterView.OnItemSelectedListener;
import android.IntentIntegrator;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonIOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MasterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edttour, edtdate, edtcode, edtmasterid, edttextbag;
    Spinner edtspinnerbag;
    Button btnadd, btnShowPDF;
    ProgressBar pbbar;
    ListView myListView, PDFListView;
    String proid;
    DatePickerDialog datePickerDialog;
    LinearLayout refreshLayout;

    ListView ListView;
    MyAdapter adapter;
    public static ArrayList<DetailListView> DetailArrayList = new ArrayList<>();
    DetailListView Detail;

    ListView ListViewPDF;
    MyAdapterPDF adapterPDF;
    public static ArrayList<PdfReportList> PDFArrayList = new ArrayList<>();
    PdfReportList PDF;

    public String spinnerText, masterId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_master);


        edttour = findViewById(R.id.edttour);
        edtdate = findViewById(R.id.edtdate);
        edtcode = findViewById(R.id.edtcode);
        edtspinnerbag = findViewById(R.id.edtspinnerbag);
        btnadd = findViewById(R.id.btnadd);
        btnShowPDF = findViewById(R.id.btnShowPDF);
        refreshLayout = findViewById(R.id.refreshLayout);
        pbbar = findViewById(R.id.pbbar);
        pbbar.setVisibility(View.GONE);
        myListView = findViewById(R.id.myListView);
        PDFListView = findViewById(R.id.PDFListView);
        proid = "";
        edtcode.setEnabled(false);
        btnShowPDF.setEnabled(false);

        myListView.setClickable(true);
        adapterPDF = new MyAdapterPDF(this, PDFArrayList);
        PDFListView.setAdapter(adapterPDF);



// GETTING CURRENT DATE //

        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.MONTH, -1);
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        edtdate.setText(year + "-" + (month + 1) + "-" + day);

        datePickerDialog = new DatePickerDialog(MasterActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int myyear, int monthofyear, int dayofyear) {
                edtdate.setText(myyear + "-" + (monthofyear + 1) + "-" + dayofyear);
            }
        }, year, month, day);

        edtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MONTH, 1);
                calendar.add(Calendar.MONTH, -1);

                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);

                edtdate.setText(year + "-" + (month + 1) + "-" + day);

                datePickerDialog = new DatePickerDialog(MasterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int myyear, int monthofyear, int dayofyear) {
                        edtdate.setText(myyear + "-" + (monthofyear + 1) + "-" + dayofyear);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });


// Spinner click listener
        edtspinnerbag.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                // Your code to do something with the selected item
                spinnerText = edtspinnerbag.getSelectedItem().toString();
//                spinnerText.setText(Text);
            }
        });

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add(0,"SELECT BAG");
        categories.add("1");
        categories.add("2");
        categories.add("3");
        categories.add("4");
        categories.add("5");
        categories.add("6");
        categories.add("7");
        categories.add("8");
        categories.add("9");
        categories.add("10");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        edtspinnerbag.setAdapter(dataAdapter);


// BUTTON ADD METHOD //

        btnadd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                MasterPostRequest();

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                btnadd.setEnabled(false);
            }
        });

        btnShowPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String pdf = edtmasterid.getText().toString();
                if(masterId.equals(""))
                {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Please! Insert Tour Detail..", Toast.LENGTH_SHORT);
                    toast.show();
                }

                else{

//                  String pdf = edtmasterid.getText().toString();
                    Intent intent = new Intent(MasterActivity.this, WebView.class);
                    intent.putExtra("Listviewclickvalue", masterId);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    startActivity(intent);

                }

            }
        });


        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String st = DetailArrayList.get(position).getQRDetail_Code();
                Log.d("Testing", "in onItemClick: " + st);
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://58.27.255.29/Reports/Report_PackingTagDetails.aspx?spId="+st));
//                startActivity(browserIntent);

                Intent intent = new Intent(MasterActivity.this, WebView.class);
                intent.putExtra("Listviewclickvalue",st);

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                startActivity(intent);
            }
        });


// LONG PRESS SCREEN //
        refreshLayout.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                EmptyFields();

                Toast toast = Toast.makeText(getApplicationContext(),
                        "Successful", Toast.LENGTH_SHORT);
                toast.show();

                btnadd.setEnabled(true);

                return false;
            }
        });


//        btnscan.setOnClickListener(this);

        edtcode.setOnClickListener(this);


        String URL="http://58.27.255.29:90/api/QR/QRMasterList";

        RequestQueue requestQueue=Volley.newRequestQueue(this);

        JsonObjectRequest objectRequest=new JsonObjectRequest(
                Request.Method.GET, URL, null, new Response.Listener<JSONObject>()
        {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Rest Response", response.toString());

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.e("Rest Response", error.toString());
                    }
                }
        );

        requestQueue.add(objectRequest);


        ListView = findViewById(R.id.myListView);
        adapter = new MyAdapter(this, DetailArrayList);
        ListView.setAdapter(adapter);

//        ListView.setAdapter(null);
//        PDFListView.setAdapter(null);
    }


    public void onClick(View v) {

        if (v.getId() == R.id.edtcode) {
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String code = scanningResult.getContents();
            String format = scanningResult.getFormatName();
            edtcode.setText(code);

            if (code != null) {
                CodeSeparator();
                btnShowPDF.setEnabled(true);
            }

            else{
                Toast toast = Toast.makeText(getApplicationContext(),
                        "No scan data received!", Toast.LENGTH_SHORT);
                toast.show();
            }

            DetailPostRequest();

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            retrieveDetailList();

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            retrievePDF();

            edtcode.getText().clear();

        }

        else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }

    }


//CODE SEPARATOR//

    public void CodeSeparator(){

        String code = edtcode.getText().toString(); // code = 58.27.255.29/PackingTagDetails.aspx?spId=3298
        String[] dateParts = code.split("=");
        edtcode.setText(dateParts[1]); // textView is Code

    }

// MASTER POST METHOD//

    public void MasterPostRequest() {

        final String tour = edttour.getText().toString().trim();
        final String date = edtdate.getText().toString().trim();
        final String User_Id = getIntent().getStringExtra("GetUserId");

        //validations

        if (TextUtils.isEmpty(tour)) {
            edttour.setError("Please Enter Tour Name");
            edttour.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(date)) {
            edtdate.setError("Please Enter Date");
            edtdate.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(spinnerText)) {
//            edtspinnerbag.getItemAtPosition(position).toString();
//            edtspinnerbag.setTe("Please Enter Bag");
            edtspinnerbag.requestFocus();
            return;
        }


        RequestQueue requestQueue= Volley.newRequestQueue(MasterActivity.this);
        String url="http://58.27.255.29:90/api/QR/QRMasterAdd";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //parse json data
                try {
                    JSONObject jsonObject = new JSONObject(response);
//                    String masterId = (jsonObject.getString("MasterID")+"\n");

                    masterId = (jsonObject.getString("MasterID")+"\n");

//                    edtmasterid.append(jsonObject.getString("MasterID")+"\n");

                    msg(jsonObject.getString("Message"));
                    edtcode.setEnabled(true);
                }
                catch (Exception e){
                    e.printStackTrace();
//                    post_response_text.setText("POST DATA : unable to Parse Json");
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Unable to Parse Json", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                post_response_text.setText("Post Data : Response Failed");
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Response Failed...", Toast.LENGTH_SHORT);
                toast.show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("QRMaster_Tour",tour);
                params.put("QRMaster_Date",date);
                params.put("QRMaster_Bag",spinnerText);
                return params;
            }

            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }

        };

        requestQueue.add(stringRequest);

    }


// DETAIL POST METHOD//

    public void DetailPostRequest() {
        RequestQueue requestQueue= Volley.newRequestQueue(MasterActivity.this);
        String url="http://58.27.255.29:90/api/QR/QRDetailAdd";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //parse json data
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    msg(jsonObject.getString("Message")+"\n");
                }
                catch (Exception e){
                    e.printStackTrace();
//                    post_response_text.setText("POST DATA : unable to Parse Json");
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Unable to Parse Json", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                post_response_text.setText("Post Data : Response Failed");
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Response Failed...", Toast.LENGTH_SHORT);
                toast.show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("QRDetail_QRMaster_Id", masterId);
                params.put("QRDetail_Code", edtcode.getText().toString());
//                params.put("data_4_post","Value 4 Data");
                return params;

            }

            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }

        };

        requestQueue.add(stringRequest);

//        Toast toast = Toast.makeText(getApplicationContext(),
//                "Scanned Successfully", Toast.LENGTH_SHORT);
//        toast.show();

    }

    private void sendGetRequest() {
        //get working now
        //let's try post and send some data to server
        final String masterID = edtmasterid.getText().toString().trim();

        RequestQueue queue = Volley.newRequestQueue(MasterActivity.this);
        String url = "http://58.27.255.29:90/api/QR/QRDetailByMaster?Id="+masterID+"";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                get_response_text.setText("Data :"+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    edttour.append(jsonObject.getString("MasterID") + "\n");
//                    get_response_text.setText("Data 1 :"+jsonObject.getString("data_1")+"\n");
//                    get_response_text.append("Data 2 :"+jsonObject.getString("data_2")+"\n");
//                    get_response_text.append("Data 3 :"+jsonObject.getString("data_3")+"\n");
                } catch (Exception e) {
                    e.printStackTrace();
//                    get_response_text.setText("Failed to Parse Json");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                edttour.setText("Data : Response Failed");
            }
        });
    }

// fast way to call Toast//

    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

// Empty Fields Method//

    private void EmptyFields() {

        edttour.getText().clear();
        edttextbag.getText().clear();
        edtmasterid.getText().clear();
        edtcode.getText().clear();
    }


    public void retrieveDetailList(){

        String url ="http://58.27.255.29:90/api/QR/QRDetailByMaster?Id="+masterId+"";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                DetailArrayList.clear();
                try {

                    JSONObject jsonObject = new JSONObject(response);
//                    String success = jsonObject.getString("Success");
                    JSONArray jsonArray = jsonObject.getJSONArray("QRDetailList");

//                    if(success.equals("1")){

                    for (int i=0; i<jsonArray.length(); i++){

                        JSONObject object = jsonArray.getJSONObject(i);
                        String QRDetail_Id =  object.getString("QRDetail_Id");
                        String QRDetail_QRMaster_Id =  object.getString("QRDetail_QRMaster_Id");
                        String QRDetail_Code =  object.getString("QRDetail_Code");

                        Detail = new DetailListView(QRDetail_Id,QRDetail_QRMaster_Id,QRDetail_Code);
                        DetailArrayList.add(Detail);
                        adapter.notifyDataSetChanged();

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
                Toast.makeText(MasterActivity.this, error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void retrievePDF(){

        String url ="http://58.27.255.29:90/api/QR/TagBarcodeReportList?id="+masterId+"";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                PDFArrayList.clear();
                try {

                    JSONObject jsonObject = new JSONObject(response);
//                    String success = jsonObject.getString("Success");
                    JSONArray jsonArray = jsonObject.getJSONArray("TagBarcodeReportList");

//                    if(success.equals("1")){

                    for (int i=0; i<jsonArray.length(); i++){

                        JSONObject object = jsonArray.getJSONObject(i);
                        String PDFReport =  object.getString("PDFReport");

                        PDF = new PdfReportList(PDFReport);
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
                Toast.makeText(MasterActivity.this, error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


    public void EXretrievePDF(){

        String url ="http://58.27.255.29:90/api/QR/TagBarcodeReportByQR?Id="+edtmasterid.getText().toString()+"";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                PDFArrayList.clear();
                try {

                    JSONObject jsonObject = new JSONObject(response);
//                    String success = jsonObject.getString("Success");
                    JSONArray jsonArray = jsonObject.getJSONArray("TagBarcodeReportDTL");

//                    if(success.equals("1")){

                    for (int i=0; i<jsonArray.length(); i++){

                        JSONObject object = jsonArray.getJSONObject(i);
                        String PDFReport =  object.getString("PDFReport");

                        PDF = new PdfReportList(PDFReport);
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
                Toast.makeText(MasterActivity.this, error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

}



