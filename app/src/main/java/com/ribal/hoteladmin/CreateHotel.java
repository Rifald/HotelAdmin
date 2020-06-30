package com.ribal.hoteladmin;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class CreateHotel extends AppCompatActivity {
    private Button simpanBtn;
    ImageView imgH;

    public Uri imguri;
    private Button upBtn;
    private EditText namaH;
    private EditText alamatH;
    private TextView urltv;
    private EditText hargaH;
    private EditText jumlahH;
    private FirebaseFirestore db;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_hotel);

        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("hotel");

        simpanBtn = findViewById(R.id.simpan_btn);
        upBtn = findViewById(R.id.up_btn);
        namaH = findViewById(R.id.nama_htl);
        alamatH = findViewById(R.id.alamat_htl);
        hargaH = findViewById(R.id.harga_htl);
        jumlahH = findViewById(R.id.jmlh_htl);


        imgH = findViewById(R.id.img_htl);


        upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileChooser();
            }


        });

        simpanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nama = namaH.getText().toString().trim();
                String alamat = alamatH.getText().toString().trim();
                String hrg = hargaH.getText().toString().trim();
                String jlh = jumlahH.getText().toString().trim();


                if (!validateInputs(nama, alamat, hrg, jlh)) {
                    final ProgressDialog progressDialog = new ProgressDialog(CreateHotel.this);
                    final StorageReference Ref = storageReference.child(namaH.getText()+"/" + "Profile"+"."+ getExtension(imguri));
                    Ref.putFile(imguri).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.setMessage("Creatingg...");
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
                                    String imageurl = downloadUrl.toString();
                                    Map<String, Object> file = new HashMap<>();
                                    file.put("url", downloadUrl.toString());
                                    file.put("nama",namaH.getText().toString());
                                    file.put("jumlah",Integer.parseInt(String.valueOf(jumlahH.getText())));
                                    file.put("harga",Integer.parseInt(String.valueOf(hargaH.getText())));
                                    file.put("alamat",alamatH.getText().toString());
                                    db.collection("hotel").document(""+namaH.getText())
                                            .set(file)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(CreateHotel.this, "Hotel Ditambahkan", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                }
                                            });


                                }
                            });
                            // Get a URL to the uploaded content
                            // Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle unsuccessful uploads
                                    // ...
                                }
                            });


                }
            }



            private boolean validateInputs(String nama, String alamat, String hrg, String jlh) {
                if (nama.isEmpty()) {
                    namaH.setError("Nama Harus Diisi!");
                    namaH.requestFocus();
                    return true;
                }

                if (alamat.isEmpty()) {
                    alamatH.setError("Alamat Harus Diisi!");
                    alamatH.requestFocus();
                    return true;
                }

                if (hrg.isEmpty()) {
                    hargaH.setError("Harga Harus Diisi!");
                    hargaH.requestFocus();
                    return true;
                }

                if (jlh.isEmpty()) {
                    jumlahH.setError("Jumlah Kamar Harus Diisi!");
                    jumlahH.requestFocus();
                    return true;
                }
                return false;
            }

        });

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
        }
    }

}
