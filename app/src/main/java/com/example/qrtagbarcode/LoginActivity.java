package com.example.qrtagbarcode;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonIOException;
import org.json.JSONException;
import org.json.JSONObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity {
    EditText edtuserid,edtusername,edtpass;
    Button btnlogin;
    ProgressBar pbbar;

    String ip, db, un, passwords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

//      connectionClass = new ConnectionClass();
        ip = "10.100.1.5";
        un = "sa";
        passwords = "Compaq786";
        db = "ERPMS";

        edtuserid = findViewById(R.id.edtuserid);
        edtusername = findViewById(R.id.edtusername);
        edtpass = findViewById(R.id.edtpass);
        btnlogin = findViewById(R.id.btnlogin);
        pbbar = findViewById(R.id.pbbar);
        pbbar.setVisibility(View.GONE);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginApi();

            }
        });
    }

// Login METHOD//

    private void LoginApi() {

        final String Name = edtusername.getText().toString().trim();
        final String Pass = edtpass.getText().toString().trim();

        //validations

        if (TextUtils.isEmpty(Name)) {
            edtusername.setError("Please Enter User Name");
            edtusername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(Pass)) {
            edtpass.setError("Please Enter Password");
            edtpass.requestFocus();
            return;
        }


        String url ="http://58.27.255.29:90/api/QR/UserLogin?userName="+Name+"&password="+Pass+"";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
//                    msg(jsonObject.getString("userModel")+"\n");

//                    if(success.equals("userModel")){

                    for (int i=0; i<jsonObject.length(); i++){

                        JSONObject object = jsonObject.getJSONObject("userModel");
                        String Users_Id =  object.getString("Users_Id");
                        String Users_Name =  object.getString("Users_Name");

                        edtuserid.setText(Users_Id);

                        {
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Welcome "+Users_Name, Toast.LENGTH_LONG);
                            toast.show();
                        }

//                        msg(("Welcome "+Users_Name));

                        Intent intent = new Intent(LoginActivity.this, MasterActivity.class);
                        intent.putExtra("GetUserId", Users_Id);
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        startActivity(intent);

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
                Toast.makeText(LoginActivity.this, error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

// fast way to call Toast//

    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }


//CONNECTION API

    @SuppressLint("NewApi")
    private Connection CONN(String _user, String _pass, String _DB,
                            String _server) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;
        try {

            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnURL = "jdbc:jtds:sqlserver://" + _server + ";"
                    + "databaseName=" + _DB + ";user=" + _user + ";password="
                    + _pass + ";";
            conn = DriverManager.getConnection(ConnURL);
        } catch (SQLException se) {
            Log.e("ERRO", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());
        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
        }
        return conn;
    }
}
