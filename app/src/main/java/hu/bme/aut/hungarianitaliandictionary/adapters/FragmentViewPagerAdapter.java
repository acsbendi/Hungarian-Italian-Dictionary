package hu.bme.aut.hungarianitaliandictionary.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import hu.bme.aut.hungarianitaliandictionary.R;
import hu.bme.aut.hungarianitaliandictionary.fragments.QuizFragment;
import hu.bme.aut.hungarianitaliandictionary.fragments.TranslationFinderFragment;

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
                return new QuizFragment();
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
