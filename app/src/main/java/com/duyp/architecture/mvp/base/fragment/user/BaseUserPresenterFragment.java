package com.duyp.architecture.mvp.base.fragment.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.duyp.architecture.mvp.base.BaseView;
import com.duyp.architecture.mvp.base.presenter.BaseUserPresenter;

import javax.inject.Inject;

import lombok.Getter;

/**
 * Created by phamd on 5/25/2017.
 * Base Fragment with presenter for user scope
 * @param <V> type of View, must extend {@link BaseView}
 * @param <P> type of Presenter, must extend {@link BaseUserPresenter}
 */

public abstract class BaseUserPresenterFragment<V extends BaseView, P extends BaseUserPresenter<V>>
        extends BaseUserFragment {

    @Getter
    @Inject
    protected P presenter;

    @Override
    protected void initialize(View view) {
        super.initialize(view);
        if (presenter != null) {
            presenter.bindView(getViewLayer());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (presenter != null) {
            presenter.onStart();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (presenter != null) {
            presenter.onStop();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (presenter != null) {
            presenter.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (presenter != null){
            presenter.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (refWatcher != null && presenter != null) {
            refWatcher.watch(presenter);
        }
        if (presenter != null) {
            presenter.onDestroy();
        }
    }

    public V getViewLayer() {
        // noinspection unchecked
        return ((V)this);
    }

}