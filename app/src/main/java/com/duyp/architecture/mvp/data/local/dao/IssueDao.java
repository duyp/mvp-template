package com.duyp.architecture.mvp.data.local.dao;

import com.duyp.architecture.mvp.base.data.BaseRealmDao;
import com.duyp.architecture.mvp.base.data.LiveRealmResults;
import com.duyp.architecture.mvp.data.model.Issue;

/**
 * Created by duypham on 9/18/17.
 * {@link Issue} Data Access Object
 */

public interface IssueDao extends BaseRealmDao<Issue> {
    /**
     * Get repository issues
     * @param repoId @{@link com.duyp.architecture.mvp.data.model.Repository#id}
     * @return RealmResults list of issues
     */
    LiveRealmResults<Issue> getRepoIssues(Long repoId);
}