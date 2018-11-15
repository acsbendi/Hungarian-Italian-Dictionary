package hu.bme.aut.shoppinglist.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

@Database(
        entities = {ShoppingItem.class},
        version = 1,
        exportSchema = false
)
@TypeConverters(value = {ShoppingItem.Category.class})
public abstract class ShoppingListDatabase extends RoomDatabase {
    public abstract ShoppingItemDao shoppingItemDao();
}
