package hu.bme.aut.hungarianitaliandictionary.backgroundtasks.alldatabase;

import android.os.AsyncTask;

import java.util.List;

import hu.bme.aut.hungarianitaliandictionary.data.DictionaryDatabase;
import hu.bme.aut.hungarianitaliandictionary.data.entities.HungarianWord;
import hu.bme.aut.hungarianitaliandictionary.data.entities.ItalianWord;

public class AllDataDeleter extends AsyncTask<Void, Void, Void> {

    private final DictionaryDatabase database;

    public AllDataDeleter(DictionaryDatabase database) {
        this.database = database;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        List<ItalianWord> allItalianWords = database.italianWordDao().getAllItalianWords();

        database.italianWordDao().delete(allItalianWords.toArray(new ItalianWord[allItalianWords.size()]));

        List<HungarianWord> allHungarianWords = database.hungarianWordDao().getAllHungarianWords();

        database.hungarianWordDao().delete(allHungarianWords.toArray(new HungarianWord[allHungarianWords.size()]));


        return null;
    }
}
