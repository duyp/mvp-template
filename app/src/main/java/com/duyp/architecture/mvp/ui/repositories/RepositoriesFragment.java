package com.duyp.architecture.mvp.ui.repositories;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.duyp.architecture.mvp.R;
import com.duyp.architecture.mvp.base.fragment.BaseSwipeRecyclerViewFragment;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by phamd on 9/14/2017.
 *
 */

public class RepositoriesFragment extends BaseSwipeRecyclerViewFragment<RepositoryAdapter, RepositoryView, RepositoryPresenter>
        implements RepositoryView {

    @BindView(R.id.tvSearch)
    EditText edtSearch;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_repositories;
    }

    @Override
    protected void initialize(View view) {
        super.initialize(view);
        RxTextView.textChanges(edtSearch).debounce(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(charSequence -> {
                    getPresenter().findRepositories(charSequence.toString());
                });
        refreshWithUi(200);
    }

    @NonNull
    @Override
    protected RepositoryAdapter createAdapter() {
        return getPresenter().getAdapter();
    }
}
