package com.example.school;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import model.studentattendance;

import static com.example.school.attendance.database;
import static com.example.school.attendance.ip;
import static com.example.school.attendance.password;
import static com.example.school.attendance.username;
import static com.example.school.attendance.port;
public class attendancerecycler extends RecyclerView.Adapter<attendancerecycler.Viewholder> {
private Statement statement=null;
private String statu="0";
    Context context;
    private Connection connectio;
    List<studentattendance> stdlist;

    public attendancerecycler(Context context, List<studentattendance> stdlist) {
        this.context = context;
        this.stdlist = stdlist;
    }
    public interface OnItemClickListener {
        void OnItemClick(model.studentattendance studentattendance);
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.attendancelayout,parent,false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder holder, int position) {
final model.studentattendance studentattendance=stdlist.get(position);
holder.name.setText(studentattendance.getName());
holder.rollno.setText("Roll No. "+studentattendance.getRollno());

holder.present.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        statu ="1";
        holder.present.setBackgroundResource(R.drawable.greenbutton);
        holder.absent.setBackgroundResource(R.color.red);
        try {
            connectionclass connectionclass=new connectionclass();




            connectio=connectionclass.connect(ip,port,database,username,password);
            statement
                    =connectio.createStatement();
            int iii=statement.executeUpdate("update [SCH_DevdahaHS76].[dbo].[attendance2] set d2842021 ='1' where StudentId='"+studentattendance.getStid()+"';");
            Log.d("helllowl jfjd","jfkdjfdfiereireireurieuri");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Toast.makeText(context,studentattendance.getName()+" "+"Present",Toast.LENGTH_LONG).show();
    }
});
holder.absent.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        statu="0";
holder.absent.setBackgroundResource(R.drawable.red_button);
holder.present.setBackgroundResource(R.color.green);
        try {
            connectionclass connectionclass=new connectionclass();




            connectio=connectionclass.connect(ip,port,database,username,password);
            statement
                    =connectio.createStatement();
            int iii=statement.executeUpdate("update [SCH_DevdahaHS76].[dbo].[attendance2] set d2842021 ='0' where StudentId='"+studentattendance.getStid()+"';");
            Log.d("helllowl jfjd","jfkdjfdfiereireireurieuri");

        } catch (SQLException e) {
            e.printStackTrace();
        }




        Toast.makeText(context,studentattendance.getName()+" "+"absent",Toast.LENGTH_LONG).show();
    }
});


    }

    @Override
    public int getItemCount() {
        return stdlist.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
         TextView name;
        TextView rollno;
  Button present,absent;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

          present=itemView.findViewById(R.id.present);
          absent=itemView
                  .findViewById(R.id.absent);



            name=itemView.findViewById(R.id.attendnamee);
            rollno=itemView.findViewById(R.id.attendroll);



        }


        public void bind(final model.studentattendance studentattendance, final attendancerecycler.OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnItemClick(studentattendance);
                }
            });
        }



    }


}
