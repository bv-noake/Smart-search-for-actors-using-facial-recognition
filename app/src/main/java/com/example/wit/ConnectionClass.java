package com.example.wit;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {

    //String classs = "com.mysql.cj.jdbc.Driver";



    String url = "jdbc:mysql://localhost/project";
    public String user = "root";
    public String pass = "";
    public String db = "project";

    @SuppressLint("NewAPI")
    public Connection CONN() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Connection conn = null;
        String ConnURL = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("8");
            Connection con= DriverManager.getConnection("jdbc:mysql://192.168.1.2/project", "root", "");
            //ConnURL = "jdbc:jtds:sqlserver://" + "localhost" +";databaseName="+ db + ";user=" + user+ ";password=" + pass + ";";
            System.out.println("9");
            //Connection conn = DriverManager.getConnection(ConnURL);

            System.out.println("SUCCESS");

        }
        catch (SQLException se){
            Log.e("ERROR", se.getMessage());
            System.out.println("NO1");
        }
        catch (ClassNotFoundException e){
            Log.e("ERROR", e.getMessage());
            System.out.println("NO2");
        }
        catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            System.out.println("NO3");
        }

        return conn;
    }
}
