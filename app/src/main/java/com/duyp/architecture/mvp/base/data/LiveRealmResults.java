package com.duyp.architecture.mvp.base.data;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Pair;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.RealmModel;
import io.realm.RealmResults;

/**
 * Created by duypham on 9/18/17.
 * Mix version of android {@link LiveData} for {@link RealmResults}
 */

public class LiveRealmResults<T extends RealmModel> extends LiveData<Pair<RealmResults<T>, OrderedCollectionChangeSet>> {

    private RealmResults<T> mRealmResults;

    private final OrderedRealmCollectionChangeListener<RealmResults<T>> listener = (realmResults, changeSet) -> {
        this.updateValue(new Pair<>(realmResults, changeSet));
    };

    public LiveRealmResults(@NonNull RealmResults<T> realmResults) {
        updateValue(new Pair<>(realmResults, null));
    }

    public RealmResults<T> getData() {
        return mRealmResults;
    }

    @Override
    protected void onActive() {
        super.onActive();
        mRealmResults.addChangeListener(listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        mRealmResults.removeChangeListener(listener);
    }


    protected void updateValue(Pair<RealmResults<T>, OrderedCollectionChangeSet> value) {
        try {
            this.setValue(value);
        } catch (IllegalStateException e) {
            // if we can't set value (since current thread is a background thread), we must call postValue() instead
            // java.lang.IllegalStateException: Cannot invoke setValue on a background thread
            this.postValue(value);
        }
    }

    @Override
    protected void setValue(Pair<RealmResults<T>, OrderedCollectionChangeSet> value) {
        super.setValue(value);
        mRealmResults = value.first;
    }

    @Override
    protected void postValue(Pair<RealmResults<T>, OrderedCollectionChangeSet> value) {
        super.postValue(value);
        mRealmResults = value.first;
    }

    /**
     * Convert {@link RealmResults} to Live data {@link LiveRealmResults}
     * @param realmResults input realm results
     * @param <T> type of model
     * @return live data version of given realm results
     */
    public static <T extends RealmModel> LiveData<Pair<RealmResults<T>, OrderedCollectionChangeSet>> asLiveData(RealmResults<T> realmResults){
        return new LiveRealmResults<T>(realmResults);
    }
}