package com.kacamata.kacamataplusminus.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kacamata.kacamataplusminus.R;
import com.kacamata.kacamataplusminus.entity.Product;
import com.kacamata.kacamataplusminus.helper.RupiahHelper;

public class HomeFragment extends Fragment {
    private FirebaseRecyclerOptions<Product> options;
    private FirebaseRecyclerAdapter<Product, ProHolder> adapter;
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


    public HomeFragment() {
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
        return inflater.inflate(R.layout.fragment_home, container, false);


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvPro = view.findViewById(R.id.rv_pro);
        rvPro.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvPro.setHasFixedSize(false);

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance()
                .getReference()
                .child("Kacamata")
                .child("product");
        reference.keepSynced(true);
        options = new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(reference, Product.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<Product, ProHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProHolder holder, int position, @NonNull Product model) {
                holder.tvProId.setText(model.getProId());
                holder.tvTitle.setText(model.getTitle());
                holder.tvPrice.setText(RupiahHelper.formatRupiah(Double.parseDouble(model.getPrice())));
                holder.tvDesc.setText(model.getDescription());
                Glide.with(requireActivity())
                        .load(model.getImageUrl())
                        .override(400,400)
                        .into(holder.imgPro);
                holder.itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(view.getContext(), HomeUpdateActivity.class);
                    intent.putExtra("tvProId", model.getProId());
                    intent.putExtra("tvTitle", model.getTitle());
                    intent.putExtra("tvPrice", model.getPrice());
                    intent.putExtra("tvDesc",model.getDescription());
                    intent.putExtra("imgPro", model.getImageUrl());
                    startActivity(intent);


                });


            }

            @NonNull
            @Override
            public ProHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
                return new ProHolder(view);
            }
        };
        rvPro.setAdapter(adapter);

    }

    private static class ProHolder extends RecyclerView.ViewHolder {
        TextView tvTitle,tvProId,tvPrice,tvDesc;
        ImageView imgPro;
        CardView cvPro;
        public ProHolder(@NonNull View itemView) {
            super(itemView);
            tvProId = itemView.findViewById(R.id.tv_proId);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvDesc = itemView.findViewById(R.id.tv_description);
            imgPro= itemView.findViewById(R.id.img_pro);
            cvPro = itemView.findViewById(R.id.cv_pro);

        }
    }
}