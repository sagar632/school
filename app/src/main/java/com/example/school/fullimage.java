package com.example.school;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class fullimage extends AppCompatActivity {
private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullimage);
        imageView=findViewById(R.id.imageView2);
        Intent i = getIntent();
        String name = i.getStringExtra("hello");
        Picasso.with(getApplicationContext()).load(name).into(imageView);
       // Toast.makeText(getApplicationContext(),name,Toast.LENGTH_LONG).show();
    }
}
