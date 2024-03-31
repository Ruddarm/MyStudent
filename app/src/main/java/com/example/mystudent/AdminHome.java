package com.example.mystudent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AdminHome extends AppCompatActivity {

    Admin admindata;
    TextView AdminName,AdminUserName,Desgination,JoininYear,DOB,Gender,Bloodgrup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        FloatingActionButton addStd = findViewById(R.id.AddStuentFAB);
        AdminName = findViewById(R.id.AdminName);
        AdminUserName = findViewById(R.id.AdminUserName);
        Desgination= findViewById(R.id.DesignaitonFeild);
        JoininYear= findViewById(R.id.JoiningYearFeild);
        DOB=findViewById(R.id.AdminDOBFeild);
        Gender=findViewById(R.id.AdminGender);
        Bloodgrup=findViewById(R.id.AdminBloodGrup);

        admindata = (Admin) this.getIntent().getParcelableExtra("Admin", Admin.class);
        if(admindata!=null){
            AdminName.setText(admindata.getName());
            AdminUserName.setText(admindata.getUserName());
            Desgination.setText(admindata.getDeesignation());
            JoininYear.setText(admindata.getJoingYear());
            DOB.setText(admindata.getDOB());
            Gender.setText(admindata.getGender());
            Bloodgrup.setText(admindata.getBloodGrup());
        }
        addStd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AddStudent  = new Intent(AdminHome.this,addStudentGeneralDetials.class);
                startActivity(AddStudent);
            }
        });
    }
}