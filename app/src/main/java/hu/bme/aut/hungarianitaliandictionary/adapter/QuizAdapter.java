package hu.bme.aut.hungarianitaliandictionary.adapter;

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

import hu.bme.aut.hungarianitaliandictionary.R;
import hu.bme.aut.hungarianitaliandictionary.data.TranslationDirection;
import hu.bme.aut.hungarianitaliandictionary.data.Word;

public class QuizAdapter
        extends RecyclerView.Adapter<QuizAdapter.QuizWordViewHolder> {

    private final List<Word> questionWords = new ArrayList<>();
    private final List<String> answers = new ArrayList<>();
    private final TranslationDirection translationDirection;

    public QuizAdapter(TranslationDirection translationDirection){
        this.translationDirection = translationDirection;
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

    @Override
    public int getItemCount() {
        return questionWords.size();
    }

    public List<String> getAnswers() {
        return answers;
    }

    public TranslationDirection getTranslationDirection() {
        return translationDirection;
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