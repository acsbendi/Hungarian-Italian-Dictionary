package hu.bme.aut.hungarianitaliandictionary.data.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "italianword",
        indices = {@Index(value = "word",
                unique = true)})
public class ItalianWord extends Word{

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    public Long id;

    public ItalianWord(String word){
        super(word);
    }
}



