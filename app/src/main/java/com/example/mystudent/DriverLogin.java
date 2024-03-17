package com.example.mystudent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DriverLogin extends AppCompatActivity {
    private Button login ;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);
        login=findViewById(R.id.DriverLoginBtn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent driverMapintent = new Intent(DriverLogin.this,driverMapact.class);
                startActivity(driverMapintent);
            }
        });

    }
}