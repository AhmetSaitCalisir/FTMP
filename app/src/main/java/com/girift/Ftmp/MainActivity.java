package com.girift.Ftmp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bu kısımı deneme ve iki kullanıcı eklemek için yazdım
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Giriş Durumu");
        myRef.setValue("Çalışıyor");
        DatabaseReference databaseReference = database.getReference();
        Kullanici kullanici = new Kullanici("1","Nil","Guresci","38.099763","27.728836");
        databaseReference.child("Kullanicilar").child(kullanici.getKullaniciID()).setValue(kullanici);
        Kullanici kullanici1 = new Kullanici("2","Ahmet Sait","Calisir","38.505859","27.706685");
        databaseReference.child("Kullanicilar").child(kullanici1.getKullaniciID()).setValue(kullanici1);
    }

    public void GirisYap(View view) {
        Intent Gecis = new Intent(MainActivity.this,MapsActivity.class);
        startActivity(Gecis);
    }
}
