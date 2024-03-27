package com.example.mystudent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AdminHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        FloatingActionButton addStd = findViewById(R.id.AddStuentFAB);
        addStd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AddStudent  = new Intent(AdminHome.this,addStudentGeneralDetials.class);
                startActivity(AddStudent);
            }
        });
    }
}