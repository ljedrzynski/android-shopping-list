package pl.devone.shoppinglist.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import pl.devone.shoppinglist.R;
import pl.devone.shoppinglist.fragments.adapters.ShoppingListItemRecyclerViewAdapter;
import pl.devone.shoppinglist.handlers.FirebaseHandler;
import pl.devone.shoppinglist.handlers.PreferenceHandler;
import pl.devone.shoppinglist.models.ShoppingList;
import pl.devone.shoppinglist.models.ShoppingListItem;
import pl.devone.shoppinglist.utils.DateUtils;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ShoppingListItemFragment extends Fragment {

    private final static String TAG = ShoppingListItemFragment.class.getSimpleName();
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private ShoppingList mShoppingList;

    public ShoppingListItemFragment() {
    }

    public static ShoppingListItemFragment newInstance(int columnCount) {
        ShoppingListItemFragment fragment = new ShoppingListItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        mShoppingList = (ShoppingList) getActivity().getIntent().getSerializableExtra("shopping_list");
        if (mShoppingList == null) {
            mShoppingList = new ShoppingList();
            mShoppingList.setCreatedAt(DateUtils.formatDateToString(new Date(), getContext()));
            mShoppingList.setItems(new ArrayList<ShoppingListItem>());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shopping_item_list, container, false);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();

            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new ShoppingListItemRecyclerViewAdapter(mShoppingList.getItems()));
            mRecyclerView = recyclerView;

            if (CollectionUtils.isEmpty(mShoppingList.getItems())) {
                addNewItem();
            }
        }
        return view;
    }


    public void addNewItem() {
        ShoppingListItem shoppingListItem = new ShoppingListItem();
        shoppingListItem.setNo(mShoppingList.getItems().size() + 1);
        shoppingListItem.setShoppingList(mShoppingList);
        mShoppingList.getItems().add(shoppingListItem);
        getAdapter().notifyDataSetChanged();
    }

    public void save() {
        final ShoppingListItemRecyclerViewAdapter adapter = (ShoppingListItemRecyclerViewAdapter) mRecyclerView.getAdapter();

        OnCompleteListener onCompleteListener = new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                adapter.notifyDataSetSaved();
            }
        };

        if (PreferenceHandler.isAutoDeleteMode(getContext()) && mShoppingList.allItemsDone()) {
            FirebaseHandler.getRef("shoppingLists")
                    .child(String.valueOf(mShoppingList.getUid()))
                    .removeValue()
                    .addOnCompleteListener(onCompleteListener);
        } else {
            DatabaseReference databaseReference = FirebaseHandler.getRef("shoppingLists");
            if (mShoppingList.getUid() == null) {
                mShoppingList.setUid(databaseReference.push().getKey());
            }

            Map<String, Object> userUpdates = new HashMap<>();
            userUpdates.put(String.valueOf(mShoppingList.getUid()), mShoppingList);
            FirebaseHandler.getRef("shoppingLists")
                    .updateChildren(userUpdates)
                    .addOnCompleteListener(onCompleteListener);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private ShoppingListItemRecyclerViewAdapter getAdapter() {
        return (ShoppingListItemRecyclerViewAdapter) mRecyclerView.getAdapter();
    }


    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(ShoppingListItem item);
    }
}
