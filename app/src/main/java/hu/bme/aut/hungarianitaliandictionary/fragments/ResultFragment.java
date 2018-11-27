package hu.bme.aut.hungarianitaliandictionary.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import hu.bme.aut.hungarianitaliandictionary.MainActivity;
import hu.bme.aut.hungarianitaliandictionary.R;
import hu.bme.aut.hungarianitaliandictionary.adapters.FragmentViewPagerAdapter;

public class ResultFragment extends Fragment {

    private int correctAnswerCount;
    private int questionCount;
    private double resultPercentage;
    private FragmentViewPagerAdapter fragmentViewPagerAdapter;

    private void onOkButtonPressed(){
        fragmentViewPagerAdapter.quizResultSeen();
    }

    private void setData(){
        Bundle arguments = getArguments();
        if(arguments != null){
            correctAnswerCount = arguments.getInt("correctAnswerCount");
            questionCount = arguments.getInt("questionCount");
            resultPercentage = correctAnswerCount / (double)questionCount;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.fragmentViewPagerAdapter = ((MainActivity)context).getFragmentViewPagerAdapter();
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

        Button okButton = rootView.findViewById(R.id.resultOkButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOkButtonPressed();
            }
        });

        return rootView;
    }
}

