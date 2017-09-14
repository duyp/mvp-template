package com.duyp.architecture.mvp.ui.repositories;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.duyp.architecture.mvp.base.fragment.BaseSwipeRecyclerViewFragment;

/**
 * Created by phamd on 9/14/2017.
 *
 */

public class RepositoriesFragment extends BaseSwipeRecyclerViewFragment<RepositoryAdapter, RepositoryView, RepositoryPresenter>
        implements RepositoryView {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentComponent().inject(this);
    }

    @Override
    protected void initialize(View view) {
        super.initialize(view);
//        refreshWithUi();
    }

    @NonNull
    @Override
    protected RepositoryAdapter createAdapter() {
        return getPresenter().getAdapter();
    }
}
