package com.example.school;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import model.college_notice;

public  class teacher_home_adapter extends RecyclerView.Adapter<teacher_home_adapter.holder> {
    private List<model.college_notice> noticeList;
    private OnItemClickListener listener;




    public teacher_home_adapter(List<model.college_notice> noticeliss,OnItemClickListener listener) {
        this.noticeList = noticeliss;
        this.listener=listener;
    }

    public interface OnItemClickListener {
        void OnItemClick(model.college_notice college_notice);
    }





    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_layout,parent,false);

        return new holder(view,listener);

    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
            String date=noticeList.get(position).getDate();
            String title=noticeList.get(position).getHeading();
            String content=noticeList.get(position).getContent();
            String image=noticeList.get(position).getImage();


            holder.bind(noticeList.get(position),listener);
            holder.setData(date,title,content,image);
            
    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }


    class holder extends RecyclerView.ViewHolder{
private TextView dat;
private TextView heading;
private TextView conten;

        public holder(View itemview, final OnItemClickListener listener){
            super(itemview);
            dat=itemview.findViewById(R.id.textView11);
            heading=itemview.findViewById(R.id.textView12);
            conten=itemview.findViewById(R.id.textView13);




        }


        public void bind(final college_notice college_notice, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnItemClick(college_notice);
                }
            });
        }

        public void setData(String date, String title, String content, String image) {
            dat.setText(date);
            heading.setText(title);
            conten.setText(content);

        }
    }
}
