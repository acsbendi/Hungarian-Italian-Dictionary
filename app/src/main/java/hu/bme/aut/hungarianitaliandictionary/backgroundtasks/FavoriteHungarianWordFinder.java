package hu.bme.aut.hungarianitaliandictionary.backgroundtasks;

import android.os.AsyncTask;

import java.util.List;

import hu.bme.aut.hungarianitaliandictionary.data.DictionaryDatabase;
import hu.bme.aut.hungarianitaliandictionary.data.HungarianWord;

public class FavoriteHungarianWordFinder
        extends AsyncTask<Void, Void, List<HungarianWord>> {

    private final DictionaryDatabase database;

    public FavoriteHungarianWordFinder(DictionaryDatabase database){
        this.database = database;
    }

    @Override
    protected List<HungarianWord> doInBackground(Void... voids) {
        return database.hungarianWordDao().findFavoriteHungarianWords();
    }
}

