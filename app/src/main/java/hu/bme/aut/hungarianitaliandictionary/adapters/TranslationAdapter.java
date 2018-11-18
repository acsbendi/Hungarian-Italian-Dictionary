package hu.bme.aut.hungarianitaliandictionary.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hu.bme.aut.hungarianitaliandictionary.R;
import hu.bme.aut.hungarianitaliandictionary.data.entities.Word;

public class TranslationAdapter<ShownWordType extends Word>
        extends RecyclerView.Adapter<TranslationAdapter.TranslationViewHolder<ShownWordType>> {

    public interface WordChangeListener<ShownWordType extends Word>{
        void onWordChanged(ShownWordType word);
    }

    private final List<ShownWordType> words = new ArrayList<>();
    private final WordChangeListener<ShownWordType> listener;

    public TranslationAdapter(WordChangeListener<ShownWordType> listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public TranslationViewHolder<ShownWordType> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.word, parent, false);
        return new TranslationViewHolder<>(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TranslationViewHolder<ShownWordType> holder, int position) {
        ShownWordType word = words.get(position);
        holder.wordTextView.setText(word.word);
        holder.favoriteCheckBox.setChecked(word.favorite);

        holder.word = word;
    }

    public void addItem(ShownWordType word) {
        words.add(word);
        notifyItemInserted(words.size() - 1);
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    static class TranslationViewHolder<ShownWordType extends Word> extends RecyclerView.ViewHolder {

        TextView wordTextView;
        CheckBox favoriteCheckBox;

        ShownWordType word;

        TranslationViewHolder(View itemView, final WordChangeListener<ShownWordType> listener) {
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