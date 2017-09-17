package com.duyp.architecture.mvp.ui.repository_detail.issues;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.duyp.architecture.mvp.base.fragment.BaseSwipeRecyclerViewFragment;
import com.duyp.architecture.mvp.data.Constants;
import com.duyp.architecture.mvp.data.model.Repository;
import com.duyp.architecture.mvp.utils.NavigatorHelper;

import org.parceler.Parcels;

/**
 * Created by duypham on 9/17/17.
 *
 */

public class IssuesFragment extends BaseSwipeRecyclerViewFragment<IssuesAdapter, IssuesView, IssuesPresenter> {

    public static IssuesFragment newInstance(@NonNull Repository repository) {
        return NavigatorHelper.createFragmentWithArguments(new IssuesFragment(), bundle -> {
            bundle.putParcelable(Constants.EXTRA_DATA, Parcels.wrap(repository));
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentComponent().inject(this);
    }

    @NonNull
    @Override
    protected IssuesAdapter createAdapter() {
        return getPresenter().getAdapter();
    }

    @Override
    protected void initialize(View view) {
        super.initialize(view);
        getPresenter().initRepo(Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_DATA)));
        refresh();
    }
}
