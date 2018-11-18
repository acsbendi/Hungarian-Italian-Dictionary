package hu.bme.aut.hungarianitaliandictionary.backgroundtasks.translation;

import android.os.AsyncTask;

import java.util.List;

import hu.bme.aut.hungarianitaliandictionary.data.DictionaryDatabase;
import hu.bme.aut.hungarianitaliandictionary.data.ItalianWord;

public class HungarianToItalianTranslationFinder extends AsyncTask<Void, Void, List<ItalianWord>> {

    private final String sourceHungarianWord;
    private final DictionaryDatabase database;

    public HungarianToItalianTranslationFinder(String sourceHungarianWord, DictionaryDatabase database){
        this.sourceHungarianWord = sourceHungarianWord;
        this.database = database;
    }

    @Override
    protected List<ItalianWord> doInBackground(Void... voids) {
        return database.italianWordDao().findItalianTranslationsFor(sourceHungarianWord);
    }
}
