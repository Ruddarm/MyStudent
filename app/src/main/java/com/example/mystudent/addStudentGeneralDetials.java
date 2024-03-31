package com.example.mystudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class addStudentGeneralDetials extends AppCompatActivity {

    Button GotPrsnlBtn;
    Spinner DriverList;
    stdDetails addstd;
    ArrayAdapter<String> adpater;
    Boolean UserNameOK,PswdOk;
    CheckBox isBusAssignBox;
    DatabaseReference UserNameDB;
    boolean isValid = false, isselect =false;
    TextView stdName,stdBatch,stdUserName,stdCreatedPswd,stdCnfPswd,stdClass,stdDOB,stdBloodGrup,stdGender,userNameWarn,DriverIDwarn,PswdWarn;
    TextView GurdainName,GaurdianNumber,GaurdianRelation,GaurdianEmail;
    Button addnewStd ;


     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student_general_detials);
        addstd = new stdDetails();
        DriverList =findViewById(R.id.DriverIDOption);
        DriverList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addstd.getBus().setDriverID(""+parent.getItemAtPosition(position));
                DriverIDwarn.setVisibility(View.INVISIBLE);
                isselect=true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                DriverIDwarn.setVisibility(View.VISIBLE);
                isselect=false;

            }
        });

        stdName = findViewById(R.id.StdNameFeild);
        stdBatch = findViewById(R.id.BatchFeild);
        stdClass = findViewById(R.id.ClassFeild);
        stdUserName = findViewById(R.id.stdUserNameFeild);
        stdCreatedPswd =findViewById(R.id.stdCreatedPswd);
        stdCnfPswd = findViewById(R.id.stdCnfPswd);
        stdDOB = findViewById(R.id.StdDOBFeild);
        stdGender = findViewById(R.id.StdGenderFeild);
        stdBloodGrup = findViewById(R.id.BloodFeild);
        GurdainName = findViewById(R.id.GuardNameFeild);
        GaurdianRelation = findViewById(R.id.GaurdRelationFeild);
        GaurdianEmail = findViewById(R.id.GaurdEmailFeild);
        GaurdianNumber = findViewById(R.id.GaurdContactFeild);
        DriverIDwarn = findViewById(R.id.SelectDriverLabel);
        userNameWarn = findViewById(R.id.UserNameWarnLabel);
        PswdWarn= findViewById(R.id.PasswordNotMatchLabel);
        isBusAssignBox = findViewById(R.id.busAssignBtn);
        addnewStd= findViewById(R.id.Add_Student);
        isBusAssignBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked){
//                   Toast.makeText(addStudentGeneralDetials.this, "Checked", Toast.LENGTH_SHORT).show();
                   addstd.getBus().setAssign(true);
                   DriverList.setVisibility(View.VISIBLE);
               }else {
                   DriverList.setVisibility(View.INVISIBLE);
                   addstd.getBus().setAssign(false);
               }
            }
        });

         AddDriverList();
         CorrectPsdw();
         setKeyListenerInUserName();
         addnewStd.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
