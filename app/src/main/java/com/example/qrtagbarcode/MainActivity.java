package com.example.qrtagbarcode;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    EditText edtuserid,edtusername,edtpass;
    Button btnlogin;
    ProgressBar pbbar;
    String userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

//        connectionClass = new ConnectionClass();

        edtuserid = findViewById(R.id.edtuserid);
        edtusername = findViewById(R.id.edtusername);
        edtpass = findViewById(R.id.edtpass);
        btnlogin = findViewById(R.id.btnlogin);
        pbbar = findViewById(R.id.pbbar);
        pbbar.setVisibility(View.GONE);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DoLogin doLogin = new DoLogin();
//                doLogin.execute("");

            }
        });
    }

//    public class DoLogin extends AsyncTask<String,String,String>
//    {
//        String z = "";
//        Boolean isSuccess = false;
//
//
//        String username = edtusername.getText().toString();
//        String password = edtpass.getText().toString();
//
//
//        @Override
//        protected void onPreExecute() {
//            pbbar.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected void onPostExecute(String r) {
//            pbbar.setVisibility(View.GONE);
//            Toast.makeText(MainActivity.this,r,Toast.LENGTH_SHORT).show();
//
//            if(isSuccess) {
//                getMasterId();
//                Intent i = new Intent(MainActivity.this, MasterActivity.class);
//                userid = edtuserid.getText().toString();
//                i.putExtra("Value", userid);
//                startActivity(i);
//                finish();
//            }
//
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            if(username.trim().equals("")|| password.trim().equals(""))
//                z = "Please enter User Id and Password";
//            else
//            {
//                try {
//                    Connection con = connectionClass.CONN();
//                    if (con == null) {
//                        z = "Error in connection with SQL server";
//                    } else {
//                        String query = "select * from Users where Users_Name='" + username + "' and Users_Password='" + password + "'";
//                        Statement stmt = con.createStatement();
//                        ResultSet rs = stmt.executeQuery(query);
//
//                        if(rs.next())
//                        {
//                            z = "Login Successfull";
//                            isSuccess=true;
//                        }
//                        else
//                        {
//                            z = "Invalid Credentials";
//                            isSuccess = false;
//                        }
//
//                    }
//                }
//                catch (Exception ex)
//                {
//                    isSuccess = false;
//                    z = "Exceptions";
//                }
//            }
//            return z;
//        }
//    }
//
//    public void getMasterId() {
//
//        Connection con = connectionClass.CONN();
//        String query1 = "select Users_Id from Users where Users_Inactive = 0 and Users_Name = '"+edtusername.getText().toString()+"'";
//
//        try {
//            Statement stmt = con.createStatement();
//            ResultSet rs = stmt.executeQuery(query1);
//
//            while (rs.next()) {
//                String id = rs.getString("Users_Id");
//                edtuserid.setText(id);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }
}
