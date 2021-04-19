package com.example.school;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


public class add_notice extends Fragment  {
    private EditText heading,text;
    private ImageButton noticeimage;
    private Button post; private static final int request = 2;
    private Spinner classes;  private Uri imageUri;
    String[] classe= { "All", "Class Nursery", "Class LKG", "Class UKG", "Class 1","Class 2","Class 3","Class 4",
            "Class 5 ","Class 6","Class 7","Class 8","Class 9","Class 10"};
    private DatabaseReference db;
    private FirebaseUser muser;
    private FirebaseAuth.AuthStateListener mauth;
    private ProgressDialog progressDialog;
    private DatabaseReference udb;
    private model.user_info user_info;
    private DatabaseReference noticedb;
    private String fullname;
    private String userid;
    private StorageReference storageReference;
    private String durl;
private String claf;
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_add_notice,container,false);

        heading=v.findViewById(R.id.heading);
        text=v.findViewById(R.id.notice_text);
        noticeimage=v.findViewById(R.id.notice_imagebutton);
        post=v.findViewById(R.id.post);



        classes=v.findViewById(R.id.spinner1);
noticedb=FirebaseDatabase.getInstance().getReference("notices");
        ArrayAdapter adapter=new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,classe);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classes.setAdapter(adapter);
       // classes.getSelectedItem().toString();
        muser=FirebaseAuth.getInstance().getCurrentUser();
        userid=muser.getUid();

        storageReference= FirebaseStorage.getInstance().getReference("notice/images");
       // db= FirebaseDatabase.getInstance().getReference("post").child(mpost.getText().toString()+ UUID.randomUUID().toString());


        noticeimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryintent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryintent.setType("image/*");
                startActivityForResult(galleryintent, request);

            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


if(imageUri==null){
   uploadintodatabasewithoutimage();
}



               else
                   uploadinttodatabase();

            }
        });







       // Toast.makeText(getContext(), classes.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
        classes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                claf=adapterView.getItemAtPosition(i).toString();


             //   Log.d("onchange",noticedb.toString());
              // Toast.makeText(getContext(),noticedb.toString(),Toast.LENGTH_SHORT).show();
            }



            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                 claf=adapterView.getItemAtPosition(0).toString();
            }

        });















        return  v;
    }

    private void uploadintodatabasewithoutimage() {
        if(!TextUtils.isEmpty(heading.getText().toString()) && !TextUtils.isEmpty(text.getText().toString())){

            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("Uploading");
            progressDialog.show();
            SimpleDateFormat simple=new SimpleDateFormat("HH:mm a");
            final String time=simple.format(new Date());

            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            final String datef=simpleDateFormat.format(new Date());

            model.college_notice collnot=new model.college_notice(heading.getText().toString(),text.getText().toString(),"no",datef+ " "+time,muser.getUid().toString());
            noticedb.child(claf).child(UUID.randomUUID().toString()).setValue(collnot).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    heading.setText("");
                    text.setText("");
                    classes.setId(0);
                    noticeimage.setImageResource(R.drawable.button_layout);
                    Toast.makeText(getContext(), "SUCCESSFULLY POSTED", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), teacher_activity.class));

                    progressDialog.dismiss();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure( Exception e) {
                    Toast.makeText(getContext(), "SORRY YOUR POST HAS NOT BEEN UPLOADED", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });







        }
        else {
            Toast.makeText(getContext(),"PLEASE FILL THE FIELDS PROPERLY",Toast.LENGTH_SHORT).show();

        }
    }



    private void uploadinttodatabase() {

        if(!TextUtils.isEmpty(heading.getText().toString()) && !TextUtils.isEmpty(text.getText().toString()) && !TextUtils.isEmpty(text.getText().toString())){

            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("Uploading");
            progressDialog.show();
            final StorageReference filepath=storageReference.child(muser.getUid()+"_"+UUID.randomUUID());
            final UploadTask upload=filepath.putFile(imageUri);
            upload.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> tasturl=upload.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then( Task<UploadTask.TaskSnapshot> task) throws Exception {
                            durl=filepath.getDownloadUrl().toString();
                            return filepath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete( final Task<Uri> task) {
                            // Calendar calendar= Calendar.getInstance();
                            // String datef= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());




                            SimpleDateFormat simple=new SimpleDateFormat("HH:mm a");
                            final String time=simple.format(new Date());

                            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                            final String datef=simpleDateFormat.format(new Date());

                            model.college_notice collnot=new model.college_notice(heading.getText().toString(),text.getText().toString(),task.getResult().toString(),datef,muser.getUid().toString());
                            noticedb.child(claf).child(UUID.randomUUID().toString()).setValue(collnot).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    heading.setText("");
                                    text.setText("");
                                    classes.setId(0);
                                    noticeimage.setImageResource(R.drawable.button_layout);
                                    Toast.makeText(getContext(), "SUCCESSFULLY POSTED", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getActivity(), teacher_activity.class));

                                    progressDialog.dismiss();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure( Exception e) {
                                    Toast.makeText(getContext(), "SORRY YOUR POST HAS NOT BEEN UPLOADED", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            });


                          /*  imadb.child(muser.getUid().toString()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange( DataSnapshot dataSnapshot) {
                                    model.profile_image profile_image=dataSnapshot.getValue(model.profile_image.class);
                                    final String proimurl=profile_image.getImageur();
                                    udb.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if(dataSnapshot.exists())
                                            {
                                                for(DataSnapshot conte:dataSnapshot.getChildren()){

                                                    user_info=dataSnapshot.getValue(model.user_info.class);
                                                    fullname=user_info.getFname()+" "+user_info.getLast_name();
                                                    Post po = new Post(datef,mpost.getText().toString(),task.getResult().toString(),time,fullname,muser.getUid().toString());
                                                    db.setValue(po).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {

                                                            progressDialog.dismiss();



                                                            mpost.setText("");
                                                            mimage.setImageResource(R.drawable.ic_image_black_250dp);
                                                            Toast.makeText(getContext(), "SUCCESSFULLY POSTED", Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent(getActivity(), Main3Activity.class));
                                                            progressDialog.dismiss();


                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure( Exception e) {
                                                            Toast.makeText(getContext(), "SORRY YOUR POST HAS NOT BEEN UPLOADED", Toast.LENGTH_SHORT).show();
                                                            progressDialog.dismiss();
                                                        }
                                                    });

                                                }
                                            }

                                        }

                                        @Override
                                        public void onCancelled( DatabaseError databaseError) {

                                        }
                                    });
                                }

                                @Override
                                public void onCancelled( DatabaseError databaseError) {

                                }
                            });*/


                        }






                    });










                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure( Exception e) {
                    Toast.makeText(getContext(), "SORRY YOUR POST HAS NOT BEEN UPLOADED", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }


        else {
            Toast.makeText(getContext(),"PLEASE FILL THE FIELDS PROPERLY",Toast.LENGTH_SHORT).show();

        }

















    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == request && resultCode ==RESULT_OK) {
            imageUri = data.getData();

            noticeimage.setImageURI(imageUri);



        }
        else{
          imageUri=null;
        }
    }


}
