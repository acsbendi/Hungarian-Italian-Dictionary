package hu.bme.aut.hungarianitaliandictionary.backgroundtasks.hungarian;

import android.os.AsyncTask;

import java.util.List;

import hu.bme.aut.hungarianitaliandictionary.data.DictionaryDatabase;
import hu.bme.aut.hungarianitaliandictionary.data.HungarianWord;

public class RandomHungarianWordFinder
    extends AsyncTask<Void, Void, List<HungarianWord>> {

    private final int resultCount;
    private final DictionaryDatabase database;

    public RandomHungarianWordFinder(int resultCount, DictionaryDatabase database){
        this.resultCount = resultCount;
        this.database = database;
    }

    @Override
    protected List<HungarianWord> doInBackground(Void... voids) {
        return database.hungarianWordDao().findRandomHungarianWords(resultCount);
    }
}
