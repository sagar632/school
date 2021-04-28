package com.example.school;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.college_notice;


public class doattendance extends Fragment {
private RecyclerView recyclerView;
private ProgressDialog progressDialog;
   private attendancerecycler attendancerecycler;
    private Connection connection=null;
    static List<model.studentattendance> stddlist=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_doattendance, container, false);



        stddlist=attendance.getStdlist();
            recyclerView=v.findViewById(R.id.attendancerecycler);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final List<model.studentattendance> studentlist  =new ArrayList<>();
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("");
        progressDialog.setMessage("Loading");
        progressDialog.show();
attendancerecycler=new attendancerecycler(getContext(),stddlist);
recyclerView.setAdapter(attendancerecycler);



        String date = getArguments().getString("date");
        String classe = getArguments().getString("class");
        String examtype = getArguments().getString("examtype");
      //  Toast.makeText(getContext(),date+classe+examtype,Toast.LENGTH_LONG).show();
















        return v;
    }

    private List<model.studentattendance> listarry() {

        List<model.studentattendance> sublist=new ArrayList<>();
        sublist.add(new model.studentattendance("English","40","30","35"));
        sublist.add(new model.studentattendance("English","4","30","35"));
        sublist.add(new model.studentattendance("English","400","30","35"));









        return sublist;



    }

}
