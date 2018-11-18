package hu.bme.aut.hungarianitaliandictionary.data.entities;


import android.arch.persistence.room.ColumnInfo;

public class Word {

    @ColumnInfo(name = "word")
    public final String word;

    @ColumnInfo(name = "favorite")
    public boolean favorite;

    Word(String word){
        this.word = word;
        this.favorite = false;
    }
}
