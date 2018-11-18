package hu.bme.aut.hungarianitaliandictionary.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import hu.bme.aut.hungarianitaliandictionary.data.daos.HungarianWordDao;
import hu.bme.aut.hungarianitaliandictionary.data.daos.ItalianWordDao;
import hu.bme.aut.hungarianitaliandictionary.data.daos.TranslationDao;
import hu.bme.aut.hungarianitaliandictionary.data.entities.HungarianWord;
import hu.bme.aut.hungarianitaliandictionary.data.entities.ItalianWord;
import hu.bme.aut.hungarianitaliandictionary.data.entities.Translation;

@Database(
        entities = {ItalianWord.class, HungarianWord.class, Translation.class},
        version = 5,
        exportSchema = false
)
public abstract class DictionaryDatabase extends RoomDatabase {
    public abstract ItalianWordDao italianWordDao();
    public abstract HungarianWordDao hungarianWordDao();
    public abstract TranslationDao translationDao();
}