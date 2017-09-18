package com.duyp.architecture.mvp.ui.repository_detail.commits;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.duyp.androidutils.navigator.NavigationUtils;
import com.duyp.architecture.mvp.base.fragment.BaseSwipeRecyclerViewFragment;
import com.duyp.architecture.mvp.data.Constants;
import com.duyp.architecture.mvp.data.model.Repository;
import com.duyp.architecture.mvp.utils.NavigatorHelper;

import org.parceler.Parcels;

/**
 * Created by duypham on 9/18/17.
 */

public class CommitFragment extends BaseSwipeRecyclerViewFragment<CommitsAdapter, CommitsView, CommitPresenter> {

    public static CommitFragment newInstance(@NonNull Repository repository) {
        return NavigationUtils.createFragmentInstance(new CommitFragment(), bundle -> {
            bundle.putParcelable(Constants.EXTRA_DATA, Parcels.wrap(repository));
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentComponent().inject(this);
        getPresenter().initRepository(Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_DATA)));
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
