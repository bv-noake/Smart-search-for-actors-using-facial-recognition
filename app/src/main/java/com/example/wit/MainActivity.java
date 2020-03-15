package com.example.wit;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;

import android.os.Handler;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.os.Bundle;
import android.widget.ImageView;
import android.preference.PreferenceManager;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.io.File;
import android.net.Uri;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private ImageView Imageres;
    private ProgressDialog progressdialog;


    private SharedPreferenceConfig sharedPreferenceConfig;


    String currentImagePath = null;
    private static final int IMAGE_REQUEST = 1;

    float x1, x2, y1, y2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Imageres = findViewById(R.id.imageView);

        progressdialog = new ProgressDialog(this);
        progressdialog.setMessage("Searching...");
        progressdialog.setTitle("We will now look for your celebrity");


        getSupportActionBar().hide();



        sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());

        SharedPreferences sharedPreferences = getSharedPreferences("application", Context.MODE_PRIVATE);
        String Email = sharedPreferences.getString("Email", "default");
        String Password = sharedPreferences.getString("Password", "default");
        MainActivity.username o = new username();
        o.execute(Email, Password);



    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void takepicture(View view) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (cameraIntent.resolveActivity(getPackageManager())!=null)
        {
            //startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            File imageFile = null;
            try {
                imageFile = getImageFile();





                Intent intent = new Intent(MainActivity.this, Celeb.class);
                intent.putExtra("Image", imageFile);
                startActivityForResult(intent, 1);


                //celebname();



            }
            catch (IOException e)
            {
                e.printStackTrace();
            }



            if (imageFile!=null)
            {
                Uri imageUri = FileProvider.getUriForFile(this, "com.example.wit.fileprovider", imageFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(cameraIntent, IMAGE_REQUEST);
                Context context = getApplicationContext();
                //Python.start(new AndroidPlatform(context));



                //Python py = Python.getInstance();
                //PyObject pyObject = py.getModule("facerecognition").callAttr("main", ""+encode);
                //celebname();
            }



        }

    }







    private File getImageFile() throws IOException
    {
        String timeStamp = new SimpleDateFormat("yyyymmdd_HHmmss").format(new Date());
        String imageName = "jpg_"+timeStamp+"_";
        File storage = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File imageFile = File.createTempFile(imageName, ".jpg", storage);
        currentImagePath = imageFile.getAbsolutePath();


        return imageFile;

    }







    public boolean onTouchEvent (MotionEvent touchevent) {
        switch (touchevent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = touchevent.getX();
                y1 = touchevent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchevent.getX();
                y2 = touchevent.getY();
                if (y1>y2) {
                    Intent i = new Intent(MainActivity.this, ViewSearches.class);
                    startActivity(i);

                }
                break;
        }
        return false;
    }


    public void userlogout(View view) {

        sharedPreferenceConfig.writeLoginStatus(false);
        startActivity(new Intent(this, Homepage.class));
        finish();
    }








    public class username extends AsyncTask<String, String, String> {
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

                //URL url = new URL("http://192.168.43.35:80/App/username.php?" + data);
                URL url = new URL("http://10.167.120.26/App/username.php?" + data);
                URLConnection con = url.openConnection();
                con.setDoOutput(true);

                BufferedReader read = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder s = new StringBuilder();
                String line = null;
                while ((line = read.readLine()) != null) {
                    s.append(line);
                }
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        final String result = s.toString();
                        System.out.println(result);
                        TextView textView = (TextView) findViewById(R.id.welcometext);
                        textView.setText("Welcome back:  " + result);
                    }
                });

            }
            catch (Exception e) {
                e.printStackTrace();
            }



                return null;
        }
    }

    public void celebname() {

        Intent intent = new Intent(this, Celeb.class);

        startActivity(intent);

    }
}
