package hu.bme.aut.shoppinglist.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import hu.bme.aut.shoppinglist.R;
import hu.bme.aut.shoppinglist.fragments.TranslationFinderFragment;

public class FragmentViewPagerAdapter extends FragmentPagerAdapter {

    private final Context context;

    public FragmentViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new TranslationFinderFragment();
            case 1:
                return new FragmentTwo();
            default:
                return new TranslationFinderFragment();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0) {
            return context.getResources().getString(R.string.translation);
        } else {
            return context.getResources().getString(R.string.quiz);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
