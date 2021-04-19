package com.example.school;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    private Timer timer;
    private FirebaseUser muser;
    private FirebaseAuth mauth;
    private FirebaseAuth.AuthStateListener mauthlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer=new Timer();
        mauth=FirebaseAuth.getInstance();
        mauthlist=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                muser=firebaseAuth.getCurrentUser();
                if(muser!=null) {
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            startActivity(new Intent(MainActivity.this, Main2Activity.class));
finish();
                        }
                    }, 0000);
                }
                // Toast.makeText(getApplicationContext(),"ALREADY LOG IN",Toast.LENGTH_SHORT).show();



                else
                {
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {

                            startActivity(new Intent(MainActivity.this,Main2Activity.class));
finish();
                        }
                    },2500);
                    Toast.makeText(getApplicationContext(),"PLEASE LOG IN",Toast.LENGTH_SHORT).show();

                }
            }
        };


    }

    @Override
    protected void onStart() {
        super.onStart();
        mauth.addAuthStateListener(mauthlist);
    }


    @Override
    protected void onStop() {
        super.onStop();

        mauth.removeAuthStateListener(mauthlist);

    }


}

