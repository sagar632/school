package com.example.school;

import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectionclass {
    private static final String LOG = "DEBUG";
    private static String ip ;
    private static String port ;
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database ;
    private static String username ;
    private static String password = "testb";
    private Connection connection = null;
private DatabaseReference db;

    private static String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;
    public Connection connect() {
        db=FirebaseDatabase.getInstance().getReference("db_info");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ip=snapshot.child("ip").getValue(String.class);
                port=snapshot.child("port").getValue(String.class);
                database=snapshot.child("db_name").getValue(String.class);
                username=snapshot.child("username").getValue(String.class);
                password=snapshot.child("password").getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Connection conn = null;
        String ConnURL = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName(Classes);
            ConnURL = "jdbc:jtds:sqlserver://" + ip +":"+port+";"
                    + "databaseName=" + database + ";user=" + username + ";password="
                    + password + ";";
            conn = DriverManager.getConnection(ConnURL);
            Log.d("Log","sucess");
        } catch (SQLException e) {
            Log.d(LOG, e.getMessage());


        } catch (ClassNotFoundException e) {
            Log.d(LOG, e.getMessage());
        }
        return conn;
    }
}
