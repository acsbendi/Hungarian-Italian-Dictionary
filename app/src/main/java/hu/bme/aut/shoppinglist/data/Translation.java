package hu.bme.aut.shoppinglist.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "translation",
        primaryKeys = { "italianWordId", "hungarianWordId" },
        foreignKeys = {
                @ForeignKey(onDelete = CASCADE,
                        entity = HungarianWord.class,
                        parentColumns = "id",
                        childColumns = "hungarianWordId"),
                @ForeignKey(onDelete = CASCADE,
                        entity = ItalianWord.class,
                        parentColumns = "id",
                        childColumns = "italianWordId")
        })
public class Translation{
    @NonNull
    public final Long italianWordId;
    @NonNull
    public final Long hungarianWordId;

    public Translation(final long italianWordId, final long hungarianWordId) {
        this.italianWordId = italianWordId;
        this.hungarianWordId = hungarianWordId;
    }
}
