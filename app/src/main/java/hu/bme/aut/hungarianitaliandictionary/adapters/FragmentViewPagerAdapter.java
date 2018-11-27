package hu.bme.aut.hungarianitaliandictionary.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import hu.bme.aut.hungarianitaliandictionary.R;
import hu.bme.aut.hungarianitaliandictionary.fragments.QuizFragment;
import hu.bme.aut.hungarianitaliandictionary.fragments.TranslationFinderFragment;

public class FragmentViewPagerAdapter extends FragmentPagerAdapter implements QuizAdapter.QuizDoneObserver{

    private final Context context;
    private boolean quizDone = false;
    private ResultFragment resultFragment = new ResultFragment();

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
                if(quizDone)
                    return resultFragment;
                else
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

    @Override
    public void quizDone(int correctAnswerCount, int questionCount) {
        quizDone = true;
    }
}
