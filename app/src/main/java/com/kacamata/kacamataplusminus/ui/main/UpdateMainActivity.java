package com.kacamata.kacamataplusminus.ui.main;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kacamata.kacamataplusminus.R;
import com.kacamata.kacamataplusminus.entity.News;

public class UpdateMainActivity extends AppCompatActivity {
    EditText edtDesc,edtTitle;
    TextView tvProId;
    ImageView imgMain;
    String imageUrl,proId,description,title;
    DatabaseReference reference;
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_main);
        edtTitle = findViewById(R.id.edt_title);
        edtDesc = findViewById(R.id.edt_description);
        tvProId = findViewById(R.id.tv_proId);
        imgMain = findViewById(R.id.img_main);
        Button btnUpdate = findViewById(R.id.btn_update);

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Kacamata");

        proId = getIntent().getStringExtra("tvId");
        title = getIntent().getStringExtra("tvTitle");
        description = getIntent().getStringExtra("tvDesc");
        imageUrl = getIntent().getStringExtra("imgMain");
        Glide.with(this)
                .load(imageUrl)
                .override(200,200)
                .into(imgMain);

        tvProId.setText(proId);
        edtTitle.setText(title);
        edtDesc.setText(description);

        btnUpdate.setOnClickListener(v -> {
            proId = tvProId.getText().toString();
            title = edtTitle.getText().toString();
            description = edtDesc.getText().toString();

            if (description.isEmpty()){
                edtDesc.setError(getString(R.string.empty));
            }else if(title.isEmpty()) {
                edtTitle.setError(getString(R.string.empty));
            }else {

                News news = new News();
                news.setTitle(title);
                news.setDescription(description);
                reference.child("news").child(proId).child("description").setValue(title);
                reference.child("news").child(proId).child("description").setValue(description);
                Toast.makeText(this,"Updated",Toast.LENGTH_SHORT).show();
                finish();


            }

        });


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
                    reference.child("news").child(proId).removeValue();

                    finish();
                })
                .setNegativeButton(getString(R.string.no), (dialog, id) -> dialog.cancel());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
    }
