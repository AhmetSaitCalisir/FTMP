package com.girift.Ftmp;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    //Ekran İtemleri
    TextView Text_Gelen;

    //Layout
    LinearLayout Buttonlar;

    //Harita konumu
    Boolean Ben;
    Boolean Orta;
    Boolean Diger;
    float Mesafe;

    //Harita İçin
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;

    //DataBase İçin
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //İd'ler
    String ID_Biz;
    String ID_Gelen;

    //Konumlar
    LatLng Konum_Biz;
    LatLng Konum_Gelen;
    LatLng Konum_Orta;

    //Markerlar
    Marker Marker_Biz;
    Marker Marker_Gelen;
    Marker Marker_Orta;

    //Zamanlayıcı
    CountDownTimer timer;
    long kalanSure;

    //Arabulucu İçin
    Boolean Ariyor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //Ekran itemleri
        Text_Gelen = (TextView) findViewById(R.id.textView);

        //Layout
        Buttonlar = (LinearLayout) findViewById(R.id.Butonlar);

        //Harita Konumu
        Ben=true;
        Mesafe = 10;

        //Fake IDler
        ID_Biz="1";

        //DataBase İçin
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        //Harita İçin
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        client = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(MapsActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            getCurrentLocation();
        }
        else {
            ActivityCompat.requestPermissions(MapsActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }

        //Zamanlayıcı
        final TextView textView = (TextView) findViewById(R.id.textView);
        kalanSure=1000;
        timer = new CountDownTimer(kalanSure,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //textView.setText(Long.toString(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                getCurrentLocation();
                timer.start();
            }
        }.start();

        //Arabulucu İçin
        Ariyor=false;

    }

    private void getCurrentLocation() {
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                if (location != null)
                {
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            if (Marker_Biz!=null){Marker_Biz.remove();}
                                Konum_Biz = new LatLng(location.getLatitude(),location.getLongitude());
                                MarkerOptions Options_Biz = new MarkerOptions().position(Konum_Biz).title("Buradasınız");
                                Marker_Biz = googleMap.addMarker(Options_Biz);
                                databaseReference.child("Kullanicilar").child(ID_Biz).child("kullaniciBoylam").setValue(Double.toString(location.getLongitude()));
                                databaseReference.child("Kullanicilar").child(ID_Biz).child("kullaniciEnlem").setValue(Double.toString(location.getLatitude()));
                                if (Ariyor && Konum_Gelen!=null){
                                    if (Marker_Gelen != null){Marker_Gelen.remove();}
                                    if (Marker_Orta !=null){Marker_Orta.remove();}
                                    Konum_Orta = new LatLng((Konum_Biz.latitude+Konum_Gelen.latitude)/2,(Konum_Biz.longitude+Konum_Gelen.longitude)/2);
                                    MarkerOptions Options_Gelen = new MarkerOptions().position(Konum_Gelen).title("Buluşacağınız kişi");
                                    MarkerOptions Options_Orta = new MarkerOptions().position(Konum_Orta).title("Orta Nokta");
                                    Marker_Gelen = googleMap.addMarker(Options_Gelen);
                                    Marker_Orta = googleMap.addMarker(Options_Orta);
                            }
                            if (Ben) { googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Konum_Biz,Mesafe)); }
                            else if (Orta) {googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Konum_Orta,Mesafe));}
                            else if (Diger){googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Konum_Gelen,Mesafe));}
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 44){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {}

    public void Bul(View view) {
        ID_Gelen=Text_Gelen.getText().toString();
        Ariyor = true;
        Buttonlar.setVisibility(View.VISIBLE);
        Ben = false;
        Orta = true;
        Diger = false;
        Mesafe = 50;
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Konum_Gelen = new LatLng(Double.parseDouble(dataSnapshot.child("Kullanicilar").child(ID_Gelen).getValue(Kullanici.class).getKullaniciEnlem()),Double.parseDouble(dataSnapshot.child("Kullanicilar").child(ID_Gelen).getValue(Kullanici.class).getKullaniciBoylam()));
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    public void Ben(View view) {
        Ben=true;
        Orta = false;
        Diger=false;
    }

    public void Orta(View view) {
        Ben = false;
        Orta = true;
        Diger = false;
    }


    public void Diger(View view) {
        Ben = false;
        Orta = false;
        Diger = true;
    }

    public void Yakinlas(View view) {
        if (Mesafe < 100){
            Mesafe+=5;
        }
    }

    public void Uzaklas(View view) {
        if (Mesafe > 5){
            Mesafe-=5;
        }
    }
}