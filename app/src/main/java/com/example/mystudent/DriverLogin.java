package com.example.mystudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DriverLogin extends AppCompatActivity {
    private Button login ;
    EditText DriverId,pswd;

    DatabaseReference dfr;
    busDetails mybus;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);
        login=findViewById(R.id.DriverLoginBtn);
        DriverId=findViewById(R.id.driverIdfeild);
        pswd=findViewById(R.id.driverPswdFeild);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = DriverId.getText().toString();
                String Dripswd = pswd.getText().toString();
                dfr=FirebaseDatabase.getInstance().getReference("BusDriver").child(userName);
                dfr.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                                if(Dripswd.equals(snapshot.child("Dripswd").getValue(String.class))) {
                                    mybus=new busDetails();
                                    mybus.setDriverID(userName);
                                    mybus.setDriverNumber(snapshot.child("DriverNumber").getValue(String.class));
                                    mybus.setDriverName(snapshot.child("DriverName").getValue(String.class));
                                    mybus.setBusNumber(snapshot.child("Bus").getValue(String.class));
                                    Toast.makeText(DriverLogin.this, mybus.getDriverName()+" and "+mybus.getBusNumber(), Toast.LENGTH_SHORT).show();
                                    Intent driverMapintent = new Intent(DriverLogin.this,driverMapact.class);
                                    driverMapintent.putExtra("Bus",mybus);
                                    startActivity(driverMapintent);
                                }else {
                                    Toast.makeText(DriverLogin.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                                }

                        }else {
                            Toast.makeText(DriverLogin.this, "Invalid DriverID", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(DriverLogin.this, "Invalid DriverID", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
}