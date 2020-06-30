package com.ribal.hoteladmin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.app.AppCompatActivity;

public class TiketDetail extends AppCompatActivity {


    private TextView namaHotel;
    private TextView namaPesan;
    private TextView jumlahKamar;
    private TextView hargaTotal;
    private TextView tanggal;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiket_detail);
        db = FirebaseFirestore.getInstance();



        namaHotel = findViewById(R.id.tv_tiket_hotel);
        namaPesan = findViewById(R.id.tv_tiket_nama);
        hargaTotal = findViewById(R.id.tv_tiket_harga);
        jumlahKamar = findViewById(R.id.tv_tiket_jumlah);
        tanggal = findViewById(R.id.tv_tiket_tanggal);


        final Tiket tiket = (Tiket) getIntent().getSerializableExtra("data");
        if (tiket != null) {
            namaPesan.setText(tiket.getNama());
            namaHotel.setText(tiket.getHotel());
            hargaTotal.setText("Rp. "+tiket.getTotal());
            jumlahKamar.setText(tiket.getJumlah()+" Kamar");
            tanggal.setText(tiket.getTanggal());


        }

    }
    public static Intent getActIntent(Activity activity) {
        // kode untuk pengambilan Intent
        return new Intent(activity, TiketDetail.class);
    }
}