package hu.bme.aut.shoppinglist.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import hu.bme.aut.shoppinglist.data.HungarianWord;

@Dao
public interface HungarianWordDao {

    @Insert
    void insert(HungarianWord hungarianWord);

    @Update
    void update(HungarianWord... hungarianWords);

    @Delete
    void delete(HungarianWord... hungarianWords);

    @Query("SELECT * FROM hungarianword")
    List<HungarianWord> getAllHungarianWords();

    @Query("SELECT * FROM hungarianword AS hw " +
            "JOIN italianword AS iw ON " + //TODO finish this query
            "WHERE iw.word = :italianWord")
    List<HungarianWord> findHungarianTranslationsFor(String italianWord);
}