package hu.bme.aut.shoppinglist.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(
        entities = {ItalianWord.class, HungarianWord.class, Translation.class},
        version = 4,
        exportSchema = false
)
public abstract class DictionaryDatabase extends RoomDatabase {
    public abstract ItalianWordDao italianWordDao();
    public abstract HungarianWordDao hungarianWordDao();
    public abstract TranslationDao translationDao();
}