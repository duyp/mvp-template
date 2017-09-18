package com.duyp.architecture.mvp.base.data;

import android.support.annotation.NonNull;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import lombok.Setter;

/**
 * Created by duypham on 9/18/17.
 * Base Realm data access object
 */

public class BaseRealmDaoImpl<E extends RealmObject> implements BaseRealmDao<E> {

    private final Realm mRealm;
    private final Class<E> mClass;
    private final String mDefaultSortField;
    private final String mPrimaryField;

    @Setter
    private Sort mDefaultSort = Sort.DESCENDING;

    public BaseRealmDaoImpl(Realm realm, Class<E> eClass, String primaryField, String defaultSortField) {
        this.mRealm = realm;
        this.mClass = eClass;
        this.mPrimaryField = primaryField;
        this.mDefaultSortField = defaultSortField;
    }

    /**
     * @return default realm sort {@link Sort#DESCENDING}
     */
    public Sort defaultSort() {
        return mDefaultSort;
    }

    /**
     * @return default sort field
     */
    public String defaultSortField() {
        return mDefaultSortField;
    }

    /**
     * @return default realm instance {@link Realm}
     */
    public Realm getRealm() {
        return mRealm;
    }

    /**
     * @return basic realm query on this class
     */
    protected RealmQuery<E> query() {
        return mRealm.where(mClass);
    }

    /**
     * @param id given object id
     * @return basic query on this class with primary key equal to given id
     */
    protected RealmQuery<E> queryById(Long id) {
        return query().equalTo(mPrimaryField, id);
    }

    @Override
    public LiveRealmResults<E> getAll() {
        return asLiveData(query().findAllSorted(mDefaultSortField, mDefaultSort));
    }

    @Override
    public LiveRealmObject<E> getById(@NonNull Long id) {
        return asLiveData(queryById(id).findFirst());
    }

    @Override
    public void addAll(@NonNull List<E> data) {
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(data);
        mRealm.commitTransaction();
    }

    @Override
    public void addOrUpdate(@NonNull E item) {
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(item);
        mRealm.commitTransaction();
    }

    @Override
    public void delete(@NonNull Long itemId) {
        mRealm.beginTransaction();
        queryById(itemId).findAll().deleteAllFromRealm();
        mRealm.commitTransaction();
    }

    @Override
    public void deleteAll() {
        mRealm.beginTransaction();
        mRealm.delete(mClass);
        mRealm.commitTransaction();
    }

    public LiveRealmResults<E> asLiveData(RealmResults<E> realmResults){
        return new LiveRealmResults<E>(realmResults);
    }

    public LiveRealmObject<E> asLiveData(E data) {
        return new LiveRealmObject<E>(data);
    }
}
