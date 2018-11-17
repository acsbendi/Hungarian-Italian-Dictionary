package hu.bme.aut.shoppinglist;

import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.List;
import java.util.concurrent.ExecutionException;

import hu.bme.aut.shoppinglist.adapter.TranslationAdapter;
import hu.bme.aut.shoppinglist.backgroundtasks.HungarianToItalianTranslationFinder;
import hu.bme.aut.shoppinglist.backgroundtasks.TranslationAdder;
import hu.bme.aut.shoppinglist.data.DictionaryDatabase;
import hu.bme.aut.shoppinglist.data.HungarianWord;
import hu.bme.aut.shoppinglist.data.ItalianWord;
import hu.bme.aut.shoppinglist.data.TranslationData;
import hu.bme.aut.shoppinglist.fragments.NewTranslationDialogFragment;

public class MainActivity extends AppCompatActivity
        implements NewTranslationDialogFragment.NewTranslationDialogListener {

    private RecyclerView recyclerView;
    private TranslationAdapter adapter;
    private ImageButton searchButton;
    private EditText searchEditText;

    private DictionaryDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchButton = findViewById(R.id.searchButton);
        searchEditText = findViewById(R.id.searchEditText);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadItalianTranslationsInBackground(searchEditText.getText().toString());
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NewTranslationDialogFragment().show(getSupportFragmentManager(), NewTranslationDialogFragment.TAG);
            }
        });

        database = Room.databaseBuilder(
                getApplicationContext(),
                DictionaryDatabase.class,
                "dictionary")
                .fallbackToDestructiveMigration()
                .build();

        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.MainRecyclerView);
        adapter = new TranslationAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void loadItalianTranslationsInBackground(String hungarianWord) {
        HungarianToItalianTranslationFinder finder = new HungarianToItalianTranslationFinder(hungarianWord, database);
        finder.execute();
        try {
            List<ItalianWord> italianTranslations = finder.get();

            for(ItalianWord italianWord : italianTranslations)
                adapter.addItem(italianWord);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            onLogDatabaseButtonClick();
            return true;
        }

        if(id == R.id.delete_all){
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

    private void onLogDatabaseButtonClick(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("All items will be logged. Are you sure you want to do this?");
        builder.setPositiveButton("yesssss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new AsyncTask<Void, Void, Void>(){

                    @Override
                    protected Void doInBackground(Void... voids) {
                        List<HungarianWord> hungarianWords = database.hungarianWordDao().getAllHungarianWords();
                        List<ItalianWord> italianWords = database.italianWordDao().getAllItalianWords();

                        for(HungarianWord hungarianWord : hungarianWords)
                            Log.d("hungarian word", hungarianWord.word + " " + String.valueOf(hungarianWord.id));

                        for(ItalianWord italianWord : italianWords)
                            Log.d("italian word", italianWord.word + " " + String.valueOf(italianWord.id));


                        return null;
                    }
                }.execute();

            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}
