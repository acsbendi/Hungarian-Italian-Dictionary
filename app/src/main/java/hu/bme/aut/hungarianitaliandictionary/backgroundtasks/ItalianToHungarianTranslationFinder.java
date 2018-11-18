package hu.bme.aut.hungarianitaliandictionary.backgroundtasks;

import android.os.AsyncTask;

import java.util.List;

import hu.bme.aut.hungarianitaliandictionary.data.DictionaryDatabase;
import hu.bme.aut.hungarianitaliandictionary.data.HungarianWord;

public class ItalianToHungarianTranslationFinder
        extends AsyncTask<Void, Void, List<HungarianWord>> {

    private final String sourceItalianWord;
    private final DictionaryDatabase database;

    public ItalianToHungarianTranslationFinder(String sourceItalianWord, DictionaryDatabase database){
        this.sourceItalianWord = sourceItalianWord;
        this.database = database;
    }

    @Override
    protected List<HungarianWord> doInBackground(Void... voids) {
        return database.hungarianWordDao().findHungarianTranslationsFor(sourceItalianWord);
    }
}