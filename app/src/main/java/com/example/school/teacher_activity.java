package com.example.school;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class teacher_activity extends AppCompatActivity {

    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
private FirebaseAuth mauth;
private FirebaseUser muser;
private TextView emailte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_activity);
        androidx.appcompat.widget.Toolbar toolbar=findViewById(R.id.toolbar_teacher);
        setSupportActionBar(toolbar);
        navigationView=findViewById(R.id.teacher_menu);
        drawerLayout=findViewById(R.id.teacher_drawer);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
            mauth=FirebaseAuth.getInstance();
            muser=mauth.getCurrentUser();
        drawerLayout.addDrawerListener(toggle);
        emailte=findViewById(R.id.emailte);


        toggle.syncState();
        getSupportFragmentManager().beginTransaction().replace(R.id.teacher_framelayout,new teacher_home()).commit();
        navigationView.setCheckedItem(R.id.std_home);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            Fragment temp;
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.home:
                        Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_SHORT).show();
                        temp=new teacher_home();
                        getSupportActionBar().setTitle("Home");


                        break;
                    case R.id.add_notice:
                        Toast.makeText(getApplicationContext(),"Add Notice",Toast.LENGTH_SHORT).show();
                       //startActivity(new Intent(teacher_activity.this,addnotice.class));
                       //finish();
                        temp =new add_notice();
                        getSupportActionBar().setTitle("Add notice");

                        break;
                    case R.id.mine_notices:
                        Toast.makeText(getApplicationContext(),"My Notices",Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(teacher_activity.this,addnotice.class));
                        //finish();
                        temp =new my_notices();
                        getSupportActionBar().setTitle("Add notice");

                        break;

                    case R.id.attendancec:
                        Toast.makeText(getApplicationContext(),"Attendance",Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(teacher_activity.this,addnotice.class));
                        //finish();
                        temp =new attendance();
                        getSupportActionBar().setTitle("Attendance");

                        break;

                    case R.id.signout:


                        if(mauth!=null && muser!=null)
                        {
                            mauth.signOut();
                            startActivity(new Intent(getApplicationContext(), Main2Activity.class));
                            drawerLayout.closeDrawer(GravityCompat.START);
                            Toast.makeText(getApplicationContext(),"SUCCESSFULLY SIGNED OUT",Toast.LENGTH_SHORT).show();
                            finish();

                        }
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.teacher_framelayout,temp).commit();

                drawerLayout.closeDrawer(GravityCompat.START);


                return true;
            }
        });

    }

}
