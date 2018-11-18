package hu.bme.aut.hungarianitaliandictionary.data;

import android.arch.persistence.room.Dao;
        import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface TranslationDao {

    @Insert
    void insert(Translation translation);

    @Query("SELECT COUNT(t.hungarianWordId) FROM translation AS t " +
            "INNER JOIN hungarianword AS hw ON t.hungarianWordId = hw.id " +
            "INNER JOIN italianword AS iw ON t.italianWordId = iw.id " +
            "WHERE hw.word = :hungarianWord AND iw.word = :italianWord")
    int checkTranslation(String hungarianWord, String italianWord);
}