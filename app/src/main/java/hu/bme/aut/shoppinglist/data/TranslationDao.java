package hu.bme.aut.shoppinglist.data;

import android.arch.persistence.room.Dao;
        import android.arch.persistence.room.Insert;
        import android.arch.persistence.room.Query;
        import java.util.List;

@Dao
public interface TranslationDao {

    @Insert
    void insert(Translation translation);

    @Query("SELECT * FROM italianword INNER JOIN translation ON " +
            "italianword.id=translation.italianWordId WHERE " +
            "translation.hungarianWordId=:hungarianId")
            List<ItalianWord> getItalianTranslationsFor(final int hungarianId);

    @Query("SELECT * FROM hungarianword INNER JOIN translation ON " +
            "hungarianword.id=translation.hungarianWordId WHERE " +
            "translation.italianWordId=:italianId")
            List<HungarianWord> getHungarianTranslationsFor(final int italianId);
}