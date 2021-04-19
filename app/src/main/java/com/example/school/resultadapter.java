package com.example.school;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class resultadapter extends RecyclerView.Adapter<resultadapter.viewhold> {
   Context context;
   List<model.result> sublist;

    public resultadapter(Context context, List<model.result> sublist) {
        this.context = context;
        this.sublist = sublist;
    }



    @NonNull
    @Override
    public viewhold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.result_table_format,parent,false);

        return new viewhold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewhold holder, int position) {
        model.result result=sublist.get(position);
        holder.subname.setText(result.getSubname());
        holder.fm.setText(result.getFm());
        holder.pm.setText(result.getPm());
        holder.om.setText(result.getOm());
        holder.remarks.setText(result.getRemarks());
        holder.grade.setText(result.getGrade());


    }

    @Override
    public int getItemCount() {
        return sublist.size();
    }


    public class viewhold extends RecyclerView.ViewHolder {
        TextView subname,fm,pm,om,remarks,grade;




        public viewhold(@NonNull View itemView) {
            super(itemView);
            subname=itemView.findViewById(R.id.subjectname);
            fm=itemView.findViewById(R.id.fullmarks);
            pm=itemView.findViewById(R.id.passmarks);
            om=itemView.findViewById(R.id.obtainmarks);
            remarks=itemView.findViewById(R.id.remarks);
            grade=itemView.findViewById(R.id.grad);







        }
    }
}
