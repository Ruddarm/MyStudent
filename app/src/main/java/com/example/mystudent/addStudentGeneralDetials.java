package com.example.mystudent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class addStudentGeneralDetials extends AppCompatActivity {

    Button GotPrsnlBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student_general_detials);
        setGotPrsnlBtn();

    }
    public void setGotPrsnlBtn(){
        GotPrsnlBtn=findViewById(R.id.Add_Student);
        GotPrsnlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}