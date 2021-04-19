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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import model.college_notice;


public class my_notices extends Fragment {

private Spinner inputclass;
    String[] classe= { "All", "Class Nursery", "Class LKG", "Class UKG", "Class 1","Class 2","Class 3","Class 4",
            "Class 5 ","Class 6","Class 7","Class 8","Class 9","Class 10"};
    private DatabaseReference db;
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
private FirebaseAuth mauth;
private FirebaseUser muser;
private String m_Text;
private TextView textView;
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Title");

// I'm using fragment here so I'm using getView() to provide ViewGroup
// but you can provide here any other instance of ViewGroup from your Fragment / Activity
        View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.input_class_name, (ViewGroup) getView(), false);
// Set up the input
        inputclass =  viewInflated.findViewById(R.id.input_class);

        ArrayAdapter adapter=new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,classe);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputclass.setAdapter(adapter);


        inputclass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                m_Text=adapterView.getItemAtPosition(i).toString();


                //   Log.d("onchange",noticedb.toString());
                // Toast.makeText(getContext(),noticedb.toString(),Toast.LENGTH_SHORT).show();
            }



            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                m_Text=adapterView.getItemAtPosition(0).toString();
            }

        });

// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        builder.setView(viewInflated);
        final View v= inflater.inflate(R.layout.fragment_my_notices, container, false);
// Set up the buttons
        textView=v.findViewById(R.id.my_notices_not_found);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();


                recyclerView=v.findViewById(R.id.mine_notices_recycler);
                db= FirebaseDatabase.getInstance().getReference("notices").child(m_Text);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                final List<college_notice> noticlist  =new ArrayList<>();
                progressDialog=new ProgressDialog(getContext());
                progressDialog.setTitle("");
                progressDialog.setMessage("Loading");
                progressDialog.show();
                mauth=FirebaseAuth.getInstance();
                muser=mauth.getCurrentUser();
                Toast.makeText(getContext(),muser.getUid(),Toast.LENGTH_SHORT).show();
                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for(DataSnapshot conte:snapshot.getChildren()){
                                if(conte.exists()) {

                                    model.college_notice colnot = conte.getValue(model.college_notice.class);
                                    if(colnot.getFrom().equals(muser.getUid().toString())){
                                        noticlist.add(colnot);
                                    }
                                }
                                else
                                {
                                    Toast.makeText(getContext(),"sorry",Toast.LENGTH_SHORT).show();
                                }}
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
                        {




                            textView.setVisibility(View.VISIBLE);
                            textView.setText("You have not post any notices");
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

















            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
      return v;

    }
}
