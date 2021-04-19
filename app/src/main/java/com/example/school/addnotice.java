package com.example.school;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class addnotice extends AppCompatActivity   {
    private EditText heading,text;
    private ImageButton noticeimage;
    private Button post;
    private Spinner classes;
    String[] classe= { "All", "Class Nursery", "Class LKG", "Class UKG", "Class 1","Class 2","Class 3","Class 4",
            "Class 5 ","Class 6","Class 7","Class 8","Class 9","Class 10"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnotice);
        heading=findViewById(R.id.heading);
        text=findViewById(R.id.notice_text);
        noticeimage=findViewById(R.id.notice_imagebutton);
        post=findViewById(R.id.post);
        classes=findViewById(R.id.spinner1);

        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item, classe);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classes.setAdapter(adapter);
        classes.getSelectedItem().toString();
        Toast.makeText(getApplicationContext(), classes.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
        classes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),adapterView.getItemAtPosition(i).toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }






}
