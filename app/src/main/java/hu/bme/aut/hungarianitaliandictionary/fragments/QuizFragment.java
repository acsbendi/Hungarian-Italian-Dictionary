package hu.bme.aut.hungarianitaliandictionary.fragments;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
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
import hu.bme.aut.hungarianitaliandictionary.adapter.QuizAdapter;
import hu.bme.aut.hungarianitaliandictionary.data.HungarianWord;
import hu.bme.aut.hungarianitaliandictionary.data.ItalianWord;

import static hu.bme.aut.hungarianitaliandictionary.data.TranslationDirection.*;

public class QuizFragment extends TranslationDirectionSettableFragment {

    public static final String TAG = "QuizFragment";
    private static final int DEFAULT_QUIZ_WORD_COUNT = 10;

    private RecyclerView recyclerView;
    private QuizAdapter adapter;
    private MainActivity activity;
    private Button checkButton;
    private SwitchCompat quizTypeSwitchButton;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.activity = (MainActivity)context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = View.inflate(getContext(), R.layout.fragment_quiz, null);

        Button startButton = rootView.findViewById(R.id.startQuizButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });

        checkButton = rootView.findViewById(R.id.checkQuizButton);
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
        Toast.makeText(activity,
                String.valueOf(adapter.getCorrectAnswerCount()), Toast.LENGTH_SHORT)
                .show(); //TODO make a result screen
    }

    private void setHungarianToItalianRecyclerViewAdapter() {
        adapter = new QuizAdapter(HUNGARIAN_TO_ITALIAN, activity);
        recyclerView.setAdapter(adapter);
    }

    private void setItalianToHungarianRecyclerViewAdapter() {
        adapter = new QuizAdapter(ITALIAN_TO_HUNGARIAN, activity);
        recyclerView.setAdapter(adapter);
    }

    private void initRecyclerView(View rootView) {
        recyclerView = rootView.findViewById(R.id.quizRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
    }
}
