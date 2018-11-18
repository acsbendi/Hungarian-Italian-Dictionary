package hu.bme.aut.hungarianitaliandictionary.backgroundtasks;

import android.os.AsyncTask;

import hu.bme.aut.hungarianitaliandictionary.data.DictionaryDatabase;

public class TranslationChecker
    extends AsyncTask<Void, Void, Boolean> {

    private final String hungarianWord;
    private final String italianWord;
    private DictionaryDatabase database;

    public TranslationChecker(String hungarianWord, String italianWord, DictionaryDatabase database) {
        this.hungarianWord = hungarianWord;
        this.italianWord = italianWord;
        this.database = database;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        return database.translationDao().checkTranslation(hungarianWord, italianWord) == 1;
    }
}
