package hu.bme.aut.shoppinglist.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hu.bme.aut.shoppinglist.R;
import hu.bme.aut.shoppinglist.data.Word;

public class TranslationAdapter
        extends RecyclerView.Adapter<TranslationAdapter.TranslationViewHolder> {

    public interface WordFavoriteSetListener{
        void onWordChanged(Word word);
    }

    private final List<Word> words = new ArrayList<>();
    private WordFavoriteSetListener listener;

    public TranslationAdapter(WordFavoriteSetListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public TranslationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.word, parent, false);
        return new TranslationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TranslationViewHolder holder, int position) {
        Word word = words.get(position);
        holder.wordTextView.setText(word.word);
        holder.favoriteCheckBox.setChecked(word.favorite);

        holder.word = word;
    }

    public void addItem(Word word) {
        words.add(word);
        notifyItemInserted(words.size() - 1);
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    class TranslationViewHolder extends RecyclerView.ViewHolder {

        TextView wordTextView;
        CheckBox favoriteCheckBox;

        Word word;

        TranslationViewHolder(View itemView) {
            super(itemView);
            wordTextView = itemView.findViewById(R.id.wordTextView);
            favoriteCheckBox = itemView.findViewById(R.id.favoriteCheckBox);

            favoriteCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(word != null){
                        word.favorite = favoriteCheckBox.isChecked();
                        listener.onWordChanged(word);
                    }
                }
            });
        }
    }
}