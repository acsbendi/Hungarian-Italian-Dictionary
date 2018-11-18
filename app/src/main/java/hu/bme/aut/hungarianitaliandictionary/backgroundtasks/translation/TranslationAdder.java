package hu.bme.aut.hungarianitaliandictionary.backgroundtasks.translation;

import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import hu.bme.aut.hungarianitaliandictionary.data.DictionaryDatabase;
import hu.bme.aut.hungarianitaliandictionary.data.entities.HungarianWord;
import hu.bme.aut.hungarianitaliandictionary.data.entities.ItalianWord;
import hu.bme.aut.hungarianitaliandictionary.data.entities.Translation;
import hu.bme.aut.hungarianitaliandictionary.data.entities.TranslationData;

public class TranslationAdder extends AsyncTask<Void, Void, Void> {

    private final TranslationData translationData;
    private final DictionaryDatabase database;

    public TranslationAdder(TranslationData translationData, DictionaryDatabase database){
        this.translationData = translationData;
        this.database = database;
    }

    private long getHungarianWordId(String hungarianWord){
        List<HungarianWord> hungarianWordResult = database.hungarianWordDao().findHungarianWord(hungarianWord);
        if(hungarianWordResult.size() > 0)
            return hungarianWordResult.get(0).id;
        else{
            insertNewHungarianWord(hungarianWord);
            return getHungarianWordId(hungarianWord);
        }
    }

    private void insertNewHungarianWord(String hungarianWord){
        HungarianWord hungarianWordToInsert = new HungarianWord(hungarianWord);
        database.hungarianWordDao().insert(hungarianWordToInsert);
    }

    private long getItalianWordId(String italianWord){
        List<ItalianWord> italianWordResult = database.italianWordDao().findItalianWord(italianWord);
        if(italianWordResult.size() > 0)
            return italianWordResult.get(0).id;
        else{
            insertNewItalianWord(italianWord);
            return getItalianWordId(italianWord);
        }
    }

    private void insertNewItalianWord(String italianWord){
        ItalianWord italianWordToInsert = new ItalianWord(italianWord);
        database.italianWordDao().insert(italianWordToInsert);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String hungarianWord = translationData.hungarianWord;
        String italianWord = translationData.italianWord;

        long hungarianWordId = getHungarianWordId(hungarianWord);
        long italianWordId = getItalianWordId(italianWord);

        Translation translation = new Translation(hungarianWordId, italianWordId);
        try{
            database.translationDao().insert(translation);
        } catch (SQLiteConstraintException e){
            Log.d("Translation adder","Adding translation failed - tried to insert existing translation");
        }
        return null;
    }
}