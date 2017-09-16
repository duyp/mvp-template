package com.duyp.architecture.mvp.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.duyp.androidutils.adapter.BaseHeaderFooterAdapter;
import com.duyp.architecture.mvp.dagger.qualifier.ActivityContext;

import java.util.List;

/**
 * Created by phamd on 9/14/2017.
 */

public abstract class BaseRecyclerViewAdapter<T> extends BaseHeaderFooterAdapter {

    protected static final int TYPE_ITEM = 333;

    @Nullable
    private List<T> mAdapterData;

    protected final LayoutInflater mInflater;

    protected final Context context;

    public BaseRecyclerViewAdapter(@ActivityContext @NonNull Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setData(List<T> newData) {
        if (newData != null && !newData.equals(mAdapterData)) {
            mAdapterData = newData;
        }
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //check what type of view our position is
        if (isHeaderOrFooter(holder.getItemViewType())) {
            super.onBindViewHolder(holder, position);
        } else {
            //it's one of our items, display as required
            T item = getItem(position);
            if (item != null) {
                bindHolder(holder, item);
            }
        }
    }

    protected abstract void bindHolder(RecyclerView.ViewHolder viewHolder, @NonNull T item);

    /**
     * @param adapterPosition adapter position
     * @return Data at input position
     */
    @Nullable
    protected T getItem(int adapterPosition) {
        if (mAdapterData != null) {
            int realPosition = getRealItemPosition(adapterPosition);
            if (realPosition >= 0 && realPosition < mAdapterData.size()) {
                return mAdapterData.get(realPosition);
            }
        }
        return null;
    }

    protected void removeItem(int adapterPosition) {
        if (mAdapterData != null) {
            int realPosition = getRealItemPosition(adapterPosition);
            if (realPosition >= 0 && realPosition < mAdapterData.size()) {
                mAdapterData.remove(realPosition);
                notifyItemRemoved(adapterPosition);
            }
        }
    }

    protected void removeItem(T item) {
        if (mAdapterData != null) {
            mAdapterData.remove(item);
            notifyDataSetChanged();
        }
    }

    protected void addItem(T item) {
        addItem(item, true);
    }

    protected void addItem(T item, boolean notify) {
        if (mAdapterData != null) {
            mAdapterData.add(item);
            if (notify) {
                notifyDataSetChanged();
            }
        }
    }

    @Nullable
    public List<T> getData() {
        return mAdapterData;
    }

    @Override
    public int getItemCountExceptHeaderFooter() {
        return mAdapterData == null ? 0 :mAdapterData.size();
    }

    @Override
    public int getItemViewType(int position) {
        int type = super.getItemViewType(position);
        return type == TYPE_NON_FOOTER_HEADER ? TYPE_ITEM : type;
    }
}
