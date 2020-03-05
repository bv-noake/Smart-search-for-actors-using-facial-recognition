package com.example.wit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class createaccount extends AppCompatActivity {

    Button loginpage, signup;
    EditText Email, FName, LName, Password;
    ProgressDialog adduser, sregister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount);

        getSupportActionBar().hide();
        adduser = new ProgressDialog(this);
        sregister = new ProgressDialog(this);

        adduser.setMessage("Please wait...");
        adduser.setTitle("Registering your details");


        loginpage = (Button) findViewById(R.id.loginbtn);
        signup = (Button) findViewById(R.id.signupbtn);
        Email = (EditText) findViewById(R.id.email);
        FName = (EditText) findViewById(R.id.fname);
        LName = (EditText) findViewById(R.id.lname);
        Password = (EditText) findViewById(R.id.password);


        loginpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Homepage();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adduser.show();
                if ((FName.getText().toString().trim().length() > 0) && (LName.getText().toString().trim().length() > 0)&& (Email.getText().toString().trim().length() > 0) && (Password.getText().toString().trim().length() > 0)) {
                    registeruser o = new registeruser();
                    o.execute(FName.getText().toString(), LName.getText().toString(), Email.getText().toString(), Password.getText().toString());
                } else {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            adduser.dismiss();
                            Toast toast1 = Toast.makeText(getApplicationContext(), "You're missing required fields", Toast.LENGTH_SHORT);
                            toast1.show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    toast1.cancel();
                                }
                            }, 3000);
                        }
                    });
                }
            }
        });

    }

    public class registeruser extends AsyncTask<String, String, String> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... params) {

            String FName = params[0];
            String LName = params[1];
            String Email = params[2];
            String Password = params[3];

            try {
                System.out.println("4");
                String data =
                        URLEncoder.encode("FName","UTF-8") + "="+
                        URLEncoder.encode(FName, "UTF-8")+"&"+
                        URLEncoder.encode("LName","UTF-8") + "="+
                        URLEncoder.encode(LName, "UTF-8")+"&" +
                        URLEncoder.encode("Email","UTF-8") + "="+
                        URLEncoder.encode(Email, "UTF-8") + "&" +
                        URLEncoder.encode("Password","UTF-8") + "="+
                        URLEncoder.encode(Password, "UTF-8");
                System.out.println("3");
                //utils.getIPAddress(true); // IPv4
                //utils.getIPAddress(false); // IPv6

                URL url = new URL("http://10.167.122.103/App/register.php?"+data);
                URLConnection con = url.openConnection();
                con.setDoOutput(true);
                System.out.println("2");

                BufferedReader read = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder s = new StringBuilder();
                String line = null;
                while((line = read.readLine()) !=null) {
                    s.append(line);
                }
                System.out.println("1");

                final String result = s.toString();
                System.out.println(result);
                adduser.dismiss();
                runOnUiThread(new Runnable() {
                    public void run() {
                        System.out.println("1");
                        if (result.toString().equals("valid")) {
                            sregister.setMessage("Register complete, you can now log in...");
                            sregister.show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    sregister.dismiss();
                                }
                            }, 6000);

                            Homepage();
                        }
                        if (result.toString().equals("not valid")) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Email address already registered, please try again...", Toast.LENGTH_SHORT);
                            toast.show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    toast.cancel();
                                }
                            }, 3000);

                        }

                    }
                });


            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }


    public void Homepage() {
        Intent intent = new Intent(this, Homepage.class);
        startActivity(intent);
    }
}