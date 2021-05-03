package com.kacamata.kacamataplusminus.ui.home;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
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
import com.kacamata.kacamataplusminus.entity.Product;

public class HomeUpdateActivity extends AppCompatActivity {
    DatabaseReference reference;
    EditText edtTitle,edtPrice,edtDesc;
    TextView tvProId;
    String proId,title,price,description,imageUrl;
    FirebaseAuth mAuth;
    ImageView imgPro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_update);
        Button btnUpdate = findViewById(R.id.btn_update);
        tvProId = findViewById(R.id.tv_proId);
        edtTitle = findViewById(R.id.edt_title);
        edtPrice = findViewById(R.id.edt_price);
        edtDesc = findViewById(R.id.edt_description);
        imgPro = findViewById(R.id.img_pro);

        reference = FirebaseDatabase.getInstance().getReference("Kacamata");
        proId = getIntent().getStringExtra("tvProId");
        title = getIntent().getStringExtra("tvTitle");
        price = getIntent().getStringExtra("tvPrice");
        description = getIntent().getStringExtra("tvDesc");
        mAuth = FirebaseAuth.getInstance();
        imageUrl = getIntent().getStringExtra("imgPro");
        Glide.with(this)
                .load(imageUrl)
                .override(200,200)
                .fitCenter()
                .into(imgPro);

        tvProId.setText(proId);
        edtTitle.setText(title);
        edtPrice.setText(price);
        edtDesc.setText(description);

        btnUpdate.setOnClickListener(v -> {
            updateProduct();
        });


    }

    private void updateProduct() {
        proId = tvProId.getText().toString();
        title = edtTitle.getText().toString();
        price = edtPrice.getText().toString();
        description = edtDesc.getText().toString();

        if (title.isEmpty()){
            edtTitle.setError(getString(R.string.empty));
        }else if (price.isEmpty()){
            edtPrice.setError(getString(R.string.empty));
        }else if (description.isEmpty()){
            edtDesc.setError(getString(R.string.empty));
        }else {
            reference = FirebaseDatabase.getInstance().getReference("Kacamata");
            Product product = new Product();
            product.setProId(proId);
            product.setTitle(title);
            product.setPrice(price);
            product.setDescription(description);

            reference.child("product").child(proId).child("title").setValue(title);
            reference.child("product").child(proId).child("price").setValue(price);
            reference.child("product").child(proId).child("description").setValue(description);

            Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_SHORT).show();
            finish();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.del_menu, menu);
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
                    reference.child("product").child(proId).removeValue();
                    finish();
                })
                .setNegativeButton(getString(R.string.no), (dialog, id) -> dialog.cancel());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


    }
}