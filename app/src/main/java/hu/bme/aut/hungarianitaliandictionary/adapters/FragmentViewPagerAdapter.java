package hu.bme.aut.hungarianitaliandictionary.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import hu.bme.aut.hungarianitaliandictionary.R;
import hu.bme.aut.hungarianitaliandictionary.fragments.QuizFragment;
import hu.bme.aut.hungarianitaliandictionary.fragments.ResultFragment;
import hu.bme.aut.hungarianitaliandictionary.fragments.TranslationFinderFragment;

public class FragmentViewPagerAdapter extends FragmentStatePagerAdapter implements QuizAdapter.QuizDoneObserver{

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

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
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
        Bundle bundle = new Bundle();
        bundle.putInt("correctAnswerCount", correctAnswerCount);
        bundle.putInt("questionCount", questionCount);
        resultFragment.setArguments(bundle);
        notifyDataSetChanged();
    }

    public void quizResultSeen(){
        quizDone = false;
        notifyDataSetChanged();
    }
}
