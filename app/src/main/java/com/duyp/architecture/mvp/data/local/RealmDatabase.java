package com.duyp.architecture.mvp.data.local;

import com.duyp.architecture.mvp.data.local.dao.IssueDao;
import com.duyp.architecture.mvp.data.local.dao.IssueDaoImpl;
import com.duyp.architecture.mvp.data.local.dao.RepositoryDao;
import com.duyp.architecture.mvp.data.local.dao.RepositoryDaoImpl;
import com.duyp.architecture.mvp.data.local.dao.UserDao;

import io.realm.Realm;

/**
 * Created by duypham on 9/20/17.
 *
 */

public class RealmDatabase {

    private final Realm mRealm;

    private RepositoryDao repositoryDao;

    private IssueDao issueDao;

    private UserDao userDao;

    public RealmDatabase(Realm realm) {
        mRealm = realm;
    }

    public RepositoryDao getRepositoryDao() {
        if (repositoryDao == null) {
            repositoryDao = new RepositoryDaoImpl(mRealm);
        }
        return repositoryDao;
    }

    public IssueDao getIssueDao() {
        if (issueDao == null) {
            issueDao = new IssueDaoImpl(mRealm);
        }
        return issueDao;
    }

    public UserDao getUserDao() {
        if (userDao == null) {
            userDao = new UserDao(mRealm);
        }
        return userDao;
    }
}
