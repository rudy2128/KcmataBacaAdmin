package com.kacamata.kacamataplusminus.ui.testimony;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
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
import com.kacamata.kacamataplusminus.entity.News;
import com.kacamata.kacamataplusminus.entity.Test;

import java.util.Objects;

public class InputTestActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText edtDesc, edtTestId;
    private ImageView imgTest;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference reference;
    private StorageTask mUploadTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_test);
        imgTest = findViewById(R.id.img_test);
        edtDesc = findViewById(R.id.edt_description);
        edtTestId = findViewById(R.id.edt_testId);
        Button btnUpload = findViewById(R.id.btn_upload);
        Button btnChooseImage = findViewById(R.id.btn_choose_file);

        mStorageRef = FirebaseStorage.getInstance().getReference("test");
        reference = FirebaseDatabase.getInstance().getReference("Kacamata");

        btnChooseImage.setOnClickListener(v -> {
            openFileChooser();
        });

        btnUpload.setOnClickListener(v -> {
            uploadFile();
        });


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
                        String testId = edtTestId.getText().toString();
                        String description = edtDesc.getText().toString();
                        String imageUrl = downloadUrl.toString();

                        Test test = new Test(testId,imageUrl,description);
                        test.setTestId(testId);
                        test.setImageUrl(imageUrl);
                        test.setDescription(description);
                        reference.child("test").child(testId).setValue(test);
                        Toast.makeText(getApplicationContext(), "Upload successful", Toast.LENGTH_SHORT).show();
                        edtDesc.setText("");
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
                    .into(imgTest);

        }
    }
}