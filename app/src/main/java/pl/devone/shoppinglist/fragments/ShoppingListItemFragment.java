package pl.devone.shoppinglist.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;

import pl.devone.shoppinglist.R;
import pl.devone.shoppinglist.fragments.adapters.ShoppingListItemRecyclerViewAdapter;
import pl.devone.shoppinglist.handlers.DatabaseHandler;
import pl.devone.shoppinglist.models.ShoppingList;
import pl.devone.shoppinglist.models.ShoppingListItem;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ShoppingListItemFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private ShoppingList mShoppingList;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ShoppingListItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
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
        if (mShoppingList != null && mShoppingList.getId() > 0) {
            mShoppingList.setItems(DatabaseHandler.getHandler(getContext()).getShoppingListItems(mShoppingList));
        } else {
            mShoppingList = new ShoppingList();
            mShoppingList.setCreatedAt(new Date());
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
            recyclerView.setAdapter(new ShoppingListItemRecyclerViewAdapter(mShoppingList.getItems(), mListener));

            mRecyclerView = recyclerView;

            if (mShoppingList.getItemsCount() == 0) {
                addNewItem();
            }
        }
        return view;
    }

    public void addNewItem() {
        ShoppingListItemRecyclerViewAdapter adapter = (ShoppingListItemRecyclerViewAdapter) mRecyclerView.getAdapter();
        adapter.saveChanges();

        ShoppingListItem shoppingListItem = new ShoppingListItem();
        shoppingListItem.setNo(mShoppingList.getItemsCount() + 1);
        shoppingListItem.setShoppingList(mShoppingList);
        mShoppingList.getItems().add(shoppingListItem);

        adapter.notifyDataSetChanged();
    }

    public void save() {
        ShoppingListItemRecyclerViewAdapter adapter = (ShoppingListItemRecyclerViewAdapter) mRecyclerView.getAdapter();
        adapter.saveChanges();

        DatabaseHandler.getHandler(getContext()).saveShoppingList(mShoppingList);
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


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(ShoppingListItem item);
    }
}
