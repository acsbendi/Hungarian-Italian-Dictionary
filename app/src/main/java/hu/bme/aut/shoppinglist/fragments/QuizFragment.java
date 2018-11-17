package hu.bme.aut.shoppinglist.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import hu.bme.aut.shoppinglist.MainActivity;
import hu.bme.aut.shoppinglist.R;
import hu.bme.aut.shoppinglist.adapter.QuizAdapter;
import hu.bme.aut.shoppinglist.data.TranslationDirection;

import static hu.bme.aut.shoppinglist.data.TranslationDirection.*;

public class QuizFragment extends Fragment {

    public static String TAG = "QuizFragment";

    private RecyclerView recyclerView;
    private QuizAdapter adapter;
    private MainActivity activity;
    private Button checkButton;
    private SwitchCompat quizTypeSwitchButton;
    private AppCompatImageButton translationDirectionSwitchButton;
    private TranslationDirection selectedTranslationDirection = HUNGARIAN_TO_ITALIAN;

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

        translationDirectionSwitchButton = rootView.findViewById(R.id.translationDirectionSwitchButton);
        translationDirectionSwitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTranslationDirectionSwitchButtonPressed();
            }
        });

        quizTypeSwitchButton = rootView.findViewById(R.id.quizTypeSwitchButton);

        initRecyclerView(rootView);

        return rootView;
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

    }

    private void startNewRandomItalianToHungarianQuiz(){

    }

    private void startNewFavoritesHungarianToItalianQuiz(){

    }

    private void startNewFavoritesItalianToHungarianQuiz(){

    }


    private void checkQuiz(){

    }

    private void onTranslationDirectionSwitchButtonPressed(){
        if(selectedTranslationDirection == ITALIAN_TO_HUNGARIAN){
            flipTranslationDirectionSwitchButton(false);
            selectedTranslationDirection = HUNGARIAN_TO_ITALIAN;
        }
        else{
            flipTranslationDirectionSwitchButton(true);
            selectedTranslationDirection = ITALIAN_TO_HUNGARIAN;
        }
    }

    private void flipTranslationDirectionSwitchButton(boolean right){
        translationDirectionSwitchButton.setScaleX(right ? -1 : 1);
    }

    private void initRecyclerView(View rootView) {
        recyclerView = rootView.findViewById(R.id.quizRecyclerView);
        adapter = new QuizAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(adapter);
    }
}
