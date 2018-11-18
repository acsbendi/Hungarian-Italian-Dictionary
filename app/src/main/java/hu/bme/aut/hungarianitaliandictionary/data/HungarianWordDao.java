package hu.bme.aut.hungarianitaliandictionary.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface HungarianWordDao {

    @Insert
    void insert(HungarianWord hungarianWord);

    @Update
    void update(HungarianWord... hungarianWords);

    @Delete
    void delete(HungarianWord... hungarianWords);

    @Query("SELECT hw.id, hw.word, hw.favorite FROM hungarianword AS hw")
    List<HungarianWord> getAllHungarianWords();

    @Query("SELECT hw.id, hw.word, hw.favorite FROM hungarianword AS hw " +
            "INNER JOIN translation AS t ON t.hungarianWordId = hw.id " +
            "INNER JOIN italianword AS iw ON t.italianWordId = iw.id " +
            "WHERE iw.word = :italianWord")
    List<HungarianWord> findHungarianTranslationsFor(String italianWord);

    @Query("SELECT hw.id, hw.word, hw.favorite FROM hungarianword AS hw " +
            "WHERE word = :hungarianWord")
    List<HungarianWord> findHungarianWord(String hungarianWord);

    @Query("SELECT hw.id, hw.word, hw.favorite FROM hungarianword AS hw " +
            "ORDER BY RANDOM() LIMIT :resultCount")
    List<HungarianWord> findRandomHungarianWords(int resultCount);

    @Query("SELECT hw.id, hw.word, hw.favorite FROM hungarianword AS hw " +
            "WHERE hw.favorite = 1")
    List<HungarianWord> findFavoriteHungarianWords();
}