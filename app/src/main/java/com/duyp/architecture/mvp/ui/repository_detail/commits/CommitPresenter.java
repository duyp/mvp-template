package com.duyp.architecture.mvp.ui.repository_detail.commits;

import android.content.Context;
import android.support.annotation.NonNull;

import com.duyp.architecture.mvp.base.presenter.BaseListPresenter;
import com.duyp.architecture.mvp.dagger.qualifier.ActivityContext;
import com.duyp.architecture.mvp.dagger.scopes.PerFragment;
import com.duyp.architecture.mvp.data.local.RealmDatabase;
import com.duyp.architecture.mvp.data.local.dao.RepositoryDao;
import com.duyp.architecture.mvp.data.local.user.UserManager;
import com.duyp.architecture.mvp.data.model.Commit;
import com.duyp.architecture.mvp.data.model.Repository;
import com.duyp.architecture.mvp.data.repos.RepositoriesRepo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import lombok.Getter;

/**
 * Created by duypham on 9/18/17.
 */

@PerFragment
public class CommitPresenter extends BaseListPresenter<CommitsView> {

    @Getter
    private final CommitsAdapter adapter;

    private Repository repository;

    private List<Commit> data = new ArrayList<>();

    private final RealmDatabase realmDatabase;

    @Inject
    public CommitPresenter(@ActivityContext Context context, UserManager userManager, CommitsAdapter adapter,
                           RealmDatabase realmDatabase) {
        super(context, userManager);
        this.adapter = adapter;
        adapter.setData(data);
        this.realmDatabase = realmDatabase;
    }

    void initRepository(@NonNull Long repoId) {
        this.repository = realmDatabase.getRepositoryDao().getById(repoId).getData();
    }

    @Override
    public boolean canLoadMore() {
        return false;
    }

    @Override
    protected void fetchData() {
        addRequest(getGithubService().getRepoCommits(repository.getOwner().getLogin(), repository.getName()), true, commits -> {
            if (isRefreshed()) {
                data.clear();
            }
            setRefreshed(false);
            data.addAll(commits);
            adapter.notifyDataSetChanged();
        });
    }
}
