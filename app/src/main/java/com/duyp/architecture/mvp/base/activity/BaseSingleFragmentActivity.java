//package com.duyp.architecture.mvp.base.activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.IdRes;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//
//import co.asktutor.app.R;
//import co.asktutor.app.base.activity.BaseActivity;
//import co.asktutor.app.base.fragment.BaseFragment;
//
///**
// * Created by phamd on 7/3/2017.
// * Base activity with single fragment
// */
//
//public abstract class BaseSingleFragmentActivity<T extends BaseFragment> extends BaseActivity {
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (savedInstanceState == null) {
//            addFragment(getContainerId(), createFragment(), null);
//        }
//    }
//
//    @NonNull
//    @Override
//    public Integer getLayout() {
//        return R.layout.container;
//    }
//
//    protected abstract T createFragment();
//
//    @Nullable
//    public T getFragment() {
//        // noinspection unchecked
//        return (T) getSupportFragmentManager().findFragmentById(getContainerId());
//    }
//
//    @IdRes
//    protected int getContainerId() {
//        return R.id.container;
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        T fragment = getFragment();
//        if (fragment != null) {
//            fragment.onActivityResult(requestCode, resultCode, data);
//        }
//    }
//
//    @Override
//    public void onBackPressed() {
//        T fragment = getFragment();
//        if (fragment != null) {
//            if (!fragment.onBackPressed()) {
//                finishWithAnimation();
//            }
//        }
//    }
//}
