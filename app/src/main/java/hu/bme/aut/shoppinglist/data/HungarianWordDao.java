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

    @Query("SELECT hw.id, hw.word FROM hungarianword AS hw")
    List<HungarianWord> getAllHungarianWords();

    @Query("SELECT hw.id, hw.word FROM hungarianword AS hw " +
            "WHERE word = :hungarianWord")
    List<HungarianWord> findHungarianWord(String hungarianWord);
}