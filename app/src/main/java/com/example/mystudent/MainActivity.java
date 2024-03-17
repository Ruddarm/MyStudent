package com.example.mystudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    String EnterdUserName,Userpswd;
    stdDetails mystudent;
    EditText usernamefeild,pswdfeild;
    TextView WarnLabel;
    TextView DriveLogin;
    DatabaseReference mystdDBRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loginbtn = findViewById(R.id.LoginBtn);
        DriveLogin = findViewById(R.id.DriverLogView);
        mystudent=new stdDetails();
        WarnLabel=findViewById(R.id.loginErrroLabel);
        WarnLabel.setVisibility(View.INVISIBLE);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isValidUser();

            }
        });
        DriveLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent drivelogin= new Intent(MainActivity.this,DriverLogin.class);
                startActivity(drivelogin);
            }
        });
    }
    public void isValidUser(){
        usernamefeild= findViewById(R.id.StudentIDFeild);
        pswdfeild=findViewById(R.id.PasswordField);

        EnterdUserName= usernamefeild.getText().toString().trim();
        Userpswd=pswdfeild.getText().toString().trim();
        mystdDBRef= FirebaseDatabase.getInstance().getReference("Student").child(EnterdUserName);
        mystdDBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    DataSnapshot stdata= snapshot.child("personalDetials");
                    if(stdata.exists()){
                        if(Userpswd.equals(stdata.child("pswd").getValue(String.class)))
                        {
                            WarnLabel.setVisibility(View.INVISIBLE);
                            Toast.makeText(MainActivity.this,"Login Sucessfull",Toast.LENGTH_SHORT).show();
//                            Setting Personal Detials
                            mystudent.setName(stdata.child("Name").getValue(String.class));
                            mystudent.setStdclass(stdata.child("class").getValue(String.class));
                            mystudent.setStdbatch(stdata.child("Batch").getValue(String.class));
                            mystudent.setUsername(stdata.child("username").getValue(String.class));
                            mystudent.setDob(stdata.child("dob").getValue(String.class));
                            mystudent.setBloodgrup(stdata.child("bloodgrup").getValue(String.class));
                            mystudent.setGender(stdata.child("gender").getValue(String.class));
                            DataSnapshot prntdata= snapshot.child("GuardianDetials");
                            if(prntdata.exists()){
                                mystudent.setParentName(prntdata.child("gurdname").getValue(String.class));
                                mystudent.setParentEmail(prntdata.child("gurdemail").getValue(String.class));
                                mystudent.setParentNumber(prntdata.child("contactnumber").getValue(String.class));
                                mystudent.setParentRelation(prntdata.child("relation").getValue(String.class));
                            }
                            DataSnapshot busdata = snapshot.child("BusDetials");
                            if(busdata.exists()){
                                    boolean assign = Boolean.TRUE.equals(busdata.child("BusAssign").getValue(Boolean.class));
                                    mystudent.getBus().setAssign(assign);
                                    if(assign){
                                        mystudent.getBus().setDriverID(busdata.child("DriveID").getValue(String.class));
                                    }
                            }
                            Intent homepageIntent =new Intent(MainActivity.this, HomePage.class);
                            homepageIntent.putExtra("StudentDetails",mystudent);
                            startActivity(homepageIntent);
                            finish();

                        }else {
//                            Toast.makeText(MainActivity.this,"Invalid Details",Toast.LENGTH_SHORT).show();
                            WarnLabel.setText("Invalid password");
                            WarnLabel.setVisibility(View.VISIBLE);
                            return;

                        }

                    }else {
//                        Toast.makeText(MainActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                            WarnLabel.setText("Invalid Username");
                            WarnLabel.setVisibility(View.VISIBLE);
                    }
                }else {
                    Toast.makeText(MainActivity.this,"InValid UserName",Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this,"Error hogya bhai",Toast.LENGTH_SHORT).show();
            }
        });

    }

}