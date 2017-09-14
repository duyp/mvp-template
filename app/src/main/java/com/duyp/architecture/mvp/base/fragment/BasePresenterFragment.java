package com.duyp.architecture.mvp.base.fragment;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.view.View;

import com.duyp.architecture.mvp.base.BaseView;
import com.duyp.architecture.mvp.base.presenter.BasePresenter;
import com.duyp.architecture.mvp.dagger.component.FragmentComponent;

import javax.inject.Inject;

import lombok.Getter;

/**
 * Base fragment with presenter (out of user scope)
 * @param <V> Type of View, must extend {@link BaseView}
 * @param <P> Type of Presenter, must extend {@link BasePresenter}
 */
public abstract class BasePresenterFragment<V extends BaseView, P extends BasePresenter<V>>
        extends BaseFragment {

    @Inject
    @Getter
    protected P presenter;

    @Override
    @CallSuper
    protected void initialize(View view) {
        if (presenter != null) {
            presenter.bindView(getViewLayer());
            presenter.onCreate();
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
    public void onDestroy() {
        super.onDestroy();
        if (refWatcher != null && presenter != null) {
            refWatcher.watch(presenter);
        }
        if (presenter != null) {
            presenter.onDestroy();
        }
    }

    private V getViewLayer() {
        // noinspection unchecked
        return ((V)this);
    }
}