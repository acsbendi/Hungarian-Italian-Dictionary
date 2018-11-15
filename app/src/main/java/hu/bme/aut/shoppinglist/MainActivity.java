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

import java.util.Iterator;
import java.util.List;

import hu.bme.aut.shoppinglist.adapter.ShoppingAdapter;
import hu.bme.aut.shoppinglist.data.ShoppingItem;
import hu.bme.aut.shoppinglist.data.ShoppingListDatabase;
import hu.bme.aut.shoppinglist.fragments.NewShoppingItemDialogFragment;

public class MainActivity extends AppCompatActivity
        implements NewShoppingItemDialogFragment.NewShoppingItemDialogListener,
        ShoppingAdapter.ShoppingItemClickListener {

    private RecyclerView recyclerView;
    private ShoppingAdapter adapter;

    private ShoppingListDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NewShoppingItemDialogFragment().show(getSupportFragmentManager(), NewShoppingItemDialogFragment.TAG);
            }
        });

        database = Room.databaseBuilder(
                getApplicationContext(),
                ShoppingListDatabase.class,
                "shopping-list"
        ).build();

        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.MainRecyclerView);
        adapter = new ShoppingAdapter(this);
        loadItemsInBackground();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void loadItemsInBackground() {
        new AsyncTask<Void, Void, List<ShoppingItem>>() {

            @Override
            protected List<ShoppingItem> doInBackground(Void... voids) {
                return database.shoppingItemDao().getAll();
            }

            @Override
            protected void onPostExecute(List<ShoppingItem> shoppingItems) {
                adapter.update(shoppingItems);
            }
        }.execute();
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
            return true;
        }

        if(id == R.id.delete_all){
            onAllItemsDeleteButtonClick();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemChanged(final ShoppingItem item) {
        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {
                database.shoppingItemDao().update(item);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean isSuccessful) {
                Log.d("MainActivity", "ShoppingItem update was successful");
            }
        }.execute();
    }

    @Override
    public void onShoppingItemCreated(final ShoppingItem newItem) {
        new AsyncTask<Void, Void, ShoppingItem>() {

            @Override
            protected ShoppingItem doInBackground(Void... voids) {
                database.shoppingItemDao().insertAll(newItem);
                return newItem;
            }

            @Override
            protected void onPostExecute(ShoppingItem shoppingItem) {
                adapter.addItem(shoppingItem);
            }
        }.execute();
    }

    @Override
    public void onItemDeleted(final ShoppingItem item){
        new AsyncTask<ShoppingItem, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(ShoppingItem... shoppingItem) {
                database.shoppingItemDao().deleteItem(shoppingItem[0]);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean isSuccessful) {
                Log.d("MainActivity", "ShoppingItem delete was successful");
            }
        }.execute(item);
    }

    private void onAllItemsDeleteButtonClick(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("All items will be deleted. Are you sure you want to do this?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteAllItems();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void deleteAllItems(){
        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {
                for(Iterator<ShoppingItem> i = database.shoppingItemDao().getAll().iterator(); i.hasNext();)
                    database.shoppingItemDao().deleteItem(i.next());
                return true;
            }

            @Override
            protected void onPostExecute(Boolean isSuccessful) {
                Log.d("MainActivity", "Deleting all ShoppingItems was successful");
            }
        }.execute();
        adapter.deleteAllItems();
    }

    @Override
    public void editItem(ShoppingItem item) {
        NewShoppingItemDialogFragment shoppingItemDialogFragment = new NewShoppingItemDialogFragment();
        shoppingItemDialogFragment.show(getSupportFragmentManager(), NewShoppingItemDialogFragment.TAG);
        shoppingItemDialogFragment.setItemToShow(item);
    }

    @Override
    public void onShoppingItemChanged(ShoppingItem item) {
        new AsyncTask<ShoppingItem, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(ShoppingItem... shoppingItems) {
                database.shoppingItemDao().update(shoppingItems[0]);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean isSuccessful) {
                Log.d("MainActivity", "Updating ShoppingItem was successful");
            }
        }.execute(item);
        adapter.onItemUpdated(item);
    }
}
