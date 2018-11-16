package hu.bme.aut.shoppinglist.data;


import android.arch.persistence.room.ColumnInfo;

public class Word {

    @ColumnInfo(name = "word")
    public final String word;

    Word(String word){
        this.word = word;
    }
}
