package hu.bme.aut.hungarianitaliandictionary.fragments;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import hu.bme.aut.hungarianitaliandictionary.MainActivity;
import hu.bme.aut.hungarianitaliandictionary.R;
import hu.bme.aut.hungarianitaliandictionary.adapters.FragmentViewPagerAdapter;
import hu.bme.aut.hungarianitaliandictionary.adapters.QuizAdapter;
import hu.bme.aut.hungarianitaliandictionary.data.entities.HungarianWord;
import hu.bme.aut.hungarianitaliandictionary.data.entities.ItalianWord;
import info.hoang8f.widget.FButton;

import static hu.bme.aut.hungarianitaliandictionary.data.entities.TranslationDirection.*;

public class QuizFragment extends TranslationDirectionSettableFragment {

    public static final String TAG = "QuizFragment";
    private static final int DEFAULT_QUIZ_WORD_COUNT = 10;

    private RecyclerView recyclerView;
    private QuizAdapter adapter;
    private MainActivity activity;
    private FButton checkButton;
    private SwitchCompat quizTypeSwitchButton;
    private FragmentViewPagerAdapter fragmentViewPagerAdapter;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.activity = (MainActivity)context;
        this.fragmentViewPagerAdapter = ((MainActivity) context).getFragmentViewPagerAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = View.inflate(getContext(), R.layout.fragment_quiz, null);

        FButton startButton = rootView.findViewById(R.id.startQuizButton);
        startButton.setButtonColor(fetchPrimaryColor());
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });

        checkButton = rootView.findViewById(R.id.checkQuizButton);
        checkButton.setButtonColor(fetchPrimaryColor());
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkQuiz();
            }
        });

        initQuizTypeSwitchButton(rootView);

        initRecyclerView(rootView);

        initTranslationDirectionSwitchButton(rootView);

        return rootView;
    }

    private int fetchPrimaryColor() {
        TypedValue typedValue = new TypedValue();

        TypedArray a = activity.obtainStyledAttributes(typedValue.data, new int[] { R.attr.colorPrimary });
        int color = a.getColor(0, 0);

        a.recycle();

        return color;
    }

    private void initQuizTypeSwitchButton(View rootView){
        quizTypeSwitchButton = rootView.findViewById(R.id.quizTypeSwitchButton);

        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        if(wm != null){
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;

            quizTypeSwitchButton.setSwitchMinWidth((int)(width*0.14));
        }
    }

    private void startQuiz(){
        checkButton.setVisibility(View.VISIBLE);
        if(selectedTranslationDirection == ITALIAN_TO_HUNGARIAN && quizTypeSwitchButton.isChecked())
            startNewFavoritesItalianToHungarianQuiz();
        else if(selectedTranslationDirection == ITALIAN_TO_HUNGARIAN && !quizTypeSwitchButton.isChecked())
            startNewRandomItalianToHungarianQuiz();
        else if(selectedTranslationDirection == HUNGARIAN_TO_ITALIAN && quizTypeSwitchButton.isChecked())
            startNewFavoritesHungarianToItalianQuiz();
        else if(selectedTranslationDirection == HUNGARIAN_TO_ITALIAN && !quizTypeSwitchButton.isChecked())
            startNewRandomHungarianToItalianQuiz();
    }

    private void startNewRandomHungarianToItalianQuiz(){
        setHungarianToItalianRecyclerViewAdapter();
        List<HungarianWord> hungarianQuizWords = activity.getRandomHungarianWords(DEFAULT_QUIZ_WORD_COUNT);

        for(HungarianWord hungarianWord : hungarianQuizWords)
            adapter.addItem(hungarianWord);
    }

    private void startNewRandomItalianToHungarianQuiz(){
        setItalianToHungarianRecyclerViewAdapter();
        List<ItalianWord> italianQuizWords = activity.getRandomItalianWords(DEFAULT_QUIZ_WORD_COUNT);

        for(ItalianWord italianWord : italianQuizWords)
            adapter.addItem(italianWord);
    }

    private void startNewFavoritesHungarianToItalianQuiz(){
        setHungarianToItalianRecyclerViewAdapter();
        List<HungarianWord> hungarianQuizWords = activity.getFavoriteHungarianWords();

        for(HungarianWord hungarianWord : hungarianQuizWords)
            adapter.addItem(hungarianWord);

    }

    private void startNewFavoritesItalianToHungarianQuiz(){
        setItalianToHungarianRecyclerViewAdapter();
        List<ItalianWord> italianQuizWords = activity.getFavoriteItalianWords();

        for(ItalianWord italianWord : italianQuizWords)
            adapter.addItem(italianWord);
    }


    private void checkQuiz(){
        adapter.checkQuiz();
    }

    private void setHungarianToItalianRecyclerViewAdapter() {
        adapter = new QuizAdapter(HUNGARIAN_TO_ITALIAN, activity, fragmentViewPagerAdapter);
        recyclerView.setAdapter(adapter);
    }

    private void setItalianToHungarianRecyclerViewAdapter() {
        adapter = new QuizAdapter(ITALIAN_TO_HUNGARIAN, activity, fragmentViewPagerAdapter);
        recyclerView.setAdapter(adapter);
    }

    private void initRecyclerView(View rootView) {
        recyclerView = rootView.findViewById(R.id.quizRecyclerView);
        recyclerView.setLayoutManager( new LinearLayoutManager(activity) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
    }
}
