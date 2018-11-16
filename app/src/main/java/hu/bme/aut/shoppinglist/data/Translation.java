package hu.bme.aut.shoppinglist.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

@Entity(tableName = "translation",
        primaryKeys = { "italianWordId", "hungarianWordId" },
        foreignKeys = {
                @ForeignKey(entity = HungarianWord.class,
                        parentColumns = "id",
                        childColumns = "hungarianWordId"),
                @ForeignKey(entity = ItalianWord.class,
                        parentColumns = "id",
                        childColumns = "italianWordId")
        })
public class Translation {
    public final int italianWordId;
    public final int hungarianWordId;

    public Translation(final int italianWordId, final int hungarianWordId) {
        this.italianWordId = italianWordId;
        this.hungarianWordId = hungarianWordId;
    }
}
