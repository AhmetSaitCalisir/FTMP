package com.girift.Ftmp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    //FireBase Bağlantıları
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //Ekran İtemleri
    EditText TextIsim;
    EditText TextSifre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FireBase Bağlatıları
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Kullanicilar");

        //Ekran İtemleri
        TextIsim = (EditText) findViewById(R.id.editText_Isim);
        TextSifre = (EditText) findViewById(R.id.editText_Sifre);


    }

    public void GirisYap(View view) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    if ((snapshot.getValue(Kullanici.class).getKullaniciAd().equals(TextIsim.getText().toString())) && (snapshot.getValue(Kullanici.class).getKullaniciSifre().equals(TextSifre.getText().toString())))
                    {
                        Intent Gecis = new Intent(MainActivity.this,MapsActivity.class);
                        Gecis.putExtra("Biz_ID",snapshot.getValue(Kullanici.class).getKullaniciID());
                        startActivity(Gecis);
                    }
                    else
                    {}
                }
                TextIsim.setText("");
                TextSifre.setText("");
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });
    }
}