package com.ribal.hoteladmin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class DaftarTiket extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TiketAdapter adapter;
    private List<Tiket> tiketList;
    private ProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout;
    private FirebaseFirestore db;
    FirebaseAuth fAuth;
    private String userId;
    private String email;
    private TextView pesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_tiket);

        progressBar = findViewById(R.id.progressbar);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout = findViewById(R.id.swipeContainer);
        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        pesan = findViewById(R.id.tv_pemesanan);
        db = FirebaseFirestore.getInstance();

        final User user = (User) getIntent().getSerializableExtra("data");
        if (user != null) {
            email = user.getEmail();
            datafirebase(email);

        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                email = user.getEmail();
                datafirebase(email);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void datafirebase(String email){
        tiketList = new ArrayList<>();
        adapter = new TiketAdapter(this, tiketList);

        recyclerView.setAdapter(adapter);

        db.collection("pesan").document(email).collection("pemesanan").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        progressBar.setVisibility(View.GONE);

                        if(!queryDocumentSnapshots.isEmpty()){

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for(DocumentSnapshot d : list){

                                Tiket tk = d.toObject(Tiket.class);
                                tiketList.add(tk);

                            }

                            adapter.notifyDataSetChanged();

                        }else{
                            pesan.setVisibility(View.VISIBLE);
                        }


                    }
                });
    }
    public static Intent getActIntent(Activity activity) {
        // kode untuk pengambilan Intent
        return new Intent(activity, DaftarTiket.class);
    }
}