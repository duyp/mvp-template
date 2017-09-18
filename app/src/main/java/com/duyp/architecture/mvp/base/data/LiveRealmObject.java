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

    private final RealmChangeListener<T> listener = this::setValue;

    public LiveRealmObject(@NonNull T data) {
        setValue(data);
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

    @Override
    protected void setValue(T value) {
        super.setValue(value);
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
