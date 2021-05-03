package com.kacamata.kacamataplusminus.ui.testimony;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kacamata.kacamataplusminus.R;
import com.kacamata.kacamataplusminus.entity.Test;

public class TestUpdateActivity extends AppCompatActivity {
    DatabaseReference reference;
    String testId,description,imageUrl;
    ImageView imgTest;
    EditText edtDescription;
    TextView tvTestid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_update);
        edtDescription = findViewById(R.id.edt_description);
        imgTest = findViewById(R.id.img_test);
        Button btnUpdate = findViewById(R.id.btn_update);
        tvTestid = findViewById(R.id.tv_testId);

        testId = getIntent().getStringExtra("tvTestId");
        description = getIntent().getStringExtra("tvDesc");
        imageUrl = getIntent().getStringExtra("imgTest");
        Glide.with(this)
                .load(imageUrl)
                .override(200,200)
                .into(imgTest);

        tvTestid.setText(testId);
        edtDescription.setText(description);


        btnUpdate.setOnClickListener(v -> {
            updateTestimony();
        });


    }

    private void updateTestimony() {
        testId = tvTestid.getText().toString();
        description = edtDescription.getText().toString();

        if (description.isEmpty()){
            edtDescription.setError(getString(R.string.empty));
        }else {
            Test test = new Test();
            test.setTestId(testId);
            test.setDescription(description);
            reference = FirebaseDatabase.getInstance().getReference("Kacamata");
            reference.child("test").child(testId).child("description").setValue(description);
            Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_SHORT).show();
            finish();

        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.del_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            showAlertDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAlertDialog() {
        String dialogTitle, dialogMessage;
        dialogMessage = getString(R.string.message_delete);
        dialogTitle = getString(R.string.delete);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), (dialog, id) -> {
                    reference = FirebaseDatabase.getInstance().getReference("Kacamata");
                    reference.child("test").child(testId).removeValue();
                    finish();
                })
                .setNegativeButton(getString(R.string.no), (dialog, id) -> dialog.cancel());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    }
