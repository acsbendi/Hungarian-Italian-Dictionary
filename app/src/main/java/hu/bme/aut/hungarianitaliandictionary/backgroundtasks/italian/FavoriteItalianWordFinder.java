package hu.bme.aut.hungarianitaliandictionary.backgroundtasks.italian;

import android.os.AsyncTask;

import java.util.List;

import hu.bme.aut.hungarianitaliandictionary.data.DictionaryDatabase;
import hu.bme.aut.hungarianitaliandictionary.data.entities.ItalianWord;

public class FavoriteItalianWordFinder
        extends AsyncTask<Void, Void, List<ItalianWord>> {

    private final DictionaryDatabase database;

    public FavoriteItalianWordFinder(DictionaryDatabase database){
        this.database = database;
    }

    @Override
    protected List<ItalianWord> doInBackground(Void... voids) {
        return database.italianWordDao().findFavoriteItalianWords();
    }
}
