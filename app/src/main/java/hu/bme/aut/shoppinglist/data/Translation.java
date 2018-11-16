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
    public final Long italianWordId;
    public final Long hungarianWordId;

    public Translation(final long italianWordId, final long hungarianWordId) {
        this.italianWordId = italianWordId;
        this.hungarianWordId = hungarianWordId;
    }
}
