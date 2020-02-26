package com.example.wit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.Statement;


public class createaccount extends AppCompatActivity {

    Button button;
    Button signupbtn;
    EditText name;
    EditText email;
    EditText password;
    ConnectionClass connectionClass;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount);

        getSupportActionBar().hide();

        button = (Button) findViewById(R.id.loginbtn);
        signupbtn = (Button) findViewById(R.id.signupbtn);

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        connectionClass = new ConnectionClass();

        progressDialog = new ProgressDialog(this);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity1();
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register register = new Register();
                register.execute("");
            }
        });
    }

    public class Register extends AsyncTask<String, String, String>
    {
        String namestr = name.getText().toString();
        String emailstr = email.getText().toString();
        String passstr = password.getText().toString();
        String z = "";
        boolean isSuccess=false;



        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading... ");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            System.out.println("1");

            if (namestr.trim().equals("") ||emailstr.trim().equals("") ||passstr.trim().equals(""))
            {
                z = "You've left some fields empty...";
                isSuccess = true;

            }
            else
            {
                try {
                    System.out.println("2");
                    Connection con = connectionClass.CONN();
                    System.out.println("3");
                    if (con == null)
                    {
                        z = "Check your internet connection...";
                        isSuccess = true;
                    }
                    else
                    {
                        System.out.println("4");
                        String query = "insert into users values('Types.NULL, '"+namestr+"','"+emailstr+"','"+passstr+"')";
                        Statement statement = con.createStatement();
                        statement.executeUpdate(query);

                        z = "Register Successfully";
                        isSuccess = true;
                    }
                }
                catch (Exception e) {
                    isSuccess = false;
                    z = "Exception"+ e;
                }
            }
            return z;

        }

        @Override
        protected void onPostExecute(String s)
        {
            System.out.println("5");
            if (isSuccess) {
                System.out.println("6");
                Toast.makeText(getBaseContext(), ""+z, Toast.LENGTH_LONG).show();

            }
            progressDialog.hide();
        }


    }

    public void openActivity1() {
        Intent intent = new Intent(this, Homepage.class);
        startActivity(intent);
    }

}
