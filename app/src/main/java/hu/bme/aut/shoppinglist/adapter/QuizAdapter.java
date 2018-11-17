package hu.bme.aut.shoppinglist.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hu.bme.aut.shoppinglist.R;
import hu.bme.aut.shoppinglist.data.Word;

public class QuizAdapter
        extends RecyclerView.Adapter<QuizAdapter.QuizWordViewHolder> {

    private final List<Word> questionWords = new ArrayList<>();

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
        notifyItemInserted(questionWords.size() - 1);
    }

    @Override
    public int getItemCount() {
        return questionWords.size();
    }

    class QuizWordViewHolder extends RecyclerView.ViewHolder {

        TextView questionWordTextView;
        EditText solutionEditText;

        Word questionWord;

        QuizWordViewHolder(View itemView) {
            super(itemView);

            questionWordTextView = itemView.findViewById(R.id.questionWordTextView);
            solutionEditText = itemView.findViewById(R.id.solutionEditText);
        }
    }
}