//package com.duyp.architecture.mvp.base.fragment;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.view.View;
//
//import javax.inject.Inject;
//
//import co.asktutor.app.base.presenter.BaseUserPresenter;
//import co.asktutor.app.mvp.view.views.BaseView;
//
///**
// * Created by phamd on 5/25/2017.
// * Base Fragment with presenter for user scope
// */
//
//public abstract class BaseUserPresenterFragment<V extends BaseView, P extends BaseUserPresenter<V>>
//        extends BaseUserFragment {
//
//    @Nullable
//    @Inject
//    P mPresenter;
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        if (mPresenter != null) {
//            mPresenter.bindView(getViewLayer());
//        }
//        super.onViewCreated(view, savedInstanceState);
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
////        if (refWatcher != null && mPresenter != null) {
////            refWatcher.watch(mPresenter);
////        }
//        if (mPresenter != null) {
//            mPresenter.onDestroy();
//        }
//    }
//
//    public V getViewLayer() {
//        // noinspection unchecked
//        return ((V)this);
//    }
//
//    public P getPresenter() {
//        return mPresenter;
//    }
//
//}