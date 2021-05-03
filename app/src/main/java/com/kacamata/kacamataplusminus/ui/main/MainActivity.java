package com.kacamata.kacamataplusminus.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kacamata.kacamataplusminus.R;
import com.kacamata.kacamataplusminus.entity.News;

public class MainActivity extends AppCompatActivity {
    private FirebaseRecyclerOptions<News> options;
    private FirebaseRecyclerAdapter<News, NewsHolder> adapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnMenu = findViewById(R.id.btn_menu);
        btnMenu.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);

        });
        RecyclerView rvMain = findViewById(R.id.rv_main);
        rvMain.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvMain.setHasFixedSize(false);
        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance()
                .getReference("Kacamata")
                .child("news");
        reference.keepSynced(true);
        options = new FirebaseRecyclerOptions.Builder<News>()
                .setQuery(reference, News.class)
                .build();


        adapter = new FirebaseRecyclerAdapter<News, NewsHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull NewsHolder holder, int position, @NonNull News model) {
                holder.tvProId.setText(model.getProId());
                holder.tvTitle.setText(model.getTitle());
                holder.tvDesc.setText(model.getDescription());
                Glide.with(getApplicationContext())
                        .load(model.getImageUrl())
                        .override(400, 400)
                        .into(holder.imgMain);
                holder.cvPro.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), UpdateMainActivity.class);
                    intent.putExtra("tvId", model.getProId());
                    intent.putExtra("tvTitle", model.getTitle());
                    intent.putExtra("tvDesc", model.getDescription());
                    intent.putExtra("imgMain", model.getImageUrl());
                    startActivity(intent);
                });

            }

            @NonNull
            @Override
            public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
                return new NewsHolder(view);
            }
        };

        rvMain.setAdapter(adapter);

    }

    private static class NewsHolder extends RecyclerView.ViewHolder {
        TextView tvProId, tvDesc, tvTitle;
        ImageView imgMain;
        CardView cvPro;

        public NewsHolder(@NonNull View itemView) {
            super(itemView);
            tvProId = itemView.findViewById(R.id.tv_proId);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDesc = itemView.findViewById(R.id.tv_description);
            imgMain = itemView.findViewById(R.id.img_main);
            cvPro = itemView.findViewById(R.id.cv_pro);


        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_input) {
            Intent intent = new Intent(getApplicationContext(), InputMainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}