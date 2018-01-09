package pl.devone.shoppinglist.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import pl.devone.shoppinglist.R;
import pl.devone.shoppinglist.fragments.ShoppingListFragment;
import pl.devone.shoppinglist.handlers.DatabaseHandler;
import pl.devone.shoppinglist.models.ShoppingList;

public class EntryActivity extends AppCompatActivity implements ShoppingListFragment.OnListFragmentInteractionListener {

    private ShoppingListFragment mShoppingListFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(R.string.title_activity_shopping_list);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EntryActivity.this, ShoppingListActivity.class);
//                ShoppingList shoppingList = new ShoppingList();
//                shoppingList.setUid(mShoppingListFragment.getShoppingLists().size() + 1);
//                intent.putExtra("shopping_list", shoppingList);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof ShoppingListFragment) {
            mShoppingListFragment = (ShoppingListFragment) fragment;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(ShoppingList item) {
        Intent intent = new Intent(EntryActivity.this, ShoppingListActivity.class);
        intent.putExtra("shopping_list", item);
        startActivity(intent);
    }
}