//                     Toast.makeText(addStudentGeneralDetials.this, "Bhai Click hua", Toast.LENGTH_SHORT).show();
                     AddStudentToDB();
             }
         });

    }

    public  void AddDriverList(){
        List<String> DriverIDlist = new ArrayList<>();
//        Toast.makeText(addStudentGeneralDetials.this, "  bidu", Toast.LENGTH_SHORT).show();

        DriverIDlist.add("SELECT");

        DatabaseReference DriverDBref = FirebaseDatabase.getInstance().getReference("BusDriver");

        DriverDBref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                for (DataSnapshot child:snapshot.getChildren()) {
                    DriverIDlist.add(child.getKey());

                }

                adpater=new ArrayAdapter<>(addStudentGeneralDetials.this, android.R.layout.simple_spinner_item, DriverIDlist);
                adpater.setDropDownViewResource(R.layout.sppinerfont);
//                DriverList.setDropDownViewResource();
                DriverList.setAdapter(adpater);
            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });


    }

    public  void  AddStudentToDB(){
            if(UserNameOK&&PswdOk){
                    SetStdDetails();
                Toast.makeText(this, "Everytihin OK", Toast.LENGTH_SHORT).show();
//                Map<String, Object> locupdate = new HashMap<>();
//                locupdate.put("lat", ""+lat);
//                locupdate.put("lang", ""+lang);
                if(addstd.getBus().isAssign()&&!isselect){
                    DriverIDwarn.setText("Select Driver ID");
                    DriverIDwarn.setVisibility(View.VISIBLE);
//                    Toast.makeText(this, "SAHI THA BAHI GADBAD HOGAY", Toast.LENGTH_SHORT).show();
                }else {
                    DatabaseReference newStdDB= FirebaseDatabase.getInstance().getReference("Student");
                    Map<String,Object> stdData= new HashMap<>();
                    stdData.put("Name",addstd.getName());
                    stdData.put("username",addstd.getUsername());
                    stdData.put("pswd",addstd.getPswd());
                    stdData.put("Batch",addstd.getStdbatch());
                    stdData.put("class",addstd.getStdclass());
                    stdData.put("gender",addstd.getGender());
                    stdData.put("bloodgrup",addstd.getBloodgrup());
                    stdData.put("dob",addstd.getPswd());
                    newStdDB.child(addstd.getUsername()).child("personalDetials").updateChildren(stdData);
                    Map<String, Object> stdGaurdData = new HashMap<>();
                    stdGaurdData.put("gurdname",addstd.getParentName());
                    stdGaurdData.put("relation",addstd.getParentRelation());
                    stdGaurdData.put("gurdemail",addstd.getParentEmail());
                    stdGaurdData.put("contactnumber",addstd.getParentNumber());
                    newStdDB.child(addstd.getUsername()).child("GuardianDetials").updateChildren(stdGaurdData);

                    Map<String,Object> BusData = new HashMap<>();
                    if(addstd.getBus().isAssign()){
                        BusData.put("BusAssign",true);
                        BusData.put("DriveID",addstd.getBus().getDriverID());
                    }else {
                        BusData.put("BusAssign",false);
                        BusData.put("DriveID","");
                    }
                    newStdDB.child(addstd.getUsername()).child("BusDetials").updateChildren(BusData);

                    isValid=true;
                    Intent gotcnfScreenf =new Intent(addStudentGeneralDetials.this,AddedConfirmation.class);
                    gotcnfScreenf.putExtra("UserName",addstd.getUsername());
                    gotcnfScreenf.putExtra("Pswd",addstd.getPswd());
                    startActivity(gotcnfScreenf);
                    finish();
                }
            }
    }
    public  void SetStdDetails(){
        addstd.setName(stdName.getText().toString());
        addstd.setUsername(stdUserName.getText().toString());
        addstd.setPswd(stdCnfPswd.getText().toString());
        addstd.setStdclass(stdClass.getText().toString());
        addstd.setStdbatch(stdBatch.getText().toString());
        addstd.setDob(stdDOB.getText().toString());
        addstd.setGender(stdGender.getText().toString());
        addstd.setBloodgrup(stdBloodGrup.getText().toString());
        addstd.setParentName(GurdainName.getText().toString());
        addstd.setParentRelation(GaurdianRelation.getText().toString());
        addstd.setParentNumber(GaurdianNumber.getText().toString());
        addstd.setParentEmail(GaurdianEmail.getText().toString());
    }


    public boolean CorrectPsdw( ){
         stdCnfPswd.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

             }

             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {


                    if(s.toString().equals(stdCreatedPswd.getText().toString())){
                        PswdWarn.setVisibility(View.INVISIBLE);
                        PswdOk=true;

                    }else {
                        PswdWarn.setVisibility(View.VISIBLE);
                        PswdWarn.setText("Password did't match");
                        PswdOk=false;
                    }

             }

             @Override
             public void afterTextChanged(Editable s) {

             }
         });

         return  true;
    }
    public void setKeyListenerInUserName() {

        stdUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                UserNameDB = FirebaseDatabase.getInstance().getReference("Student").child(stdUserName.getText().toString().toLowerCase().trim());
                UserNameDB.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.exists()) {
                            userNameWarn.setText("UserName Available");
                            UserNameOK = true;
                        } else {
                            userNameWarn.setText("UserName not Available");
                            UserNameOK = false;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

}