package hu.bme.aut.shoppinglist.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "italianword",
        foreignKeys = @ForeignKey(entity = HungarianWord.class,
                parentColumns = "id",
                childColumns = "hungarianid",
                onDelete = CASCADE))
public class ItalianWord {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    public Long id;

    @ColumnInfo(name = "word")
    public String word;
}