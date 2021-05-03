package com.kacamata.kacamataplusminus.ui.cart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kacamata.kacamataplusminus.R;
import com.kacamata.kacamataplusminus.entity.Cart;


public class CartFragment extends Fragment {
    private FirebaseRecyclerOptions<Cart> options;
    private FirebaseRecyclerAdapter<Cart, CartHolder> adapter;
    DatabaseReference reference;
    FirebaseAuth mAuth;

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


    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.rv_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setHasFixedSize(false);

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance()
                .getReference()
                .child("Kacamata")
                .child("order");

        reference.keepSynced(true);
        options = new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(reference, Cart.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<Cart, CartHolder>(options) {
            @SuppressLint("SetTextI18n")
            @Override
            protected void onBindViewHolder(@NonNull CartHolder holder, int position, @NonNull Cart model) {
                holder.tvPhone.setText(model.getPhone());
                holder.tvName.setText(model.getName());
                holder.tvAddress.setText(model.getAddress());
                holder.tvProcess.setText("bayar:"+model.getProcess());
                holder.itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(view.getContext(), UpdateCartActivity.class);
                    intent.putExtra("tvPhone", model.getPhone());
                    intent.putExtra("tvName", model.getName());
                    intent.putExtra("tvAddress", model.getAddress());
                    startActivity(intent);


                });

            }

            @NonNull
            @Override
            public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
                return new CartHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private static class CartHolder extends RecyclerView.ViewHolder {
        TextView tvPhone, tvName, tvAddress,tvProcess;

        public CartHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPhone = itemView.findViewById(R.id.tv_phone);
            tvAddress = itemView.findViewById(R.id.tv_address);
            tvProcess = itemView.findViewById(R.id.tv_process);


        }
    }
}