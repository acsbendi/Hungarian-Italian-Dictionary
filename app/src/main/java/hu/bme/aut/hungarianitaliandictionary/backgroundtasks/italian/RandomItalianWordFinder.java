package hu.bme.aut.hungarianitaliandictionary.backgroundtasks.italian;

import android.os.AsyncTask;

import java.util.List;

import hu.bme.aut.hungarianitaliandictionary.data.DictionaryDatabase;
import hu.bme.aut.hungarianitaliandictionary.data.ItalianWord;

public class RandomItalianWordFinder
        extends AsyncTask<Void, Void, List<ItalianWord>> {

    private final int resultCount;
    private final DictionaryDatabase database;

    public RandomItalianWordFinder(int resultCount, DictionaryDatabase database){
        this.resultCount = resultCount;
        this.database = database;
    }

    @Override
    protected List<ItalianWord> doInBackground(Void... voids) {
        return database.italianWordDao().findRandomItalianWords(resultCount);
    }
}
