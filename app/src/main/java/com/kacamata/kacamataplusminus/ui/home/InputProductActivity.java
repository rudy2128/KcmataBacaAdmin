package com.kacamata.kacamataplusminus.ui.home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.kacamata.kacamataplusminus.R;
import com.kacamata.kacamataplusminus.entity.Product;

import java.util.Objects;

public class InputProductActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    EditText edtProId, edtTitle, edtPrice,edtDesc;
    private ImageView imgPro;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference reference;
    private  StorageTask mUploadTask;
    String proId,title,imageUrl,price,description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_product);
        edtProId = findViewById(R.id.edt_proId);
        edtTitle = findViewById(R.id.edt_title);
        edtPrice = findViewById(R.id.edt_price);
        edtDesc = findViewById(R.id.edt_description);
        imgPro = findViewById(R.id.img_pro);
        Button btnChoose = findViewById(R.id.btn_choose_file);
        Button btnUpload = findViewById(R.id.btn_upload);

        mStorageRef = FirebaseStorage.getInstance().getReference("products");
        reference = FirebaseDatabase.getInstance().getReference("Kacamata");

        btnChoose.setOnClickListener(v -> openFileChooser());

        btnUpload.setOnClickListener(v -> uploadFile());


    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadFile() {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));
            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!urlTask.isSuccessful()) ;
                        Uri downloadUrl = urlTask.getResult();
                        assert downloadUrl != null;
                        proId = edtProId.getText().toString();
                        title = edtTitle.getText().toString();
                        price = edtPrice.getText().toString();
                        description = edtDesc.getText().toString();
                        imageUrl = downloadUrl.toString();


                        Product product = new Product(proId,imageUrl,title,price,description);
                        product.setProId(proId);
                        product.setTitle(title);
                        product.setPrice(price);
                        product.setDescription(description);
                        product.setImageUrl(imageUrl);

                        reference.child("product").child(proId).setValue(product);
                        Toast.makeText(getApplicationContext(), "Upload successful", Toast.LENGTH_SHORT).show();
                        edtTitle.setText("");
                        finish();

                    });


        }

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Glide.with(this)
                    .load(mImageUri)
                    .into(imgPro);

        }
    }

}