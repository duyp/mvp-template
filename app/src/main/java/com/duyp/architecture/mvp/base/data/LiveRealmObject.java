package com.duyp.architecture.mvp.base.data;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import io.realm.RealmChangeListener;
import io.realm.RealmObject;

/**
 * Created by duypham on 9/18/17.
 * Mix version of android {@link LiveData} for {@link RealmObject}
 */

public class LiveRealmObject<T extends RealmObject> extends LiveData<T> {

    private T mData;

    private final RealmChangeListener<T> listener = this::updateValue;

    public LiveRealmObject(@NonNull T data) {
        updateValue(data);
    }

    @Override
    protected void onActive() {
        super.onActive();
        mData.addChangeListener(listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        mData.removeChangeListener(listener);
    }

    protected void updateValue(T t) {
        try {
            this.setValue(t);
        } catch (IllegalStateException e) {
            // if we can't set value (since current thread is a background thread), we must call postValue() instead
            // java.lang.IllegalStateException: Cannot invoke setValue on a background thread
            this.postValue(t);
        }
    }

    @Override
    protected void setValue(T value) {
        super.setValue(value);
        mData = value;
    }

    @Override
    protected void postValue(T value) {
        super.postValue(value);
        mData = value;
    }

    public T getData() {
        return mData;
    }
    /**
     * Convert an {@link RealmObject} to live data {@link LiveRealmObject}
     * @param data input object
     * @param <E> type of object
     * @return Live data version of given realm object
     */
    public static <E extends RealmObject> LiveRealmObject<E> asLiveData(@NonNull E data) {
        return new LiveRealmObject<E>(data);
    }
}
