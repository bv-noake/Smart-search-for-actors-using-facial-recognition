package com.example.wit;


import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import android.view.KeyEvent;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.os.Handler;
public class Homepage extends AppCompatActivity  {

    private EditText Email, Password;

    private Button login;
    private Button createaccount;
    private ProgressDialog progressDialog, loginval;
    private SharedPreferenceConfig sharedPreferenceConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);


        getSupportActionBar().hide();



        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);
        createaccount = (Button) findViewById(R.id.button3);
        progressDialog = new ProgressDialog(this);
        loginval = new ProgressDialog(this);
        login = (Button) findViewById(R.id.button2);


        progressDialog.setMessage("Please wait...");
        progressDialog.setTitle("Checking user details");


        sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());

        if (sharedPreferenceConfig.readloginStatus())
        {
            viewcamera();
            finish();
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();
                if ((Email.getText().toString().trim().length() > 0) && (Password.getText().toString().trim().length() > 0)) {
                    userLogin o = new userLogin();
                    o.execute(Email.getText().toString(), Password.getText().toString());
                } else {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
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



        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createaccount();
            }
        });


    }



    public class userLogin extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //Toast.makeText(getBaseContext(),s,Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... params) {

            String Email = params[0];
            String Password = params[1];

            try {
                String data = URLEncoder.encode("Email","UTF-8") + "="+
                        URLEncoder.encode(Email, "UTF-8")+"&"+
                        URLEncoder.encode("Password","UTF-8") + "="+
                        URLEncoder.encode(Password, "UTF-8");

                //utils.getIPAddress(true); // IPv4
                //utils.getIPAddress(false); // IPv6

                URL url = new URL("http://10.167.122.103/App/login.php?"+data);
                URLConnection con = url.openConnection();
                con.setDoOutput(true);

                BufferedReader read = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder s = new StringBuilder();
                String line = null;
                while((line = read.readLine()) !=null) {
                    s.append(line);
                }
                final String result = s.toString();
                System.out.println(result);



                progressDialog.cancel();


                runOnUiThread(new Runnable() {
                    public void run() {
                        System.out.println("1");
                        if (result.toString().equals("valid")) {

                            SharedPreferences sharedPreferences = getSharedPreferences("application", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Email", Email);
                            editor.putString("Password", Password);
                            editor.apply();

                            loginval.setMessage("Authorised, logging you in now...");
                            loginval.show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    loginval.cancel();
                                }
                                }, 9000);


                            sharedPreferenceConfig.writeLoginStatus(true);
                            viewcamera();



                        }
                        if (result.toString().equals("not valid")) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Incorrect Details, Please try again...", Toast.LENGTH_SHORT);
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




    public void createaccount() {
        Intent intent = new Intent(this, createaccount.class);
        startActivity(intent);

    }

    public void viewcamera() {
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if ( progressDialog!=null && progressDialog.isShowing() )
        {
            progressDialog.cancel();
        }



    }

    // 2) :
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if ( progressDialog!=null && progressDialog.isShowing())
        {
            progressDialog.cancel();
        }
        if ( loginval!=null && loginval.isShowing() )
        {
            loginval.cancel();
        }



    }

}
