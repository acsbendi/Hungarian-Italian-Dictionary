package hu.bme.aut.hungarianitaliandictionary;

import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import hu.bme.aut.hungarianitaliandictionary.adapters.FragmentViewPagerAdapter;
import hu.bme.aut.hungarianitaliandictionary.adapters.TranslationAdapter;
import hu.bme.aut.hungarianitaliandictionary.backgroundtasks.hungarian.FavoriteHungarianWordFinder;
import hu.bme.aut.hungarianitaliandictionary.backgroundtasks.italian.FavoriteItalianWordFinder;
import hu.bme.aut.hungarianitaliandictionary.backgroundtasks.translation.HungarianToItalianTranslationFinder;
import hu.bme.aut.hungarianitaliandictionary.backgroundtasks.hungarian.HungarianWordChangeListener;
import hu.bme.aut.hungarianitaliandictionary.backgroundtasks.translation.ItalianToHungarianTranslationFinder;
import hu.bme.aut.hungarianitaliandictionary.backgroundtasks.italian.ItalianWordChangeListener;
import hu.bme.aut.hungarianitaliandictionary.backgroundtasks.hungarian.RandomHungarianWordFinder;
import hu.bme.aut.hungarianitaliandictionary.backgroundtasks.italian.RandomItalianWordFinder;
import hu.bme.aut.hungarianitaliandictionary.backgroundtasks.translation.TranslationAdder;
import hu.bme.aut.hungarianitaliandictionary.backgroundtasks.translation.TranslationChecker;
import hu.bme.aut.hungarianitaliandictionary.data.DictionaryDatabase;
import hu.bme.aut.hungarianitaliandictionary.data.entities.HungarianWord;
import hu.bme.aut.hungarianitaliandictionary.data.entities.ItalianWord;
import hu.bme.aut.hungarianitaliandictionary.data.entities.TranslationData;
import hu.bme.aut.hungarianitaliandictionary.fragments.NewTranslationDialogFragment;

public class MainActivity
        extends AppCompatActivity
        implements NewTranslationDialogFragment.NewTranslationDialogListener {

    private DictionaryDatabase database;
    private FragmentViewPagerAdapter fragmentViewPagerAdapter;

    public FragmentViewPagerAdapter getFragmentViewPagerAdapter() {
        return fragmentViewPagerAdapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentViewPagerAdapter = new FragmentViewPagerAdapter(getBaseContext(), getSupportFragmentManager());

        initLayout();

        initDatabase();
    }

    private void initLayout() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NewTranslationDialogFragment().show(getSupportFragmentManager(), NewTranslationDialogFragment.TAG);
            }
        });

        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(fragmentViewPagerAdapter);
    }

    private void initDatabase(){
        database = Room.databaseBuilder(
                getApplicationContext(),
                DictionaryDatabase.class,
                "dictionary")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.deleteAllItems){
            onAllItemsDeleteButtonClick();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTranslationCreated(final TranslationData translationData) {
        TranslationAdder translationAdder = new TranslationAdder(translationData, database);

        translationAdder.execute();
    }

    private void onAllItemsDeleteButtonClick(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("All items will be deleted. Are you sure you want to do this?");
        builder.setPositiveButton("yesssss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new AsyncTask<Void, Void, Void>(){

                    @Override
                    protected Void doInBackground(Void... voids) {

                        List<ItalianWord> allItalianWords = database.italianWordDao().getAllItalianWords();

                        database.italianWordDao().delete(allItalianWords.toArray(new ItalianWord[allItalianWords.size()]));

                        List<HungarianWord> allHungarianWords = database.hungarianWordDao().getAllHungarianWords();

                        database.hungarianWordDao().delete(allHungarianWords.toArray(new HungarianWord[allHungarianWords.size()]));

                        return null;
                    }
                }.execute();

            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }


    public List<ItalianWord> findItalianTranslationsFor(String hungarianWord) {
        try {
            HungarianToItalianTranslationFinder finder = new HungarianToItalianTranslationFinder(hungarianWord, database);
            finder.execute();

            return finder.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<HungarianWord> findHungarianTranslationsFor(String italianWord) {
        try {
            ItalianToHungarianTranslationFinder finder = new ItalianToHungarianTranslationFinder(italianWord, database);
            finder.execute();

            return finder.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public TranslationAdapter.WordChangeListener<HungarianWord> getNewHungarianWordChangeListener(){
        return new HungarianWordChangeListener(database);
    }

    public TranslationAdapter.WordChangeListener<ItalianWord> getNewItalianWordChangeListener(){
        return new ItalianWordChangeListener(database);
    }

    public List<HungarianWord> getRandomHungarianWords(int resultCount){
        try {
            RandomHungarianWordFinder finder = new RandomHungarianWordFinder(resultCount, database);
            finder.execute();

            return finder.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<ItalianWord> getRandomItalianWords(int resultCount){
        try {
            RandomItalianWordFinder finder = new RandomItalianWordFinder(resultCount, database);
            finder.execute();

            return finder.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<ItalianWord> getFavoriteItalianWords(){
        try {
            FavoriteItalianWordFinder finder = new FavoriteItalianWordFinder(database);
            finder.execute();

            return finder.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<HungarianWord> getFavoriteHungarianWords(){
        try {
            FavoriteHungarianWordFinder finder = new FavoriteHungarianWordFinder(database);
            finder.execute();

            return finder.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean correctTranslation(String hungarianWord, String italianWord){
        try {
            TranslationChecker finder =
                    new TranslationChecker(hungarianWord, italianWord, database);
            finder.execute();

            return finder.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}
