package hu.bme.aut.hungarianitaliandictionary.backgroundtasks.hungarian;

import android.os.AsyncTask;

import hu.bme.aut.hungarianitaliandictionary.adapter.TranslationAdapter;
import hu.bme.aut.hungarianitaliandictionary.data.DictionaryDatabase;
import hu.bme.aut.hungarianitaliandictionary.data.HungarianWord;

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
