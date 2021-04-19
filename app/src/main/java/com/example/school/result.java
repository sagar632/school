package com.example.school;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class result extends Fragment {
private Button result;

private TextView rollno;
private Spinner spinner;
private String examtype,roll;
private String[] classes;
private DatabaseReference db;
private FirebaseAuth mauth;
private FirebaseUser muser;
View v;
private static String stid,stclass,stsec,strollno,total,cgpa,grade,working,attendance,examty;

    static List<model.result> sublist=new ArrayList<>();
private String idd;
    private Connection connection = null;
    private ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         v= inflater.inflate(R.layout.fragment_result, container, false);
        classes= com.example.school.classes.examtype();
        rollno=v.findViewById(R.id.roll_number);
        spinner=v.findViewById(R.id.spinner);

        progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("");
        mauth=FirebaseAuth.getInstance();
        muser=mauth.getCurrentUser();
if(!sublist.isEmpty()){
    resetlist();
}

        db= FirebaseDatabase.getInstance().getReference("students");
        progressDialog.setMessage("Loading");
        progressDialog.show();
        final ArrayAdapter adapter=new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,classes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


db.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if(snapshot.exists()) {
           roll= snapshot.child(mauth.getCurrentUser().getUid()).child("roll_no").getValue(String.class);
            Toast.makeText(getContext(),roll,Toast.LENGTH_LONG).show();
rollno.setText("Student Id: "+roll);
        }
        else{
            Toast.makeText(getContext(),"Please Try Again",Toast.LENGTH_LONG).show();

        }


    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
        Toast.makeText(getContext(),"Please Try Again",Toast.LENGTH_LONG).show();
    }
});



progressDialog.dismiss();

        result=v.findViewById(R.id.resultbutton);




spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        examtype=adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
examtype=adapterView.getItemAtPosition(0).toString();
    }
});



        result.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
       // text.setText(examtype);
        switch(examtype){


            case "First Terminal Examination-2076":
                idd="1";
                break;
            case "Second Terminal Examination-2076":
                idd="2";
                break;
            case "Third Terminal Examination-2076":
                idd="3";
                break;
            case "Annual Examination 2076":
                idd="4";
                break;

        }
        sqlButton(v);
      //  Bundle bundle=new Bundle();
      //  bundle.putSerializable("object",  sublist);

selectexamtype selectexamtype=new selectexamtype();
//selectexamtype.setArguments(bundle);
getFragmentManager().beginTransaction().replace(R.id.student_framelayout,selectexamtype).commit();

    }
});











        return v;
    }




    public void sqlButton(View view){
        ArrayList<Map<String, String>> data = new ArrayList<Map<String, String>>();
        connectionclass connectionclass=new connectionclass();
        connection=connectionclass.connect();


        if (connection!=null){
            Statement statement = null;
            Toast.makeText(getContext(),"Succes",Toast.LENGTH_LONG).show();
            try { char c1v='1';
                statement = connection.createStatement();
                //String query="select * from testing where c1 like "+'''c1v '+";";
                ResultSet resultSet = statement.executeQuery("SELECT * FROM MarkSheetGrade2  left Join MarkSheetGrade1 on" +
                        " MarkSheetGrade2.PRNO=MarkSheetGrade1.PRNO " +
                        "where MarkSheetGrade2.StudentId='"+roll+"' and MarkSheetGrade2.EXAMTYPE='"+examtype+"';");

                   while (resultSet.next()){
                       //sublist.add(new model.result("English","40","30","35","Pass","A"));
                     //  sublist.add(new model.result("Nepali","40","30","35","Pass","B"));
                     //  sublist.add(new model.result("English","40","30","35","Pass","V"));
sublist.add(new model.result(resultSet.getString("SUBJECT"),resultSet.getString("FULLMARKS"),resultSet.getString("PASSMARKS"),resultSet.getString("OBTMARKS"),"Pass",resultSet.getString("GRADE1")));

                     /*  Map<String,String> dtname=new HashMap<String, String>();
                       dtname.put("Studentname",resultSet.getString(2));
                       dtname.put("Gender",resultSet.getString(3));
                       data.add(dtname);*/
                       Log.d("sagar",resultSet.getString(1));
                       stid=resultSet.getString("StudentId");
                       stclass=resultSet.getString("CLASS");
                       stsec=resultSet.getString("Section");
                       strollno=resultSet.getString("RollNo");
                       total=resultSet.getString("PERCENTAGE");
                       working=resultSet.getString("WORKINGDAYS");
                       attendance=resultSet.getString("ATTENDANCE");
                       grade=resultSet.getString("DIVISION");
                       cgpa=resultSet.getString("RESULT");
                       examty=resultSet.getString("EXAMTYPE");
                   }



                 //  Toast.makeText(getContext(),"hello done",Toast.LENGTH_LONG).show();

                connection.close();



            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {

        }
    }
    public static List<model.result> getmarks(){
        return sublist;
    }
public static void  resetlist(){
  sublist.clear();
}
    public static String getStid() {
        return stid;
    }



    public static String getStclass() {
        return stclass;
    }




    public static String getExamty() {
        return examty;
    }

    public static String getStsec() {
        return stsec;
    }

    public void setStsec(String stsec) {
        this.stsec = stsec;
    }

    public static String getStrollno() {
        return strollno;
    }



    public static String getTotal() {
        return total;
    }



    public static String getGrade() {
        return grade;
    }



    public static String getWorking() {
        return working;
    }

    public static String getCgpa() {
        return cgpa;
    }





    public static String getAttendance() {
        return attendance;
    }


}
