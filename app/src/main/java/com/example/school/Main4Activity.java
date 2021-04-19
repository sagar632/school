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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main4Activity extends AppCompatActivity {
NavigationView navigationView;
ActionBarDrawerToggle toggle;
DrawerLayout drawerLayout;
private FirebaseAuth mauth;
private FirebaseUser muser;
private TextView stemail;
private DatabaseReference db;
private String classee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
      androidx.appcompat.widget.Toolbar toolbar=findViewById(R.id.toolbar_std);
        setSupportActionBar(toolbar);
navigationView=findViewById(R.id.navmenu);
drawerLayout=findViewById(R.id.student_drawer);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
mauth=FirebaseAuth.getInstance();
muser=mauth.getCurrentUser();
stemail=findViewById(R.id.stemaiilte);
db= FirebaseDatabase.getInstance().getReference("students");

Toast.makeText(getApplicationContext(),muser.getEmail(),Toast.LENGTH_LONG).show();

db.child(muser.getUid()).addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        classee=snapshot.child("class").getValue(String.class);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});
drawerLayout.addDrawerListener(toggle);
toggle.syncState();
        getSupportFragmentManager().beginTransaction().replace(R.id.student_framelayout,new studnet_home()).commit();
navigationView.setCheckedItem(R.id.std_home);
navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
   Fragment temp;
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
       switch (menuItem.getItemId())
       {
           case R.id.std_home:
               Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_SHORT).show();
               temp=new studnet_home();
                getSupportActionBar().setTitle("Home");


               break;
           case R.id.read_college_notice:
               Toast.makeText(getApplicationContext(),"College Notice",Toast.LENGTH_SHORT).show();
               getSupportActionBar().setTitle("College Notice");
               temp=new studnet_home();

               break;


           case R.id.read_class_notice:
               Bundle bundle = new Bundle();
               String myMessage = classee;
               bundle.putString("message", myMessage );
              // Toast.makeText(getApplicationContext(),"Class Notice",Toast.LENGTH_SHORT).show();
               getSupportActionBar().setTitle("Class Notice");
               temp=new classnotice();
               temp.setArguments(bundle);

               break;



           case R.id.grad:
               Toast.makeText(getApplicationContext(),"Attendance",Toast.LENGTH_SHORT).show();
               getSupportActionBar().setTitle("Attendance");
               drawerLayout.closeDrawer(GravityCompat.START);
               temp=new selectexamtype();
               break;

           case R.id.result:
               Toast.makeText(getApplicationContext(),"Result",Toast.LENGTH_SHORT).show();
               drawerLayout.closeDrawer(GravityCompat.START);
               getSupportActionBar().setTitle("Result");
               temp=new result();
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
        getSupportFragmentManager().beginTransaction().replace(R.id.student_framelayout,temp).commit();

        drawerLayout.closeDrawer(GravityCompat.START);


        return true;
    }
});

    }
}
