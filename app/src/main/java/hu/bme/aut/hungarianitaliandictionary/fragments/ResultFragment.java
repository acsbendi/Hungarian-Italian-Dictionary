package hu.bme.aut.hungarianitaliandictionary.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hu.bme.aut.hungarianitaliandictionary.R;

public class ResultFragment extends Fragment {

    private int correctAnswerCount;
    private int questionCount;
    private double resultPercentage;


    private void setData(){
        Bundle arguments = getArguments();
        if(arguments != null){
            correctAnswerCount = arguments.getInt("correctAnswerCount");
            questionCount = arguments.getInt("questionCount");
            resultPercentage = correctAnswerCount / (double)questionCount;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = View.inflate(getContext(), R.layout.fragment_result, null);
        setData();

        TextView ratioTextView = rootView.findViewById(R.id.quizResultRatio);

        ratioTextView.setText(getResources().getString(R.string.quiz_ratio_template,
                correctAnswerCount, questionCount));

        TextView percentageTextView = rootView.findViewById(R.id.quizResultPercentage);

        percentageTextView.setText(getResources().getString(R.string.quiz_percentage_template, resultPercentage));

        return rootView;
    }
}

