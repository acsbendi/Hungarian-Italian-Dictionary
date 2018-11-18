package hu.bme.aut.hungarianitaliandictionary.backgroundtasks.italian;

import android.os.AsyncTask;

import hu.bme.aut.hungarianitaliandictionary.adapter.TranslationAdapter;
import hu.bme.aut.hungarianitaliandictionary.data.DictionaryDatabase;
import hu.bme.aut.hungarianitaliandictionary.data.ItalianWord;

public class ItalianWordChangeListener
        extends AsyncTask<ItalianWord, Void, Void>
        implements TranslationAdapter.WordChangeListener<ItalianWord> {

    private final DictionaryDatabase database;

    public ItalianWordChangeListener(DictionaryDatabase database){
        this.database = database;
    }

    @Override
    public void onWordChanged(ItalianWord italianWord) {
        execute(italianWord);
    }

    @Override
    protected Void doInBackground(ItalianWord... italianWords) {
        database.italianWordDao().update(italianWords[0]);

        return null;
    }
}

