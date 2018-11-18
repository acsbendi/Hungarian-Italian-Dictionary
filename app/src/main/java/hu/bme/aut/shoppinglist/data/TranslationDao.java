package hu.bme.aut.shoppinglist.data;

import android.arch.persistence.room.Dao;
        import android.arch.persistence.room.Insert;
        import android.arch.persistence.room.Query;
        import java.util.List;

@Dao
public interface TranslationDao {

    @Insert
    void insert(Translation translation);
}