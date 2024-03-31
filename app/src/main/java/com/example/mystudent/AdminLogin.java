package com.example.mystudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLogin extends AppCompatActivity {


    Admin admin ;
    TextView UserNamefeild,PswdFeild,WarnLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        admin=new Admin();
        UserNamefeild = findViewById(R.id.AdminUserNameFeild);
        PswdFeild= findViewById(R.id.AdminPasswordFeild);
        WarnLabel = findViewById(R.id.AdminWarnLabel);

        Button loginbtn = findViewById(R.id.AdminLoginBtn);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference DBref = FirebaseDatabase.getInstance().getReference("Admin").child(UserNamefeild.getText().toString());
                DBref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            if(PswdFeild.getText().toString().equals(snapshot.child("Pswd").getValue(String.class))){
                                Toast.makeText(AdminLogin.this, "Login Sucessfully", Toast.LENGTH_SHORT).show();
                                admin.setName(snapshot.child("Name").getValue(String.class));
                                admin.setUserName(snapshot.child("UserName").getValue(String.class));
                                admin.setJoingYear(snapshot.child("JoiningYear").getValue(String.class));
                                admin.setGender(snapshot.child("Gender").getValue(String.class));
                                admin.setDeesignation(snapshot.child("Designation").getValue(String.class));
                                admin.setDOB(snapshot.child("DOB").getValue(String.class));
                                admin.setBloodGrup(snapshot.child("BloodGrup").getValue(String.class));
//                                Toast.makeText(AdminLogin.this, "Dataset", Toast.LENGTH_SHORT).show();
                                Intent gotAdminHome = new Intent(AdminLogin.this,AdminHome.class);
                                gotAdminHome.putExtra("Admin",  admin);
                                startActivity(gotAdminHome);
                                finish();
                            }else {
                                WarnLabel.setText("Invalid Password");
                                WarnLabel.setVisibility(View.VISIBLE);
                            }
                        }else {
                            WarnLabel.setText("Invalid Username");
                            WarnLabel.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}