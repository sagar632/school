package com.example.school;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

import model.user_info;

public class Main3Activity extends AppCompatActivity {

private FirebaseAuth mauth;
private FirebaseUser muser;
private String userId;
private TextView textView;
private DatabaseReference databaseReference;
private model.user_info user_info;
private String  isss;
private FirebaseAuth.AuthStateListener authListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        mauth=FirebaseAuth.getInstance();
        muser=mauth.getCurrentUser();textView=findViewById(R.id.textView9);
        databaseReference=FirebaseDatabase.getInstance().getReference("students");userId=muser.getUid();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
           isss=snapshot.child("dxC6n1Zj9sYDE8cJztPZZpZnrZ92").child("email").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
isss="hello";
            }
        });
       Toast.makeText(Main3Activity.this,isss,Toast.LENGTH_LONG).show();
      //  Toast.makeText(Main3Activity.this,isstd,Toast.LENGTH_LONG).show();











    }
}
