package hu.bme.aut.hungarianitaliandictionary.data;

import android.arch.persistence.room.Dao;
        import android.arch.persistence.room.Insert;

@Dao
public interface TranslationDao {

    @Insert
    void insert(Translation translation);
}