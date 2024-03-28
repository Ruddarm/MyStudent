package com.example.mystudent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomePage extends AppCompatActivity {
    TextView Nameview,usernameview,batchview,classview,genderview,bloodgrupview,dobview,parentNameView,parentRelationView;
    TextView parentNumberView,parentemailView;
    stdDetails mystd;

    DatabaseReference dbr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        mystd= (stdDetails) getIntent().getParcelableExtra("StudentDetails",stdDetails.class);
        Nameview=findViewById(R.id.StudentName);
        usernameview = findViewById(R.id.UsernameFeild);
        batchview= findViewById(R.id.batchfeild);
        classview=findViewById(R.id.classfeild);
        genderview=findViewById(R.id.genderfeild);
        bloodgrupview=findViewById(R.id.bloodgrupfeild);
        dobview=findViewById(R.id.dobFeild);
        parentNameView=findViewById(R.id.guardainnamefeild);
        parentRelationView=findViewById(R.id.realtionFeild);
        parentNumberView=findViewById(R.id.ParentNumberFeild);
        parentemailView=findViewById(R.id.gaurdainemailFeild);
        Button bustrackbtn = findViewById(R.id.trackBtn);
        if(mystd!=null){
            Nameview.setText(mystd.getName());
            usernameview.setText(mystd.getUsername());
            batchview.setText(mystd.getStdbatch());
            classview.setText(mystd.getStdclass());
            genderview.setText(mystd.getGender());
            bloodgrupview.setText(mystd.getBloodgrup());
            dobview.setText(mystd.getDob());
            parentNameView.setText(mystd.getParentName());
            parentemailView.setText(mystd.getParentEmail());
            parentRelationView.setText(mystd.getParentRelation());
            parentNumberView.setText(mystd.getParentNumber());

        }


        bustrackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapIntent = new Intent(HomePage.this,stdbusmap.class);
                mapIntent.putExtra("bus",mystd.getBus());
                startActivity(mapIntent);
            }
        });

    }
}