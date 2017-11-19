package pl.devone.shoppinglist.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pl.devone.shoppinglist.R;
import pl.devone.shoppinglist.fragments.adapters.ShoppingListRecyclerViewAdapter;
import pl.devone.shoppinglist.handlers.DatabaseHandler;
import pl.devone.shoppinglist.models.ShoppingList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ShoppingListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private List<ShoppingList> mShoppingLists;
    private RecyclerView mRecyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ShoppingListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ShoppingListFragment newInstance(int columnCount) {
        ShoppingListFragment fragment = new ShoppingListFragment();
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
    }

    @Override
    public void onResume() {
        super.onResume();

        loadData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shopping_item_list, container, false);
        if (view instanceof RecyclerView) {
            mRecyclerView = (RecyclerView) view;
        }
        initRecyclerView();

        return view;
    }

    private void initRecyclerView() {
        loadData();

        Context context = mRecyclerView.getContext();
        if (mColumnCount <= 1) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }

        mRecyclerView.setAdapter(new ShoppingListRecyclerViewAdapter(mShoppingLists, mListener));
    }

    private void loadData() {
        List<ShoppingList> shoppingLists = DatabaseHandler.getHandler(this.getContext()).getShoppingLists();
        if (mShoppingLists == null) {
            mShoppingLists = DatabaseHandler.getHandler(this.getContext()).getShoppingLists();
        } else {
            for (ShoppingList shoppingList : shoppingLists) {
                if (!mShoppingLists.contains(shoppingList)) {
                    mShoppingLists.add(shoppingList);
                }
            }

            mRecyclerView.getAdapter().notifyDataSetChanged();
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

    public List<ShoppingList> getShoppingLists() {
        return mShoppingLists;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(ShoppingList item);
    }
}
