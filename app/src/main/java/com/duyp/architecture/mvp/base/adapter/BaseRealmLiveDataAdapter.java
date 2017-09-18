package com.duyp.architecture.mvp.base.adapter;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;

import com.duyp.architecture.mvp.base.data.LiveRealmResults;
import com.duyp.architecture.mvp.base.interfaces.ListData;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollection;
import io.realm.RealmObject;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;
import lombok.Getter;

/**
 * Created by duypham on 9/18/17.
 * Mix version of {@link RealmRecyclerViewAdapter}
 * used with android LiveData
 */

public abstract class BaseRealmLiveDataAdapter<T extends RealmObject, VH extends RecyclerView.ViewHolder>
    extends RecyclerView.Adapter<VH> implements ListData{

    private boolean hasAutoUpdates = true;
    private boolean updateOnModification = true;

    @Nullable
    private LiveRealmResults<T> adapterData;

    @NonNull
    private final LifecycleOwner lifecycleOwner;

    @Getter
    private final Context context;

    protected final LayoutInflater mInflater;

    private Observer<Pair<RealmResults<T>, OrderedCollectionChangeSet>> observer;

    protected Observer<Pair<RealmResults<T>, OrderedCollectionChangeSet>> createObserver() {
        return pair -> {
            if (pair == null) {
                notifyDataSetChanged();
                return;
            }
            OrderedCollectionChangeSet changeSet = pair.second;
            // null Changes means the async query returns the first time.
            if (changeSet == null) {
                notifyDataSetChanged();
                return;
            }
            // For deletions, the adapter has to be notified in reverse order.
            OrderedCollectionChangeSet.Range[] deletions = changeSet.getDeletionRanges();
            for (int i = deletions.length - 1; i >= 0; i--) {
                OrderedCollectionChangeSet.Range range = deletions[i];
                notifyItemRangeRemoved(range.startIndex, range.length);
            }

            OrderedCollectionChangeSet.Range[] insertions = changeSet.getInsertionRanges();
            for (OrderedCollectionChangeSet.Range range : insertions) {
                notifyItemRangeInserted(range.startIndex, range.length);
            }

            if (!updateOnModification) {
                return;
            }

            OrderedCollectionChangeSet.Range[] modifications = changeSet.getChangeRanges();
            for (OrderedCollectionChangeSet.Range range : modifications) {
                notifyItemRangeChanged(range.startIndex, range.length);
            }
        };
    }

    public BaseRealmLiveDataAdapter(Context context, @NonNull LifecycleOwner owner) {
        this.lifecycleOwner = owner;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    /**
     * see {@link #setData(LiveRealmResults, boolean, boolean)}
     * @param data data
     */
    private void setData(@Nullable LiveRealmResults<T> data) {
        setData(data, true, true);
    }

    /**
     * @param data collection data to be used by this adapter.
     * @param autoUpdate when it is {@code false}, the adapter won't be automatically updated when collection data
     *                   changes.
     * @param updateOnModification when it is {@code true}, this adapter will be updated when deletions, insertions or
     *                             modifications happen to the collection data. When it is {@code false}, only
     *                             deletions and insertions will trigger the updates. This param will be ignored if
     *                             {@code autoUpdate} is {@code false}.
     */
    private void setData(@Nullable LiveRealmResults<T> data, boolean autoUpdate, boolean updateOnModification) {
        this.adapterData = data;
        this.hasAutoUpdates = autoUpdate;
        this.updateOnModification = updateOnModification;
        if (hasAutoUpdates && observer == null) {
            observer = createObserver();
        } else {
            observer = null;
        }
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (hasAutoUpdates && isDataValid()) {
            //noinspection ConstantConditions
            addListener(adapterData);
        }
    }

    @Override
    public void onDetachedFromRecyclerView(final RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        if (hasAutoUpdates && isDataValid()) {
            //noinspection ConstantConditions
            removeListener(adapterData);
        }
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        //check what type of view our position is
        //it's one of our items, display as required
        T item = getItem(position);
        if (item != null) {
            bindHolder(holder, item);
        }
    }

    protected abstract void bindHolder(VH viewHolder, @NonNull T item);

    /**
     * Returns the current ID for an item. Note that item IDs are not stable so you cannot rely on the item ID being the
     * same after notifyDataSetChanged() or {@link #updateData(LiveRealmResults)} has been called.
     *
     * @param index position of item in the adapter.
     * @return current item ID.
     */
    @Override
    public long getItemId(final int index) {
        return index;
    }

    @Override
    public int getItemCount() {
        //noinspection ConstantConditions
        return isDataValid() ? adapterData.getData().size() : 0;
    }

    /**
     * Returns the item associated with the specified position.
     * Can return {@code null} if provided Realm instance by {@link OrderedRealmCollection} is closed.
     *
     * @param index index of the item.
     * @return the item at the specified position, {@code null} if adapter data is not valid.
     */
    @SuppressWarnings("WeakerAccess")
    @Nullable
    public T getItem(int index) {
        //noinspection ConstantConditions
        return isDataValid() ? adapterData.getData().get(index) : null;
    }

    /**
     * Returns data associated with this adapter.
     *
     * @return adapter data.
     */
    @Nullable
    public RealmResults<T> getData() {
        if (isDataValid()) {
            // noinspection ConstantConditions
            return adapterData.getValue().first;
        }
        return null;
    }

    /**
     * Updates the data associated to the Adapter. Useful when the query has been changed.
     * If the query does not change you might consider using the automaticUpdate feature.
     *
     * @param data the new {@link OrderedRealmCollection} to display.
     */
    @SuppressWarnings("WeakerAccess")
    public void updateData(@Nullable LiveRealmResults<T> data) {

        if (data != null && !data.equals(adapterData)) {

            if (isDataValid()) {
                //noinspection ConstantConditions
                removeListener(adapterData);
            }

            setData(data, hasAutoUpdates, updateOnModification);

            if (hasAutoUpdates) {
                addListener(data);
            }
            notifyDataSetChanged();
        } else if (data == null) {
            notifyDataSetChanged();
        }
    }

    private void addListener(LiveRealmResults<T> data) {
        if (observer != null) {
            data.observe(lifecycleOwner, observer);
        }
    }

    private void removeListener(@NonNull LiveRealmResults<T> adapterData) {
        if (adapterData.hasObservers() && observer != null) {
            adapterData.removeObserver(observer);
        }
    }

    private boolean isDataValid() {
        return adapterData != null && adapterData.getData() != null && adapterData.getData().isManaged();
    }

    @Override
    public boolean isDataEmpty() {
        return getItemCount() == 0;
    }
}
