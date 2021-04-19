package com.example.school;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;




public class selectexamtype extends Fragment {
  RecyclerView recyclerView;
  private TextView rollno,stdname,classe,section,Total,grade,examtype,workingdays,attendance;

  resultadapter resultadapter;
    List<model.result> subblist=new ArrayList<>();

    public selectexamtype() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_selectexamtype, container, false);
       rollno=v.findViewById(R.id.roll_number);
       stdname=v.findViewById(R.id.stdname);
       classe=v.findViewById(R.id.classe);
       section=v.findViewById(R.id.section);
       Total=v.findViewById(R.id.Total);
       grade=v.findViewById(R.id.gradee);
       workingdays=v.findViewById(R.id.workingdays);
       attendance=v.findViewById(R.id.attendance);
            examtype=v.findViewById(R.id.examtype);
            examtype.setText(result.getExamty());
        Total.setText("Percentage: "+result.getTotal());
        grade.setText("CGPA: "+result.getCgpa()+"("+result.getGrade()+")");
        workingdays.setText("Working Days: "+result.getWorking());
        attendance.setText("Attendance: "+result.getAttendance());
       rollno.setText("Student Id: "+result.getStid());
       stdname.setText("Roll No: "+result.getStrollno());
       classe.setText("Class: "+result.getStclass());
       section.setText("Section: "+result.getStsec());
        recyclerView=v.findViewById(R.id.resultrec);

        setrecycler();








      //  List<model.result> transactionList = (ArrayList<model.result>)getArguments().getSerializable("object");
//Log.d("ok",transactionList.toString());
//subblist=transactionList;

























        return v;
    }

    private void setrecycler() {
subblist=result.getmarks();


Log.d("hellofjkdfjdkfjkdf",subblist.toString());
recyclerView.setHasFixedSize(true);
recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
resultadapter=new resultadapter(getContext(), subblist);

recyclerView.setAdapter(resultadapter);






    }

    private List<model.result> listarry() {

List<model.result> sublist=new ArrayList<>();
        sublist.add(new model.result("English","40","30","35","Pass","A"));
        sublist.add(new model.result("English","40","30","35","Pass","B"));
        sublist.add(new model.result("English","40","30","35","Pass","V"));









        return sublist;



    }

}
