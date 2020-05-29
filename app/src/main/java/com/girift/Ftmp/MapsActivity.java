package com.girift.Ftmp;

import androidx.fragment.app.FragmentActivity;


import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String IDBizim;
    private String IDGelen;
    private String EnlemB;
    private String BoylamB;
    private String EnlemG;
    private String BoylamG;
    private double BoylamO;
    private double EnlemO;
    private LatLng Biz;
    private LatLng Gelen;
    private LatLng Orta;
    private MarkerOptions BizM;
    private MarkerOptions GelenM;
    private MarkerOptions OrtaM;
    private Marker Bizz;
    private Marker Gelenn;
    private Marker Ortaa;
    private boolean Bul;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        Biz= new LatLng(0,0);
        //DENEME
        IDBizim="1";
        IDGelen="2";
        Bul = false;
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                EnlemB = dataSnapshot.child("Kullanicilar").child(IDBizim).getValue(Kullanici.class).getKullaniciEnlem();
                BoylamB = dataSnapshot.child("Kullanicilar").child(IDBizim).getValue(Kullanici.class).getKullaniciBoylam();
                EnlemG = dataSnapshot.child("Kullanicilar").child(IDGelen).getValue(Kullanici.class).getKullaniciEnlem();
                BoylamG = dataSnapshot.child("Kullanicilar").child(IDGelen).getValue(Kullanici.class).getKullaniciBoylam();
                EnlemO = (Double.parseDouble(EnlemB)+Double.parseDouble(EnlemG))/2;
                BoylamO = (Double.parseDouble(BoylamB)+Double.parseDouble(BoylamG))/2;
                Biz = new LatLng(Double.parseDouble(EnlemB),Double.parseDouble(BoylamB));
                Gelen = new LatLng(Double.parseDouble(EnlemG),Double.parseDouble(BoylamG));
                Orta = new LatLng(EnlemO,BoylamO);
                Bizz.remove();
                BizM.position(Biz);
                Bizz =  mMap.addMarker(BizM);
                if (Bul)
                {
                    Gelenn.remove();
                    Ortaa.remove();
                    GelenM.position(Gelen);
                    OrtaM.position(Orta);
                    Gelenn = mMap.addMarker(GelenM);
                    Ortaa = mMap.addMarker(OrtaM);
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        BizM = new MarkerOptions().position(Biz).title("Siz");
        Bizz =  mMap.addMarker(BizM);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Biz));

    }

    public void Bul(View view) {

        GelenM = new MarkerOptions().position(Gelen).title("Buluşulacak Kişi");
        OrtaM = new  MarkerOptions().position(Orta).title("Orta Nokta");
        Gelenn = mMap.addMarker(GelenM);
        Ortaa = mMap.addMarker(OrtaM);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Orta));
        Bul=true;
    }
}
