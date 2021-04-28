package com.example.school;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.icu.util.ValueIterator;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.studentattendance;


public class attendance extends Fragment {
private EditText date;
private Spinner classe;
private Button addattendance;

private String classname;
private Spinner examtypspinner;
private String examtype;  public static Connection connection=null;
    static List<studentattendance> stdlist=new ArrayList<>();
private DatabaseReference db;
    public static String ip;
    public static String port ;
private String datt;

    public static String database;
    public static String username;
    public static String password;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_attendance, container, false);
        date=v.findViewById(R.id.selectdate);
        classe=v.findViewById(R.id.classspinner);
        addattendance=v.findViewById(R.id.addattendance);
examtypspinner=v.findViewById(R.id.examtypee);
        Calendar calendar=Calendar.getInstance();

        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH)+1;
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
        db= FirebaseDatabase.getInstance().getReference("db_info");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ip=snapshot.child("ip").getValue(String.class);
                port=snapshot.child("port").getValue(String.class);
                database=snapshot.child("db_name").getValue(String.class);
                username=snapshot.child("username").getValue(String.class);
                password=snapshot.child("password").getValue(String.class);

                Log.d("hi","jfdkf");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        date.setText(day+"/"+month+"/"+year);

        ArrayAdapter adapter=new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,classes.classes());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classe.setAdapter(adapter);
        if(!stdlist.isEmpty()){
            resetlist();
        }

        ArrayAdapter adaptere=new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,classes.examtype());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        examtypspinner.setAdapter(adaptere);


        classe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                classname=adapterView.getItemAtPosition(i).toString();


                //   Log.d("onchange",noticedb.toString());
                // Toast.makeText(getContext(),noticedb.toString(),Toast.LENGTH_SHORT).show();
            }



            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                classname=adapterView.getItemAtPosition(0).toString();
            }

        });

        examtypspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                examtype=adapterView.getItemAtPosition(i).toString();


                //   Log.d("onchange",noticedb.toString());
                // Toast.makeText(getContext(),noticedb.toString(),Toast.LENGTH_SHORT).show();
            }



            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                examtype=adapterView.getItemAtPosition(0).toString();
            }

        });

        datt=String.valueOf(day)+String.valueOf(month)+String.valueOf(year);

      date.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              DatePickerDialog  mdiDialog =new DatePickerDialog(getContext(),new DatePickerDialog.OnDateSetListener() {
                  @Override
                  public void onDateSet(DatePicker view, int years, int monthOfYears, int dayOfMonths) {
                //      Toast.makeText(getContext(),year+ " "+monthOfYear+" "+dayOfMonth,Toast.LENGTH_LONG).show();
                     int  mon=monthOfYears+1;
                        date.setText(dayOfMonths+"/"+mon+"/"+years);
                        datt=String.valueOf(dayOfMonths)+String.valueOf(mon)+String.valueOf(years);

                  }
              }, year, month, day);
              mdiDialog.show();

          }

      });

      addattendance.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              Bundle bundle = new Bundle();
              bundle.putString("date",date.getText().toString());
              bundle.putString("class",classname);
              bundle.putString("class",examtype);
              setconnection(datt);
              doattendance fragment2 = new doattendance();
              fragment2.setArguments(bundle);

            //  Toast.makeText(getContext(),"doing",Toast.LENGTH_LONG).show();
              getFragmentManager()
                      .beginTransaction()
                      .replace(R.id.teacher_framelayout, fragment2)
                      .commit();


         //     Toast.makeText(getContext(),date.getText()+classname,Toast.LENGTH_LONG).show();




          }
      });























        return v;
    }  private void setconnection(String dat){
        connectionclass connectionclass=new connectionclass();
        String dattt=date.getText().toString();
        Toast.makeText(getContext(),"ello"+ip+port+database+username+password,Toast.LENGTH_LONG).show();
        connection=connectionclass.connect(ip,port,database,username,password);
        if(connection!=null){

            Statement statement=null;
          //  Toast.makeText(getContext(),"Success",Toast.LENGTH_LONG).show();

            try {
               String hii="d"+dat;
               Toast.makeText(getContext(),hii,Toast.LENGTH_LONG).show();
                statement = connection.createStatement();
             //   statement.executeUpdate("alter table attendance2 add hello3 varchar(1) ");
//                int i=statement.executeUpdate("alter table  [SCH_DevdahaHS76].[dbo].[attendance2] add "+hii+" varchar(10)");
                ResultSet resultSet = statement.executeQuery("select * from attendance2 where Class='"+"07"+"'order by Rollno;");


                while (resultSet.next()){
                    //sublist.add(new model.result("English","40","30","35","Pass","A"));
                    //  sublist.add(new model.result("Nepali","40","30","35","Pass","B"));
                    //  sublist.add(new model.result("English","40","30","35","Pass","V"));
                    stdlist.add(new model.studentattendance(resultSet.getString("Name"),resultSet.getString("Rollno"),
                            resultSet.getString("StudentId"),resultSet.getString("Class")));

                     /*  Map<String,String> dtname=new HashMap<String, String>();
                       dtname.put("Studentname",resultSet.getString(2));
                       dtname.put("Gender",resultSet.getString(3));
                       data.add(dtname);*/
                    Log.d("sagar",resultSet.getString(1));

                }



                  Toast.makeText(getContext(),"hello done",Toast.LENGTH_LONG).show();

                connection.close();



            } catch (SQLException e) {
                e.printStackTrace();
            }








        }
    }


    public static List<model.studentattendance> getStdlist(){
        return stdlist;
    }
    public static void  resetlist(){
        stdlist.clear();
    }


}
