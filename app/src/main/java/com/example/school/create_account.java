package com.example.school;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class create_account extends AppCompatActivity {
    private EditText mfirstname;
    private EditText mlastname;
    private EditText memail;
    private EditText mpassword;
    private EditText mrollno;

    private Button msignup;
    private FirebaseAuth mauth;
    private FirebaseDatabase mdatabase;
    private DatabaseReference db;
    private ImageButton passwordimage;
    private String tokens;
    private DatabaseReference tdb;
    private TextView already_account;
private CheckBox areyoustudent;
private Spinner spinner;
private String classee;
    String[] classe= {  "Class Nursery", "Class LKG", "Class UKG", "Class 1","Class 2","Class 3","Class 4",
            "Class 5 ","Class 6","Class 7","Class 8","Class 9","Class 10"};
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        mfirstname=findViewById(R.id.firstname);
        mlastname=findViewById(R.id.lastname);
        memail=findViewById(R.id.email);
        mpassword=findViewById(R.id.password);
        mrollno=findViewById(R.id.schoolid);
        areyoustudent=findViewById(R.id.checkBox);
        msignup=findViewById(R.id.button);
        passwordimage=findViewById(R.id.imageButton);
        mdatabase=FirebaseDatabase.getInstance();
        tdb=FirebaseDatabase.getInstance().getReference("fm-tokens");
        already_account=findViewById(R.id.textView8);

spinner=findViewById(R.id.spinner2);
        ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,classe);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                classee=adapterView.getItemAtPosition(i).toString();


                //   Log.d("onchange",noticedb.toString());
                // Toast.makeText(getContext(),noticedb.toString(),Toast.LENGTH_SHORT).show();
            }



            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                classee=adapterView.getItemAtPosition(0).toString();
            }

        });
        mauth=FirebaseAuth.getInstance();
        // FirebaseUser muser=mauth.getCurrentUser().getT


        msignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validate()) {
                    createnewuser();
                }
                else
                {
                    onfailure();
                }

            }
        });


        already_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),login.class));
                finish();
            }
        });
        passwordimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enter=mpassword.getText().toString();
                mpassword.setInputType(1 );
            }
        });



      /*  FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if(task.isSuccessful())
                {
                    token=task.getResult().getToken().toString();
                    //  Log.d("tikken",token);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"YOUR DEVICE HAS NOOT BEEN REGISTERED",Toast.LENGTH_SHORT).show();

            }
        });*/







    }
    public void onfailure()
    {
        Toast.makeText(getBaseContext(), "Please fill the fields properly", Toast.LENGTH_LONG).show();

        already_account.setEnabled(true);
    }

    private void createnewuser() {

        final String fname=mfirstname.getText().toString().trim();
        final String lname=mlastname.getText().toString().trim();
        final String email=memail.getText().toString().trim();
        final String password=mpassword.getText().toString().trim();

        final String tollno=mrollno.getText().toString().trim();


        if(!TextUtils.isEmpty(fname) && !TextUtils.isEmpty(lname) && areyoustudent.isChecked()&&  !TextUtils.isEmpty(email)  && !TextUtils.isEmpty(password)&& !TextUtils.isEmpty(tollno) && !TextUtils.isEmpty(classee))
        {
            final ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("CREATING ACCOUNT");
            progressDialog.setTitle("SETTING TO DATABASE");
            progressDialog.show();

            mauth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    if(authResult!=null)
                    {
                        //tdb.child(token).child("device").setValue("active");


                        String userid=mauth.getCurrentUser().getUid();
                        db=mdatabase.getReference("students").child(userid);

                        db.child("fname").setValue(fname);
                        db.child("last_name").setValue(lname);
                        db.child("email").setValue(email);

                        db.child("password").setValue(password);
                        db.child("roll_no").setValue(tollno);
                        db.child("isStudent").setValue("true");
                        db.child("class").setValue(classee);




                        mfirstname.setText("");
                        mlastname.setText("");
                        memail.setText("");
                        mrollno.setText("");
                        mpassword.setText("");



                        Toast.makeText(getApplicationContext(),"ACCOUNT CREATED SUCCESSFULLY",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        startActivity(new Intent(create_account.this,login.class));

                    }
                    else
                    {


                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    mfirstname.setText("");
                    mlastname.setText("");
                    memail.setText("");
                    mrollno.setText("");
                    mpassword.setText("");

                    Toast.makeText(getApplicationContext(),"PLEASE FILL THE FIELDS PROPERLY",Toast.LENGTH_SHORT).show();
                }
            });



        }







    }

    public boolean validate() {
        boolean valid = true;

        String firstname = mfirstname.getText().toString();
        String lastname = mlastname.getText().toString();
        String password = mpassword.getText().toString();
        String email=memail.getText().toString();
        String rollno=mrollno.getText().toString();

if(!areyoustudent.isChecked()){
    areyoustudent.setError("Please Check It");
    valid=false;
}
else {
    areyoustudent.setError(null);
}

        if (firstname.isEmpty() || firstname.length() < 3) {
            mfirstname.setError("at least 3 characters");
            valid = false;
        } else {
            mfirstname.setError(null);
        }

        if (lastname.isEmpty() || lastname.length() < 3) {
            mlastname.setError("at least 3 characters");
            valid = false;
        } else {
            mlastname.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            memail.setError("enter a valid email address");
            valid = false;
        } else {
            memail.setError(null);
        }

        if (password.isEmpty() || password.length() < 6 || password.length() > 15) {
            mpassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            mpassword.setError(null);
        }

        if (rollno.isEmpty() ) {
            mrollno.setError("enter valid rollno");
            valid = false;
        } else {
            mrollno.setError(null);
        }




        return valid;
    }
}
