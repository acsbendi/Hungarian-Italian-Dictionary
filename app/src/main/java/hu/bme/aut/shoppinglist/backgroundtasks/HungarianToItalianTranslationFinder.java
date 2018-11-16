package hu.bme.aut.shoppinglist.backgroundtasks;

import android.os.AsyncTask;

import java.util.List;

import hu.bme.aut.shoppinglist.data.DictionaryDatabase;
import hu.bme.aut.shoppinglist.data.ItalianWord;

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
