package com.example.mystudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.mystudent.databinding.ActivityStdbusmapBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class stdbusmap extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityStdbusmapBinding binding;
    TextView BusNo,Drivename;
    private FloatingActionButton callbtn;
    private  busDetails stdbus;
    DatabaseReference drf;
    CircleOptions mpcrcl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStdbusmapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        stdbus =(busDetails) getIntent().getParcelableExtra("bus",busDetails.class);
        if(stdbus!=null){
            drf= FirebaseDatabase.getInstance().getReference("BusDriver").child(stdbus.getDriverID());
            drf.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        stdbus.setBusNumber(snapshot.child("Bus").getValue(String.class));
                        stdbus.setDriverName(snapshot.child("DriverName").getValue(String.class));
                        stdbus.setDriverNumber(snapshot.child("DriverNumber").getValue(String.class));
                        stdbus.setBusStatus(Boolean.TRUE.equals(snapshot.child("Status").getValue(Boolean.class)));
                        SetUserDetials();
                    }else{
                        Toast.makeText(stdbusmap.this, "Bus Not Found "+stdbus.getDriverNumber(), Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onCancelled( DatabaseError error) {
                    Toast.makeText(stdbusmap.this, "Eror in bus Database "+stdbus.getDriverNumber(), Toast.LENGTH_SHORT).show();

                }
            });




        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        try
        {
            mapFragment.getMapAsync(this);
        }catch (NullPointerException ex) {
            Toast.makeText(stdbusmap.this, "This map is null", Toast.LENGTH_SHORT).show();
        }

    }
    public void SetUserDetials(){
        BusNo = findViewById(R.id.BusNumberView);
        Drivename = findViewById(R.id.DriverName);
        BusNo.setText(stdbus.getBusNumber());
        Drivename.setText(stdbus.getDriverName());
        callbtn = findViewById(R.id.callactionbtn);
        callbtn.setOnClickListener(v -> {
            Intent callintent = new Intent(Intent.ACTION_DIAL);
            callintent.setData(Uri.parse("tel:"+stdbus.getDriverNumber()));
            startActivity(callintent);
        });

    }
    public  void GetLocation(){
        drf=FirebaseDatabase.getInstance().getReference("BusDriver").child(stdbus.getDriverID()).child("crnLoc");
        drf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    double lat = Double.parseDouble(Objects.requireNonNull(snapshot.child("lat").getValue(String.class)));
                    double lang = Double.parseDouble(Objects.requireNonNull(snapshot.child("lang").getValue(String.class)));
                    LatLng crnlocaiton = new LatLng(lat, lang);

                    if(mpcrcl==null){
                        mpcrcl = new CircleOptions()
                                .center(crnlocaiton)
                                .radius(20) // Radius in meters
                                .strokeWidth(2)
                                .strokeColor(0xFF0000FF) // Blue color
                                .fillColor(0x300000FF);
                        mMap.addCircle(mpcrcl);


                    }else {
                        mpcrcl.center(crnlocaiton);

                    }
                    // Semi-transparent blue
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(crnlocaiton, 18));

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Last loaction"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        mMap.animateCamera(CameraUpdateFactory.newLatLng(sydney));
//        mMap.animateCamera(CameraUpdateFactory.zoomBy(16));
        Toast.makeText(stdbusmap.this,"Map is Ready",Toast.LENGTH_SHORT).show();
        GetLocation();

    }
}