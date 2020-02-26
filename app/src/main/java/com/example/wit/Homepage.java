package com.example.wit;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;

public class Homepage extends AppCompatActivity {

    private Button button;
    private Button button1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);


        getSupportActionBar().hide();

        button = (Button) findViewById(R.id.button3);
        button1 = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });
    }



    public void openActivity2() {
        Intent intent = new Intent(this, createaccount.class);
        startActivity(intent);
    }

    public void openActivity3() {
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }

}
