package com.duyp.architecture.mvp.ui.repository_detail.commits;

import android.content.Context;
import android.support.annotation.NonNull;

import com.duyp.architecture.mvp.base.presenter.BaseListPresenter;
import com.duyp.architecture.mvp.base.presenter.BasePresenter;
import com.duyp.architecture.mvp.dagger.qualifier.ActivityContext;
import com.duyp.architecture.mvp.dagger.scopes.PerFragment;
import com.duyp.architecture.mvp.data.local.user.UserManager;
import com.duyp.architecture.mvp.data.model.Commit;
import com.duyp.architecture.mvp.data.model.Repository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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

    @Inject
    public CommitPresenter(@ActivityContext Context context, UserManager userManager, CommitsAdapter adapter) {
        super(context, userManager);
        this.adapter = adapter;
        adapter.setData(data);
    }

    void initRepository(@NonNull Repository repository) {
        this.repository = repository;
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
            data.addAll(commits);
            adapter.notifyDataSetChanged();
            setRefreshed(false);
        });
    }

    @Override
    public boolean isDataEmpty() {
        return adapter.getData() == null || adapter.getData().isEmpty();
    }
}
