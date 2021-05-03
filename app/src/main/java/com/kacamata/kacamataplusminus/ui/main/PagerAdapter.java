package com.kacamata.kacamataplusminus.ui.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.kacamata.kacamataplusminus.ui.cart.CartFragment;
import com.kacamata.kacamataplusminus.ui.home.HomeFragment;
import com.kacamata.kacamataplusminus.ui.testimony.TestimonyFragment;
import com.kacamata.kacamataplusminus.ui.trx.TrxFragment;

public class PagerAdapter extends FragmentPagerAdapter {
   private int tab_numb;

    public PagerAdapter(@NonNull FragmentManager fm, int tab_numb) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.tab_numb = tab_numb;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new TestimonyFragment();
            case 2:
                return new CartFragment();

            default:
                return null;
        }
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return tab_numb;
    }
}
