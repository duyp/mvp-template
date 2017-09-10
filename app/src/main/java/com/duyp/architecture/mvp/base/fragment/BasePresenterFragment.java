//package com.duyp.architecture.mvp.base.fragment;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.view.View;
//
//import com.duyp.architecture.mvp.base.BaseView;
//
//import javax.inject.Inject;
//
//import co.asktutor.app.base.presenter.BasePresenter;
//import co.asktutor.app.dagger.component.FragmentComponent;
//import lombok.Getter;
//
//public abstract class BasePresenterFragment<V extends BaseView, P extends BasePresenter<V>>
//        extends BaseFragment {
//
//    @Inject
//    @Getter
//    P presenter;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        inject(fragmentComponent());
//    }
//
//    protected abstract void inject(FragmentComponent fragmentComponent);
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        if (presenter != null) {
//            presenter.bindView(getViewLayer());
//        }
//        super.onViewCreated(view, savedInstanceState);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
////        if (refWatcher != null && presenter != null) {
////            refWatcher.watch(presenter);
////        }
//        if (presenter != null) {
//            presenter.onDestroy();
//        }
//    }
//
//    private V getViewLayer() {
//        // noinspection unchecked
//        return ((V)this);
//    }
//}