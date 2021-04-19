package com.example.school;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import model.user_info;

public class login extends AppCompatActivity {
    private EditText memail;
    private EditText password;
    private Button login;
    public FirebaseAuth mauth;
    private FirebaseAuth.AuthStateListener mauthlist;
    private FirebaseUser muser;
    private TextView register;
    private Button forgot;
    private ImageButton showpass;
private String uuid;
private String isss;
private  Boolean isstudent;
private DatabaseReference databaseReference;
    private user_info user_info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mauth=FirebaseAuth.getInstance();






        register=findViewById(R.id.textView5);

        memail=findViewById(R.id.email);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);

        forgot=findViewById(R.id.forgotpassword);
        showpass=findViewById(R.id.imageButton);
        showpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enter=password.getText().toString();
                password.setInputType(1 );
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),create_account.class));
                finish();
            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(validate()) {

                    String memailte = memail.getText().toString();
                    String mpasste = password.getText().toString();
                    login(memailte, mpasste);
                    Toast.makeText(login.this,"je;;p",Toast.LENGTH_SHORT).show();




                    //

                   // Toast.makeText(login.this,isStudent,Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please fill the fields properly",Toast.LENGTH_SHORT).show();

                }


            }

            private void login(String mail,String pass)
            {
                if(mail!=null && pass!=null)
                {final ProgressDialog progressDialog=new ProgressDialog(login.this);
                    progressDialog.setMessage("AUTHENTICATING...");
                    //progressDialog.setTitle("PLEASE WAIT");
                    progressDialog.show();

                    mauth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(
                            login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()) {
                                        muser=mauth.getCurrentUser();
                                        uuid=muser.getUid();
                                        Toast.makeText(login.this,
                                                "Successfully singned in",Toast.LENGTH_SHORT).show();
                                        databaseReference=FirebaseDatabase.getInstance().getReference("students");
                                        databaseReference.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                 isss = snapshot.child(uuid).child("isStudent").getValue(String.class);
                                                if(isss.equals("true")){

                                                    Intent intent=new Intent(login.this,Main4Activity.class);
                                                    intent.putExtra("email",muser.getEmail());
                                                    startActivity(intent); progressDialog.dismiss();
                                                }
                                                else {
                                                    Intent intent=new Intent(login.this,teacher_activity.class);
                                                    intent.putExtra("teacher_email",muser.getEmail());
                                                    startActivity(intent); progressDialog.dismiss();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                isss="hello";
                                            }
                                        });

                                       // Intent intent =new Intent(login.this,Main3Activity.class);
                                       // startActivity(intent);




                                    }
                                    else
                                    {
                                        Toast.makeText(login.this,"Sorry you are not registered",Toast.LENGTH_SHORT).show();
                                        memail.setText("");
                                        password.setText("");
                                        progressDialog.dismiss();

                                    }
                                }
                            }
                    );

                }


            }


        });

    }

    public boolean validate() {
        boolean valid = true;

        String email = memail.getText().toString();
        String passwordc =password.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            memail.setError("enter a valid email address");
            valid = false;
        } else {
            memail.setError(null);
        }

        if (passwordc.isEmpty() || passwordc.length() < 6 || passwordc.length() > 15) {
            password.setError("between 6 and 15 alphanumeric characters");
            valid = false;
        } else {
            password.setError(null);
        }

        return valid;
    }
}
