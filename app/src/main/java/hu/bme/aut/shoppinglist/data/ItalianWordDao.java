package hu.bme.aut.shoppinglist.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import hu.bme.aut.shoppinglist.data.ItalianWord;

@Dao
public interface ItalianWordDao {

    @Insert
    void insert(ItalianWord italianWord);

    @Update
    void update(ItalianWord... italianWords);

    @Delete
    void delete(ItalianWord... italianWords);

    @Query("SELECT * FROM italianword")
    List<ItalianWord> getAllItalianWords();

    @Query("SELECT * FROM italianword AS iw " +
            "JOIN hungarianword AS hw ON " + //TODO finish this query
            "WHERE hw.word = :hungarianWord")
    List<ItalianWord> findItalianTranslationsFor(String hungarianWord);
}