package hu.bme.aut.shoppinglist.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "italianword")
public class ItalianWord extends Word{

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    public Long id;
}