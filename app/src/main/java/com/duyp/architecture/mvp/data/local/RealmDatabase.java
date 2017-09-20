package com.duyp.architecture.mvp.data.local;

import com.duyp.architecture.mvp.data.local.dao.RepositoryDao;
import com.duyp.architecture.mvp.data.local.dao.RepositoryDaoImpl;

import io.realm.Realm;

/**
 * Created by duypham on 9/20/17.
 *
 */

public class RealmDatabase {

    private final Realm mRealm;

    public RealmDatabase(Realm realm) {
        mRealm = realm;
    }

    public RepositoryDao getRepositoryDao() {
        return new RepositoryDaoImpl(mRealm);
    }
}
