package com.example.qrtagbarcode;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class RestApiTest extends AppCompatActivity {


    private RequestQueue requestQueue;
    private EditText edttour, edtdate, edtspinnerbag;
    private Button btnadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rest_api_test);

        edttour = (EditText)findViewById(R.id.edttour);
        edtdate = (EditText)findViewById(R.id.edtdate);
        edtspinnerbag = (EditText)findViewById(R.id.edtspinnerbag);

        btnadd = (Button) findViewById(R.id.btnadd);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = "{"+
                        "\"QRMaster_Tour\"" + "\"" + edttour.getText().toString() + "\","+
                        "\"QRMaster_Date\"" + "\"" + edtdate.getText().toString() + "\""+
                        "\"QRMaster_Bag\"" + "\"" + edtspinnerbag.getText().toString() + "\""+
                        "}";
                Submit(data);
                //msg("Added Successfully");
            }
        });
    }

    private void Submit(String data)
    {
        final String savedata = data;
        String URL="http://58.27.255.29:90/api/QR/QRMasterAdd";

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject objres=new JSONObject(response);
                    Toast.makeText(getApplicationContext(),objres.toString(),Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_LONG).show();

                }
                Log.i("VOLLEY", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                Log.v("VOLLEY", error.toString());
            }
        })
        {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return savedata == null ? null : savedata.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    //Log.v("Unsupported Encoding while trying to get the bytes", data);
                    return null;
                }
            }

        };
        requestQueue.add(stringRequest);
    }

    // fast way to call Toast
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }
}

