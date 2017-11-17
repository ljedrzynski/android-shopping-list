package pl.devone.shoppinglist.activities;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import pl.devone.shoppinglist.R;
import pl.devone.shoppinglist.fragments.ShoppingListItemFragment;
import pl.devone.shoppinglist.fragments.dummy.DummyContent;

public class ShoppingListActivity extends AppCompatActivity implements ShoppingListItemFragment.OnListFragmentInteractionListener {

    private ShoppingListItemFragment mShoppingListItemFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_add_item:
                    mShoppingListItemFragment.addEmptyItem();
                    return true;
//                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
//                    return true;
//                case R.id.navigation_notifications:
//                    mTextMessage.setText(R.string.title_notifications);
//                    return true;
            }
            return false;
        }
    };

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof ShoppingListItemFragment) {
            mShoppingListItemFragment = (ShoppingListItemFragment) fragment;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.ShoppingListItem item) {

    }
}
