package com.example.wit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.MotionEvent;
import android.webkit.WebView;

import android.os.Bundle;

public class celebimdb extends AppCompatActivity {
    float x1, x2, y1, y2;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celebimdb);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading up the page now...");

        getSupportActionBar().hide();

        WebView myWebView = (WebView) findViewById(R.id.webview);

        myWebView.loadUrl("https://www.imdb.com/name/nm0429367/");
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

                if (x1<x2) {
                    Intent i = new Intent(celebimdb.this, celebamazon.class);
                    startActivity(i);

                }
                break;
        }
        return false;
    }
}
