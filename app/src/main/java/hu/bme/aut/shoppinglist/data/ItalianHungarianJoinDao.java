package hu.bme.aut.shoppinglist.data;

import android.arch.persistence.room.Dao;
        import android.arch.persistence.room.Insert;
        import android.arch.persistence.room.Query;
        import java.util.List;

@Dao
public interface ItalianHungarianJoinDao {

    @Insert
    void insert(ItalianHungarianJoin italianHungarianJoin);

    @Query("SELECT * FROM italianword INNER JOIN italian_hungarian_join ON " +
            "italianword.id=italian_hungarian_join.italianWordId WHERE " +
            "italian_hungarian_join.hungarianWordId=:hungarianId")
            List<ItalianWord> getItalianTranslationsFor(final int hungarianId);

    @Query("SELECT * FROM hungarianword INNER JOIN italian_hungarian_join ON " +
            "hungarianword.id=italian_hungarian_join.hungarianWordId WHERE " +
            "italian_hungarian_join.italianWordId=:italianId")
            List<HungarianWord> getHungarianTranslationsFor(final int italianId);
}