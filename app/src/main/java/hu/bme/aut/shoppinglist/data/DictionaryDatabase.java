package hu.bme.aut.shoppinglist.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(
        entities = {ItalianWord.class, HungarianWord.class},
        version = 1,
        exportSchema = false
)
public abstract class DictionaryDatabase extends RoomDatabase {
    public abstract ItalianWordDao italianWordDao();
    public abstract HungarianWordDao hungarianWordDao();
}