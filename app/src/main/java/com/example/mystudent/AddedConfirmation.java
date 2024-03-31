package com.example.mystudent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddedConfirmation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added_confirmation);
        String userName = this.getIntent().getParcelableExtra("UserName",String.class);
        String pswd = this.getIntent().getParcelableExtra("Pswd", String.class);
        TextView Uname = findViewById(R.id.UserNameView);
        TextView Pswd = findViewById(R.id.PswdView);
        Uname.setText("Username :  "+userName);
        Pswd.setText("Password : "+pswd);
        Button Gotohome = findViewById(R.id.GotoHomeBtn);
        Gotohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}