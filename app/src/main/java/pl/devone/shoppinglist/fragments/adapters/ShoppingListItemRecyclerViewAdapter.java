package pl.devone.shoppinglist.fragments.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import pl.devone.shoppinglist.R;
import pl.devone.shoppinglist.fragments.ShoppingListItemFragment.OnListFragmentInteractionListener;
import pl.devone.shoppinglist.models.ShoppingListItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ShoppingListItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ShoppingListItemRecyclerViewAdapter extends RecyclerView.Adapter<ShoppingListItemRecyclerViewAdapter.ViewHolder> {

    private final List<ShoppingListItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    //    private ShoppingListItem mItemOnFocus;
    private ViewHolder mSelectedHolder;

    public ShoppingListItemRecyclerViewAdapter(List<ShoppingListItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopping_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.init(mValues.get(position));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    if (mSelectedHolder != null && mSelectedHolder != holder) {
                        mSelectedHolder.saveChanges();
                    }
                    holder.setReadonly(false);
                    mSelectedHolder = holder;
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
        public final TextView mNameView;
        public final TextView mQuantityView;
        public final TextView mPriceView;
        public final CheckBox mDoneCb;
        public final EditText mNameEditView;
        public final EditText mQuantityEditView;
        public final EditText mPriceEditView;
        private boolean readonly;

        private ShoppingListItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.tv_id);
            mNameView = (TextView) view.findViewById(R.id.tv_name);
            mNameEditView = (EditText) view.findViewById(R.id.et_name);
            mPriceView = (TextView) view.findViewById(R.id.tv_price);
            mPriceEditView = (EditText) view.findViewById(R.id.np_price);
            mQuantityView = (TextView) view.findViewById(R.id.tv_quantity);
            mQuantityEditView = (EditText) view.findViewById(R.id.np_quantity);
            mDoneCb = (CheckBox) view.findViewById(R.id.cb_done);
            mDoneCb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveChanges();
                }
            });
            readonly = true;
        }

        public void init(ShoppingListItem shoppingListItem) {
            mItem = shoppingListItem;
            setReadonly(readonly);

            refresh();
        }

        public void saveChanges() {
            String temp = mNameEditView.getText().toString();
            mItem.setName(temp);

            temp = mPriceEditView.getText().toString();
            if (!temp.isEmpty()) {
                mItem.setPrice(Double.valueOf(temp));
            }
            temp = mQuantityEditView.getText().toString();
            if (!temp.isEmpty()) {
                mItem.setQuantity(Integer.valueOf(temp));
            }
            mItem.setDone(mDoneCb.isChecked());
            mItem.setNew(false);

            setReadonly(true);
        }

        public void setReadonly(boolean readonly) {
            this.readonly = readonly;
            mQuantityView.setVisibility(readonly ? View.VISIBLE : View.GONE);
            mQuantityEditView.setVisibility(readonly ? View.GONE : View.VISIBLE);
            mNameView.setVisibility(readonly ? View.VISIBLE : View.GONE);
            mNameEditView.setVisibility(readonly ? View.GONE : View.VISIBLE);
            mPriceView.setVisibility(readonly ? View.VISIBLE : View.GONE);
            mPriceEditView.setVisibility(readonly ? View.GONE : View.VISIBLE);

            refresh();
        }

//        public boolean isValid() {
//            return !(TextUtils.isEmpty(mNameEditView.getText().toString()) && TextUtils.equals(mQuantityEditView.getText().toString(), "0.0")
//                    && TextUtils.equals(mPriceEditView.getText().toString(), "0"));
//
//        }

        private void refresh() {
            if (mItem != null) {
                mIdView.setText(String.valueOf(mItem.getId()));
                mDoneCb.setChecked(mItem.isDone());
                mIdView.setText(String.valueOf(mItem.getId()));
                mNameView.setText(mItem.getName());
                mQuantityView.setText(String.valueOf(mItem.getQuantity()));
                mPriceView.setText(String.valueOf(mItem.getPrice()));
                mNameEditView.setText(mItem.getName());
                mQuantityEditView.setText(String.valueOf(mItem.getQuantity()));
                mPriceEditView.setText(String.valueOf(mItem.getPrice()));
            }
        }

        @Override
        public String toString() {
            return "ViewHolder{" +
                    "mView=" + mView +
                    ", mIdView=" + mIdView +
                    ", mNameView=" + mNameView +
                    ", mQuantityView=" + mQuantityView +
                    ", mPriceView=" + mPriceView +
                    ", mDoneCb=" + mDoneCb +
                    ", mNameEditView=" + mNameEditView +
                    ", mQuantityEditView=" + mQuantityEditView +
                    ", mPriceEditView=" + mPriceEditView +
                    ", mItem=" + mItem +
                    '}';
        }
    }

    public ViewHolder getSelectedHolder() {
        return mSelectedHolder;
    }
}
