package com.duyp.architecture.mvp.ui.repository_detail.issues;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.duyp.androidutils.navigator.NavigationUtils;
import com.duyp.architecture.mvp.base.fragment.BaseSwipeRecyclerViewFragment;
import com.duyp.architecture.mvp.app.Constants;

/**
 * Created by duypham on 9/17/17.
 *
 */

public class IssuesFragment extends BaseSwipeRecyclerViewFragment<IssuesLiveAdapter, IssuesView, IssuesPresenter> {

    public static IssuesFragment newInstance(@NonNull Long repoId) {
        return NavigationUtils.createFragmentInstance(new IssuesFragment(), bundle -> {
            bundle.putLong(Constants.EXTRA_DATA, repoId);
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentComponent().inject(this);
    }

    @NonNull
    @Override
    protected IssuesLiveAdapter createAdapter() {
        return getPresenter().getAdapter();
    }

    @Override
    protected void initialize(View view) {
        super.initialize(view);
        getPresenter().initRepo(getArguments().getLong(Constants.EXTRA_DATA));
        refreshWithUi(300);
    }
}
