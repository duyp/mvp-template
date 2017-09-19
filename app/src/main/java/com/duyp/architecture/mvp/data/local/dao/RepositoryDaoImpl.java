package com.duyp.architecture.mvp.data.local.dao;

import com.duyp.architecture.mvp.base.data.BaseRealmDaoImpl;
import com.duyp.architecture.mvp.base.data.LiveRealmResults;
import com.duyp.architecture.mvp.data.model.Repository;

import javax.inject.Inject;

import io.realm.Realm;

/**
 * Created by duypham on 9/18/17.
 * {@link Repository} Data Access Bbject
 */

public class RepositoryDaoImpl extends BaseRealmDaoImpl<Repository> implements RepositoryDao {

    @Inject
    public RepositoryDaoImpl(Realm realm) {
        super(realm, Repository.class, "id", null); // items wont' be sorted
    }

    @Override
    public LiveRealmResults<Repository> getRepositoriesWithNameLike(String repoName) {
        return findAllSorted(query().like("name", repoName));
    }

    @Override
    public LiveRealmResults<Repository> getUserRepositories(String userLogin) {
        return findAllSorted(
                query().equalTo("owner.login", userLogin)
                        .or()
                        .equalTo("memberLoginName", userLogin)
        );
    }
}
