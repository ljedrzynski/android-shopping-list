package pl.devone.shoppinglist.fragments.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import pl.devone.shoppinglist.R;
import pl.devone.shoppinglist.fragments.ShoppingListItemFragment.OnListFragmentInteractionListener;
import pl.devone.shoppinglist.handlers.PreferenceHandler;
import pl.devone.shoppinglist.models.ShoppingListItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ShoppingListItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ShoppingListItemRecyclerViewAdapter extends RecyclerView.Adapter<ShoppingListItemRecyclerViewAdapter.ViewHolder> {

    private final List<ShoppingListItem> mValues;
    private boolean save = false;

    public ShoppingListItemRecyclerViewAdapter(List<ShoppingListItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopping_item, parent, false);
        return new ViewHolder(view);
    }

    public void notifyDataSetSaved() {
        save = true;
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ShoppingListItem shoppingListItem = mValues.get(position);
        holder.setItem(shoppingListItem);

        if (save) {
            holder.forceSave();
            save = false;
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mNameView;
        public final TextView mQuantityView;
        public final TextView mPriceView;
        public final CheckBox mDoneCb;
        public final EditText mNameEditView;
        public final EditText mQuantityEditView;
        public final EditText mPriceEditView;
        private ShoppingListItem mItem;
        private HolderFieldSaveHandler mNameFieldSaveHandler;
        private HolderFieldSaveHandler mQuantityFieldSaveHandler;
        private HolderFieldSaveHandler mPriceFieldSaveHandler;

        interface HolderFieldSaveHandler {
            void onSave();
        }

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
            initHandlers();
            initListeners();
        }

        public void setItem(ShoppingListItem shoppingListItem) {
            mItem = shoppingListItem;
            if (mItem != null) {
                mIdView.setText(String.valueOf(mItem.getNo()));
                mDoneCb.setChecked(mItem.isDone());
                mNameView.setText(mItem.getName());
                mQuantityView.setText(String.valueOf(mItem.getQuantity()));
                mPriceView.setText(String.valueOf(mItem.getPrice()));
                mNameEditView.setText(mItem.getName());
                mQuantityEditView.setText(String.valueOf(mItem.getQuantity()));
                mPriceEditView.setText(String.valueOf(mItem.getPrice()));
            }
        }

        public void forceSave() {
            saveField(mNameView, mNameEditView, mNameFieldSaveHandler);
            saveField(mPriceView, mPriceEditView, mPriceFieldSaveHandler);
            saveField(mQuantityView, mQuantityEditView, mQuantityFieldSaveHandler);
        }

        private void initHandlers() {
            mNameFieldSaveHandler = new HolderFieldSaveHandler() {
                @Override
                public void onSave() {
                    String temp = mNameEditView.getText().toString();
                    mItem.setName(temp);
                    mNameView.setText(temp);
                }
            };
            mQuantityFieldSaveHandler = new HolderFieldSaveHandler() {
                @Override
                public void onSave() {
                    String temp = mQuantityEditView.getText().toString();
                    if (!temp.isEmpty()) {
                        mItem.setQuantity(Integer.valueOf(temp));
                        mQuantityView.setText(temp);
                    }
                }
            };
            mPriceFieldSaveHandler = new HolderFieldSaveHandler() {
                @Override
                public void onSave() {
                    String temp = mPriceEditView.getText().toString();
                    if (!temp.isEmpty()) {
                        mItem.setPrice(Double.valueOf(temp));
                        mPriceView.setText(temp);
                    }
                }
            };
        }

        private void initListeners() {
            mDoneCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mItem.setDone(mDoneCb.isChecked());
                }
            });
            mNameView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makeFieldEditable(true, mNameView, mNameEditView);
                }
            });
            mNameEditView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        saveField(mNameView, mNameEditView, mNameFieldSaveHandler);
                    }
                }
            });
            mQuantityView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makeFieldEditable(true, mQuantityView, mQuantityEditView);
                }
            });
            mQuantityEditView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        saveField(mQuantityView, mQuantityEditView, mQuantityFieldSaveHandler);
                    }
                }
            });
            mPriceView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makeFieldEditable(true, mPriceView, mPriceEditView);
                }
            });
            mPriceEditView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        saveField(mPriceView, mPriceEditView, mPriceFieldSaveHandler);
                    }
                }
            });
        }


        private static void makeFieldEditable(boolean editable, TextView tv, EditText et) {
            tv.setVisibility(editable ? View.GONE : View.VISIBLE);
            et.setVisibility(editable ? View.VISIBLE : View.GONE);
        }

        private static void saveField(TextView tv, EditText et, HolderFieldSaveHandler onSaveHandler) {
            makeFieldEditable(false, tv, et);
            if (onSaveHandler != null) {
                onSaveHandler.onSave();
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

}
