package com.duyp.architecture.mvp.data.local.dao;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.duyp.architecture.mvp.base.data.BaseRealmDaoImpl;
import com.duyp.architecture.mvp.base.data.LiveRealmResults;
import com.duyp.architecture.mvp.data.model.Issue;

import javax.inject.Inject;

import io.realm.Realm;

/**
 * Created by duypham on 9/18/17.
 * Issue Data Access Object Implement
 */

public class IssueDaoImpl extends BaseRealmDaoImpl<Issue> implements IssueDao {

    @Inject
    public IssueDaoImpl(Realm realm) {
        super(realm, Issue.class);
    }

    @Override
    public LiveRealmResults<Issue> getRepoIssues(Long repoId) {
        return asLiveData(query().equalTo("repoId", repoId).findAllSorted(defaultSortField(), defaultSort()));
    }

    @Override
    @Nullable
    protected String getDefaultSortField() {
        return "createdAt";
    }
}
