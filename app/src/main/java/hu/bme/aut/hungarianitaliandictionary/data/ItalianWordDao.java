package hu.bme.aut.hungarianitaliandictionary.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ItalianWordDao {

    @Insert
    void insert(ItalianWord italianWord);

    @Update
    void update(ItalianWord... italianWords);

    @Delete
    void delete(ItalianWord... italianWords);

    @Query("SELECT iw.id, iw.word, iw.favorite FROM italianword AS iw")
    List<ItalianWord> getAllItalianWords();

    @Query("SELECT iw.id, iw.word, iw.favorite FROM italianword AS iw " +
            "INNER JOIN translation AS t ON t.italianWordId = iw.id " +
            "INNER JOIN hungarianword AS hw ON t.hungarianWordId = hw.id " +
            "WHERE hw.word = :hungarianWord")
    List<ItalianWord> findItalianTranslationsFor(String hungarianWord);

    @Query("SELECT iw.id, iw.word, iw.favorite FROM italianword AS iw " +
            "WHERE word = :italianWord")
    List<ItalianWord> findItalianWord(String italianWord);

    @Query("SELECT iw.id, iw.word, iw.favorite FROM italianword AS iw " +
            "ORDER BY RANDOM() LIMIT :resultCount")
    List<ItalianWord> findRandomItalianWords(int resultCount);

    @Query("SELECT iw.id, iw.word, iw.favorite FROM italianword AS iw " +
            "WHERE iw.favorite = 1")
    List<ItalianWord> findFavoriteItalianWords();
}