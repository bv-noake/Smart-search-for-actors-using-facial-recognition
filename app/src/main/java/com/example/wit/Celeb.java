package com.example.wit;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileNotFoundException;
import android.annotation.SuppressLint;

import android.app.ProgressDialog;
import android.content.Intent;
import java.io.OutputStreamWriter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.graphics.BitmapFactory;
import java.net.HttpURLConnection;

import android.os.Handler;
import android.view.MotionEvent;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;

import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Base64;

public class Celeb extends AppCompatActivity {

    float x1, x2, y1, y2;
    private SharedPreferenceConfig sharedPreferenceConfig;
    private ProgressDialog progressdialog, progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celeb);

        progressdialog = new ProgressDialog(this);
        progressDialog = new ProgressDialog(this);
        progressdialog.setMessage("Searching...");
        progressdialog.setTitle("We will now look for your celebrity");

        getSupportActionBar().hide();

        runOnUiThread(new Runnable() {
            public void run() {
                progressdialog.show();

            }
        });

        Celeb.celebs o = new celebs();
        o.execute();


    }


    public class celebs extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //Toast.makeText(getBaseContext(),s,Toast.LENGTH_SHORT).show();
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... params) {

            sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());
            Intent intent = getIntent();
            File Image = (File) intent.getSerializableExtra("Image");

            String encoded = encodeFileToBase64Binary(Image);
            System.out.println(encoded);



            try {

                String data = URLEncoder.encode("Image", "UTF-8") + "=" +
                        URLEncoder.encode(encoded, "UTF-8");


                URL url = new URL("http://192.168.43.35/App/pythonconnect.php?");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setDoOutput(true);
                con.getOutputStream().write(data.getBytes("UTF-8"));
                con.getInputStream();

                //URL url = new URL("http://192.168.43.35:80/App/pythonconnect.php?" + data);


                BufferedReader read = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder s = new StringBuilder();
                String line = null;
                while ((line = read.readLine()) != null) {
                    s.append(line);
                }
                final String result = s.toString();
                runOnUiThread(new Runnable() {
                    public void run() {
                        progressdialog.cancel();

                    }
                });




                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        TextView textView = (TextView) findViewById(R.id.celebname);
                        textView.setText(result);


                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private static String encodeFileToBase64Binary(File file) {
        String encodedfile = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile = Base64.getEncoder().encodeToString(bytes);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return encodedfile;
    }


    public boolean onTouchEvent(MotionEvent touchevent) {
        switch (touchevent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = touchevent.getX();
                y1 = touchevent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchevent.getX();
                y2 = touchevent.getY();
                if (x1 > x2) {
                    Intent i = new Intent(Celeb.this, celebamazon.class);
                    startActivity(i);

                }
                break;
        }
        return false;
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (progressdialog != null && progressdialog.isShowing()) {
            progressdialog.cancel();
        }


    }

    // 2) :
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressdialog != null && progressdialog.isShowing()) {
            progressdialog.cancel();
        }



    }
}
