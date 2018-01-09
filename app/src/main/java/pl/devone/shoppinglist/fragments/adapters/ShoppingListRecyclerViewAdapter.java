package pl.devone.shoppinglist.fragments.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pl.devone.shoppinglist.R;
import pl.devone.shoppinglist.fragments.ShoppingListFragment;
import pl.devone.shoppinglist.handlers.PreferenceHandler;
import pl.devone.shoppinglist.models.ShoppingList;
import pl.devone.shoppinglist.models.ShoppingListItem;
import pl.devone.shoppinglist.utils.DateUtils;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ShoppingListItem} and makes a call to the
 * specified {@link ShoppingListFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data
 */
public class ShoppingListRecyclerViewAdapter extends RecyclerView.Adapter<ShoppingListRecyclerViewAdapter.ViewHolder> {

    private final List<ShoppingList> mValues;
    private final ShoppingListFragment.OnListFragmentInteractionListener mListener;
    private final Context mContext;

    public ShoppingListRecyclerViewAdapter(Context context, List<ShoppingList> items, ShoppingListFragment.OnListFragmentInteractionListener listener) {
        mContext = context;
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopping_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ShoppingList shoppingList = mValues.get(position);
        holder.mItem = shoppingList;
        holder.mIdView.setText(String.valueOf(shoppingList.getNo()));
        holder.mCreateDate.setText(DateUtils.getDateFormat(mContext).format(shoppingList.getCreatedAt()));
        holder.mDone.setVisibility(shoppingList.isDone() ? View.VISIBLE : View.INVISIBLE);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mCreateDate;
        public final ImageView mDone;
        private ShoppingList mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.tv_id);
            mCreateDate = (TextView) view.findViewById(R.id.tv_start_dt);
            mDone = (ImageView) view.findViewById(R.id.iv_done);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdView.getText() + "'";
        }
    }
}
