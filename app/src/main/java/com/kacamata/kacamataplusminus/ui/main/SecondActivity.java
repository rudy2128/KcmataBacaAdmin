package com.kacamata.kacamataplusminus.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.kacamata.kacamataplusminus.R;
import com.kacamata.kacamataplusminus.ui.home.InputProductActivity;
import com.kacamata.kacamataplusminus.ui.testimony.InputTestActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TabLayout tabLayout = findViewById(R.id.tabs_id);
        ViewPager viewPager = findViewById(R.id.view_pager);

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.next_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.product_input) {
            Intent intent = new Intent(getApplicationContext(), InputProductActivity.class);
            startActivity(intent);
        }else if (item.getItemId() == R.id.test_input){
            Intent intent = new Intent(getApplicationContext(), InputTestActivity.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }
    }
