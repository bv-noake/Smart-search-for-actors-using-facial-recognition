package com.example.wit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import java.util.ArrayList;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class ViewSearches extends AppCompatActivity {
    Button button;
    private SharedPreferenceConfig sharedPreferenceConfig;
    ArrayList<String> searcharray = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_searches);




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());
        SharedPreferences sharedPreferences = getSharedPreferences("application", Context.MODE_PRIVATE);
        String Email = sharedPreferences.getString("Email", "default");

        ViewSearches.searches o = new searches();
        o.execute(Email);


        }

    public class searches extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //Toast.makeText(getBaseContext(),s,Toast.LENGTH_SHORT).show();
        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... params) {

            String Email = params[0];


            try {
                String data = URLEncoder.encode("Email", "UTF-8") + "=" +
                        URLEncoder.encode(Email, "UTF-8");


                URL url = new URL("http://192.168.43.35:80/App/searches.php?" + data);
                URLConnection con = url.openConnection();
                con.setDoOutput(true);

                BufferedReader read = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder s = new StringBuilder();
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        String line = null;
                        while (true) {
                            try {
                                if (!((line = read.readLine()) != null)) break;
                                {
                                    if (line.equals("not valid")) {

                                        TextView textView = (TextView) findViewById(R.id.viewmsg);
                                        textView.setText("You don't have any searches");
                                    } else {
                                        String[] arr = line.split(" . ");

                                        for ( String ss : arr) {
                                            System.out.println(ss);
                                            searcharray.add(ss);
                                        }
                                        TextView textView = (TextView) findViewById(R.id.viewmsg);
                                        textView.setText("You Searched for:");
                                        for (int i = 0; i< searcharray.size(); i++) {
                                            System.out.println(searcharray );

                                        }

                                    }
                                }


                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        }
                    }

                });
                final String result = s.toString();
                System.out.println(result);







            }
            catch (Exception e) {
                e.printStackTrace();
            }



            return null;
        }
    }

}
