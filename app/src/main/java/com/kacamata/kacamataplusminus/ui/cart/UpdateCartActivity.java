package com.kacamata.kacamataplusminus.ui.cart;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kacamata.kacamataplusminus.R;
import com.kacamata.kacamataplusminus.entity.Order;
import com.kacamata.kacamataplusminus.helper.RupiahHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UpdateCartActivity extends AppCompatActivity {
    private FirebaseRecyclerOptions<Order> options;
    private FirebaseRecyclerAdapter<Order, OrderHolder> adapter;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    EditText edtDiscount, edtCash;
    Button btnDiscount, btnChanges;
    TextView tvPhone, tvName, tvAddress, tvPay, tvChanges;
    String phone, name, address, discount, total;
    double changes = 0;
    double total_value = 0;
    double percent = 100;

    List<Order> orderList = new ArrayList<>();
    private TextView tvTotal;

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cart);
        tvPhone = findViewById(R.id.tv_phone);
        tvName = findViewById(R.id.tv_name);
        tvAddress = findViewById(R.id.tv_address);
        tvTotal = findViewById(R.id.tv_total);
        edtDiscount = findViewById(R.id.edt_discount);
        btnDiscount = findViewById(R.id.btn_discount);
        btnChanges = findViewById(R.id.btn_changes);
        edtCash = findViewById(R.id.edt_cash);
        tvPay = findViewById(R.id.tv_pay);
        tvChanges = findViewById(R.id.tv_changes);

        phone = getIntent().getStringExtra("tvPhone");
        name = getIntent().getStringExtra("tvName");
        address = getIntent().getStringExtra("tvAddress");

        tvPhone.setText(phone);
        tvName.setText(name);
        tvAddress.setText(address);

        btnDiscount.setOnClickListener(v -> {
            total = tvTotal.getText().toString().trim();
            discount = edtDiscount.getText().toString().trim();
            if (discount.isEmpty()) {
                total_value = Double.parseDouble(total);
            } else {
                total_value = Double.parseDouble(total) - Double.parseDouble(total) * Double.parseDouble(discount) / percent;

            }
            tvPay.setText(RupiahHelper.formatRupiah(total_value));
        });

        btnChanges.setOnClickListener(v -> {
            String pay = edtCash.getText().toString().trim();

            changes = Integer.parseInt(pay) - total_value;

            tvChanges.setText(RupiahHelper.formatRupiah(changes));
        });


        RecyclerView rvOrder = findViewById(R.id.rv_order);
        rvOrder.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvOrder.setHasFixedSize(false);
        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance()
                .getReference()
                .child("Kacamata")
                .child("order")
                .child(phone)
                .child("order_list");

        reference.keepSynced(true);
        options = new FirebaseRecyclerOptions.Builder<Order>()
                .setQuery(reference, Order.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<Order, OrderHolder>(options) {
            @SuppressLint("SetTextI18n")
            @Override
            protected void onBindViewHolder(@NonNull OrderHolder holder, int position, @NonNull Order model) {
                holder.tvDate.setText(model.getDate());
                holder.tvTitle.setText(model.getTitle());
                holder.tvNote.setText(model.getNote());
                holder.tvPrice.setText(RupiahHelper.formatRupiah(Double.parseDouble(model.getPrice())));
                holder.tvProId.setText(model.getProId());
                holder.tvQty.setText("X " + model.getQuantity());
                holder.tvSubtotal.setText(RupiahHelper.formatRupiah(Double.parseDouble(model.getSubtotal())));

            }

            @NonNull
            @Override
            public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
                return new OrderHolder(view);
            }
        };
        rvOrder.setAdapter(adapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderList.clear();
                int total = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Order order = ds.getValue(Order.class);
                    int subtotal = Integer.parseInt(Objects.requireNonNull(order).getSubtotal());
                    total = total + subtotal;
                    orderList.add(order);
                    tvTotal.setText(String.valueOf(total));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private static class OrderHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvPrice, tvQty, tvSubtotal, tvNote, tvDate, tvProId;
        CardView cvOrder;

        public OrderHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvProId = itemView.findViewById(R.id.tv_proId);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvNote = itemView.findViewById(R.id.tv_note);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvQty = itemView.findViewById(R.id.tv_qty);
            tvSubtotal = itemView.findViewById(R.id.tv_subtotal);
            cvOrder = itemView.findViewById(R.id.cv_order);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_detail, menu);
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
                    reference.child("order").child(phone).child("order_list").removeValue();
                    reference.child("order").child(phone).child("process").removeValue();
                    finish();
                })
                .setNegativeButton(getString(R.string.no), (dialog, id) -> dialog.cancel());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}