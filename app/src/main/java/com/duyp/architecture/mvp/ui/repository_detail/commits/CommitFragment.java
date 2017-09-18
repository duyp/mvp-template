package com.duyp.architecture.mvp.ui.repository_detail.commits;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.duyp.androidutils.navigator.NavigationUtils;
import com.duyp.architecture.mvp.base.fragment.BaseSwipeRecyclerViewFragment;
import com.duyp.architecture.mvp.data.Constants;
import com.duyp.architecture.mvp.data.model.Repository;

import org.parceler.Parcels;

/**
 * Created by duypham on 9/18/17.
 */

public class CommitFragment extends BaseSwipeRecyclerViewFragment<CommitsAdapter, CommitsView, CommitPresenter> {

    public static CommitFragment newInstance(@NonNull Long repoId) {
        return NavigationUtils.createFragmentInstance(new CommitFragment(), bundle -> {
            bundle.putLong(Constants.EXTRA_DATA, repoId);
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentComponent().inject(this);
        getPresenter().initRepository(getArguments().getLong(Constants.EXTRA_DATA));
    }

    @Override
    protected void initialize(View view) {
        super.initialize(view);
        refreshWithUi(300);
    }

    @NonNull
    @Override
    protected CommitsAdapter createAdapter() {
        return getPresenter().getAdapter();
    }
}
