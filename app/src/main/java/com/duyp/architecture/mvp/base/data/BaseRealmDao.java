package com.duyp.architecture.mvp.base.data;

import android.support.annotation.NonNull;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by duypham on 9/18/17.
 * Base Realm Data Access Object
 */

public interface BaseRealmDao<T extends RealmObject> {

    /**
     * Get all data as live data version
     * @return RealmResults
     */
    LiveRealmResults<T> getAll();

    /**
     * Get realm object by given id as live data version
     * @param id object id
     * @return live data Realm Object
     */
    LiveRealmObject<T> getById(@NonNull Long id);

    /**
     * Add all data into realm database
     * @param data list of data
     */
    void addAll(@NonNull List<T> data);

    /**
     * Add or update an object into realm database
     * @param item item to be added or updated
     */
    void addOrUpdate(@NonNull T item);

    /**
     * delete an item from realm database
     * @param itemId id of item to be deleted
     */
    void delete(@NonNull Long itemId);

    /**
     * Delete all item of current class from realm database
     */
    void deleteAll();

    /**
     * Closes the Realm instance and all its resources.
     * <p>
     * It's important to always remember to close Realm instances when you're done with it in order not to leak memory,
     * file descriptors or grow the size of Realm file out of measure.
     * {@link Realm#close()}
     */
//    void closeRealm();
}
