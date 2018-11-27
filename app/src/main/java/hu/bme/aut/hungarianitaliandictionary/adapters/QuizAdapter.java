package hu.bme.aut.hungarianitaliandictionary.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hu.bme.aut.hungarianitaliandictionary.MainActivity;
import hu.bme.aut.hungarianitaliandictionary.R;
import hu.bme.aut.hungarianitaliandictionary.data.entities.TranslationDirection;
import hu.bme.aut.hungarianitaliandictionary.data.entities.Word;

import static hu.bme.aut.hungarianitaliandictionary.data.entities.TranslationDirection.*;

public class QuizAdapter
        extends RecyclerView.Adapter<QuizAdapter.QuizWordViewHolder> {

    public interface QuizDoneObserver{
        void quizDone(int correctAnswerCount, int questionCount);
    }

    private final List<Word> questionWords = new ArrayList<>();
    private final List<String> answers = new ArrayList<>();
    private final TranslationDirection translationDirection;
    private final MainActivity activity;
    private final QuizDoneObserver observer;

    public QuizAdapter(TranslationDirection translationDirection, MainActivity activity, QuizDoneObserver observer){
        this.translationDirection = translationDirection;
        this.activity = activity;
        this.observer = observer;
    }

    @NonNull
    @Override
    public QuizWordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.quiz_word, parent, false);
        return new QuizWordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizWordViewHolder holder, int position) {
        Word questionWord = questionWords.get(position);
        holder.questionWordTextView.setText(questionWord.word);

        holder.questionWord = questionWord;
    }

    public void addItem(Word word) {
        questionWords.add(word);
        answers.add("");
        notifyItemInserted(questionWords.size() - 1);
    }

    private int getCorrectAnswerCount(){
        int correctAnswerCount = 0;

        if(translationDirection == ITALIAN_TO_HUNGARIAN){
            for(int i = 0; i < questionWords.size(); ++i){
                if(activity.correctTranslation(answers.get(i), questionWords.get(i).word))
                    ++correctAnswerCount;
            }
        } else if(translationDirection == HUNGARIAN_TO_ITALIAN){
            for(int i = 0; i < questionWords.size(); ++i){
                if(activity.correctTranslation(questionWords.get(i).word, answers.get(i)))
                    ++correctAnswerCount;
            }
        }

        return correctAnswerCount;
    }

    public void checkQuiz(){
        observer.quizDone(getCorrectAnswerCount(), questionWords.size());
    }

    @Override
    public int getItemCount() {
        return questionWords.size();
    }

    class QuizWordViewHolder extends RecyclerView.ViewHolder {

        TextView questionWordTextView;
        EditText answerEditText;

        Word questionWord;

        QuizWordViewHolder(View itemView) {
            super(itemView);

            questionWordTextView = itemView.findViewById(R.id.questionWordTextView);
            answerEditText = itemView.findViewById(R.id.answerEditText);

            answerEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    answers.set(getAdapterPosition(), s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }
}