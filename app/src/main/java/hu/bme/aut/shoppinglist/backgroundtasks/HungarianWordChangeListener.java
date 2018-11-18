package hu.bme.aut.shoppinglist.backgroundtasks;

import android.os.AsyncTask;

import hu.bme.aut.shoppinglist.adapter.TranslationAdapter;
import hu.bme.aut.shoppinglist.data.DictionaryDatabase;
import hu.bme.aut.shoppinglist.data.HungarianWord;

public class HungarianWordChangeListener
        extends AsyncTask<HungarianWord, Void, Void>
        implements TranslationAdapter.WordChangeListener<HungarianWord> {

    private final DictionaryDatabase database;

    public HungarianWordChangeListener(DictionaryDatabase database){
        this.database = database;
    }

    @Override
    public void onWordChanged(HungarianWord hungarianWord) {
        execute(hungarianWord);
    }

    @Override
    protected Void doInBackground(HungarianWord... hungarianWords) {
        database.hungarianWordDao().update(hungarianWords[0]);

        return null;
    }
}
