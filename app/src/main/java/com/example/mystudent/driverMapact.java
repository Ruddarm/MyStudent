package com.example.mystudent;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.mystudent.databinding.ActivityDriverMapactBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class driverMapact extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public static int reqcode = 100;
    private double lat, lang;
    private LocationManager driverlocmangere;
    FusedLocationProviderClient lpc;
    FloatingActionButton startActionbtn;
    Boolean isstart=false;
     DatabaseReference dfr;
     Task<Void> UpdateLoc;
    crnloc DriverLocation;
    busDetails mybus;
    private LocationListener driverloclistener;
    private ActivityDriverMapactBinding binding;
//    private LocationManager

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDriverMapactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        driverlocmangere = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        CheckLocPermsssion();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        lpc = LocationServices.getFusedLocationProviderClient(this);
        DriverLocation= new crnloc();
        mybus= (busDetails) getIntent().getParcelableExtra("Bus",busDetails.class);
        if(mybus!=null){
            dfr= FirebaseDatabase.getInstance().getReference("BusDriver").child(mybus.getDriverID());
        }
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
        if (mMap != null) {
            GetDriverLocation();
        } else {
            return;
        }
        startActionbtn=findViewById(R.id.StartAction);
        startActionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isstart){
                    isstart=true;
                    startActionbtn.setBackgroundColor(Color.RED);
                    updateCurrntLoc();
                    Toast.makeText(driverMapact.this, "Start", Toast.LENGTH_SHORT).show();
                }else{
                    isstart=false;
                    startActionbtn.setBackgroundColor(Color.GREEN);
                    driverlocmangere.removeUpdates(driverloclistener);
                    Toast.makeText(driverMapact.this, "End", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Add a marker in Sydney and move the camera
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }

    public void CheckLocPermsssion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

        } else {
            getPermession();
        }
    }

    public void GetDriverLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = lpc.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    lat=location.getLatitude();
                    lang=location.getLongitude();
                    LatLng sydney = new LatLng(lat, lang);
                    Toast.makeText(driverMapact.this, "stuck "+sydney.latitude, Toast.LENGTH_SHORT).show();
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16));
                    mMap.addMarker(new MarkerOptions().position(sydney).title("You are here"));
                    crnloc loc = new crnloc();
                    Map<String, Object> locupdate = new HashMap<>();
                    locupdate.put("lat", ""+lat);
                    locupdate.put("lang", ""+lang);
                    UpdateLoc=FirebaseDatabase.getInstance().getReference("BusDriver").child(mybus.getDriverID()).child("crnLoc").updateChildren(locupdate);

                }
            }
        });

    }
//
//
    public  void updateCurrntLoc(){
        driverloclistener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
              lat=location.getLatitude();
              lang=location.getLongitude();
              Toast.makeText(driverMapact.this, "Lat  is "+lat, Toast.LENGTH_SHORT).show();
              crnloc loc = new crnloc();
              Map<String, Object> locupdate = new HashMap<>();
              locupdate.put("lat", ""+lat);
              locupdate.put("lang", ""+lang);
              UpdateLoc=FirebaseDatabase.getInstance().getReference("BusDriver").child(mybus.getDriverID()).child("crnLoc").updateChildren(locupdate);
        }

    };

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==reqcode){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Location Enable", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Location is not Enable", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public  void getPermession(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},reqcode);

    }

}