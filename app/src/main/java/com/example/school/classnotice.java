package com.example.school;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import model.college_notice;


public class classnotice extends Fragment {

    private DatabaseReference db;
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
private FirebaseAuth mauht;
private String uid,classe;
private TextView textView;
private DatabaseReference cdb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mauht=FirebaseAuth.getInstance();
        uid=mauht.getCurrentUser().getUid();

        View v= inflater.inflate(R.layout.fragment_classnotice, container, false);
cdb=FirebaseDatabase.getInstance().getReference("students").child(uid);
textView=v.findViewById(R.id.notfoundtext);
        Bundle bundle = this.getArguments();
        String myValue = bundle.getString("message");
Toast.makeText(getContext(),myValue,Toast.LENGTH_LONG).show();

        recyclerView=v.findViewById(R.id.class_notice_recycler);
        db= FirebaseDatabase.getInstance().getReference("notices").child(myValue);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final List<college_notice> noticlist  =new ArrayList<>();
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("");
        progressDialog.setMessage("Loading");
        progressDialog.show();

        db.orderByChild("date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot conte:snapshot.getChildren()){
                        if(conte.exists()) {

                            model.college_notice colnot = conte.getValue(model.college_notice.class);
                            noticlist.add(colnot);
                        }
                        else
                        {
                            Toast.makeText(getContext(),"sorry",Toast.LENGTH_SHORT).show();
                        }}
                    progressDialog.dismiss();
                    recyclerView.setAdapter(new teacher_home_adapter(noticlist, new teacher_home_adapter.OnItemClickListener() {
                        @Override
                        public void OnItemClick(college_notice college_notice) {
                            Toast.makeText(getContext(),college_notice.getDate(),Toast.LENGTH_SHORT).
                                    show();
                            LayoutInflater inflater= LayoutInflater.from(getContext());
                            View view=inflater.inflate(R.layout.noticedetail_dialog, null);
                            TextView head=view.findViewById(R.id.notice_text);
                            ImageView impic=view.findViewById(R.id.notice_image);
                            head.setText(college_notice.getContent());
                            if(!college_notice.getImage().equals("no")){
                                impic.setVisibility(View.VISIBLE);

                                Picasso.with(getContext()).load(college_notice.getImage()).resize(150,150).into(impic);

                            }
                            else {
                                impic.setVisibility(View.GONE);
                            }

                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle(college_notice.getHeading());
                            builder.setView(view);

                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // dismiss dialog
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.show();


                        }
                    }));
                    progressDialog.dismiss();

                }
                else
                {textView.setVisibility(View.VISIBLE);
                textView.setText("There are no notices for your class");
                progressDialog.dismiss();

                    Toast.makeText(getContext(),"sorry not fetch",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                textView.setVisibility(View.VISIBLE);
                textView.setText("Please Check Your Internet Connection and try again");
                progressDialog.dismiss();
            }
        });





















        return v;
    }


}
