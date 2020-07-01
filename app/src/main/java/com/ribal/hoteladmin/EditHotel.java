package com.ribal.hoteladmin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class EditHotel extends AppCompatActivity {
    private Button upBtn;
    private Button delBtn;
    private EditText namaH;
    private EditText alamatH;
    private EditText hargaH;
    private EditText jumlahH;
    private FirebaseFirestore db;
    StorageReference storageReference;
    private Button simpanBtn;
    ImageView imgH;
    public Uri imguri;
    boolean clicked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hotel);
        clicked = false;

        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("hotel");

        simpanBtn = findViewById(R.id.simpan_btn);
        delBtn = findViewById(R.id.delete_btn);
        namaH = findViewById(R.id.nama_htl);
        alamatH = findViewById(R.id.alamat_htl);
        hargaH = findViewById(R.id.harga_htl);
        jumlahH = findViewById(R.id.jmlh_htl);

        imgH = findViewById(R.id.img_htl);
        upBtn = findViewById(R.id.up_btn);


        final Hotel hotel = (Hotel) getIntent().getSerializableExtra("data");

        if (hotel != null) {
            final String namaS = hotel.getNama();
            namaH.setText(hotel.getNama());
            alamatH.setText(hotel.getAlamat());
            hargaH.setText(hotel.getHarga().toString());
            jumlahH.setText(hotel.getJumlah().toString());
            Picasso.get().load(hotel.getUrl()).fit().centerCrop().into(imgH);

            upBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fileChooser();

                }


            });

            simpanBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(clicked){

                        final StorageReference RefS = storageReference.child(hotel.getNama()+"/" + "Profile"+"."+ getExtension(imguri));
                        RefS.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                        final ProgressDialog progressDialog = new ProgressDialog(EditHotel.this);
                        final StorageReference Ref = storageReference.child(namaH.getText()+"/" + "Profile"+"."+ getExtension(imguri));
                        Ref.putFile(imguri).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                                progressDialog.setMessage("Updating...");
                                progressDialog.show();
                            }
                        })
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Uri downloadUrl = uri;
                                        hotel.setNama(namaH.getText().toString());
                                        hotel.setAlamat(alamatH.getText().toString());
                                        hotel.setHarga(Integer.parseInt(String.valueOf(hargaH.getText())));
                                        hotel.setJumlah(Integer.parseInt(String.valueOf(jumlahH.getText())));
                                        hotel.setUrl(downloadUrl.toString());


                                        /**
                                         * Baris kode yang digunakan untuk mengupdate data barang
                                         * yang sudah dimasukkan di Firebase
                                         */
                                        db.collection("hotel").document(namaS).delete();
                                        db.collection("hotel")
                                                .document(hotel.getNama())
                                                .set(hotel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                progressDialog.dismiss();
                                                Toast.makeText(EditHotel.this, "Hotel Updated", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        });

                                    }
                                });

                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle unsuccessful uploads
                                        // ...
                                    }
                                });
                    }else {
                        hotel.setNama(namaH.getText().toString());
                        hotel.setAlamat(alamatH.getText().toString());
                        hotel.setHarga(Integer.parseInt(String.valueOf(hargaH.getText())));
                        hotel.setJumlah(Integer.parseInt(String.valueOf(jumlahH.getText())));

                        /**
                         * Baris kode yang digunakan untuk mengupdate data barang
                         * yang sudah dimasukkan di Firebase
                         */
                        db.collection("hotel").document(namaS).delete();
                        db.collection("hotel")
                                .document(hotel.getNama())
                                .set(hotel).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(EditHotel.this, "Hotel Updated", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }

                }

            });

            delBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final StorageReference RefS = storageReference.child(hotel.getNama()+"/" + "Profile.jpg");
                    RefS.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });
                    db.collection("hotel")
                            .document(hotel.getNama())
                            .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(EditHotel.this, "Hotel Dihapus", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
            });
        }


    }
    public static Intent getActIntent(Activity activity) {
        // kode untuk pengambilan Intent
        return new Intent(activity, EditHotel.class);
    }
    private  String getExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }


    private void fileChooser() {
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    public void onActivityResult( int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK &&data!=null &&data.getData()!=null){
            imguri=data.getData();

            Picasso.get().load(imguri).fit().centerCrop().into(imgH);
            clicked = true;
        }
    }
}
