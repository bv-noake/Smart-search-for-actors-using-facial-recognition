package com.example.wit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.os.Bundle;
import android.widget.ImageView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;
import android.net.Uri;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private ImageView Imageres;


    String currentImagePath = null;
    private static final int IMAGE_REQUEST = 1;

    float x1, x2, y1, y2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Imageres = findViewById(R.id.imageView);


        getSupportActionBar().hide();




    }

    public void takepicture(View view) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (cameraIntent.resolveActivity(getPackageManager())!=null)
        {
            //startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            File imageFile = null;
            try {
                imageFile = getImageFile();
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
                Python.start(new AndroidPlatform(context));

                Python py = Python.getInstance();
                PyObject pyObject = py.getModule("facerecognition").callAttr("main", ""+imageFile);
            }



        }

    }

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBit = (Bitmap) extras.get("data");
            Imageres.setImageBitmap(imageBit);


        }

    }

     */



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



}
