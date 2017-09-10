//package com.duyp.architecture.mvp.base.fragment;
//
//import android.os.Bundle;
//import android.support.annotation.CallSuper;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.design.widget.AppBarLayout;
//import android.support.v4.view.ViewPager;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.util.Log;
//import android.view.View;
//
//import butterknife.BindView;
//import co.asktutor.app.R2;
//import co.asktutor.app.app.Constants;
//import co.asktutor.app.base.presenter.BaseUserPresenter;
//import co.asktutor.app.mvp.view.listener.Refreshable;
//import co.asktutor.app.mvp.view.util.AppBarStateChangeListener;
//import co.asktutor.app.mvp.view.views.BaseView;
//
///**
// * Created by phamd on 7/19/2017.
// * Base Fragment with Swipe to refresh layout
// */
//
//public abstract class BaseSwipeToRefreshFragment<V extends BaseView, P extends BaseUserPresenter<V>>
//        extends BaseUserPresenterFragment<V, P> implements SwipeRefreshLayout.OnRefreshListener, Refreshable {
//
//    @BindView(R2.id.srl)
//    protected SwipeRefreshLayout refreshLayout;
//
//    protected boolean isRefresh = false;
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        initSwipeToRefresh();
//        super.onViewCreated(view, savedInstanceState);
//    }
//
//    private void initSwipeToRefresh() {
//        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);
//        refreshLayout.setOnRefreshListener(this);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        doneRefresh();
//    }
//
//
//    public void disableSwipeToRefresh() {
//        refreshLayout.setEnabled(false);
//    }
//
//    public void enableSwipeToRefresh() {
//        refreshLayout.setEnabled(true);
//    }
//
//    @CallSuper
//    public void doneRefresh() {
//        isRefresh = false;
//        shouldRefreshUi = false;
//        if (refreshLayout != null) {
//            refreshLayout.setRefreshing(false);
//        }
//    }
//
//    @Override
//    @CallSuper
//    public void onRefresh() {
//        if (!isRefresh) {
//            isRefresh = true;
//            refresh();
//        }
//    }
//
//    public void refreshWithUi(int delay) {
//        if (refreshLayout != null) {
//            refreshLayout.postDelayed(() -> {
//                refreshUi();
//                onRefresh();
//            }, delay);
//        }
//    }
//
//    private boolean shouldRefreshUi = true;
//    protected void refreshUi() {
//        new android.os.Handler().postDelayed(() -> {
//            if (shouldRefreshUi) {
//                refreshLayout.setRefreshing(true);
//            }
//        }, Constants.PROGRESS_REFRESH_DELAY);
//    }
//
//    public void refreshWithUi() {
//        refreshWithUi(0);
//    }
//
//    public boolean isRefreshing() {
//        return isRefresh;
//    }
//
//    public void setupWithAppBarScrollingState(@NonNull AppBarLayout appBar){
//        appBar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
//            @Override
//            public void onStateChanged(AppBarLayout appBarLayout, State state) {
//                if (state == State.EXPANDED) {
//                    if (refreshLayout != null) {
//                        Log.d("refresh_layout", "enabled");
//                        refreshLayout.setEnabled(true);
//                    }
//                } else {
//                    if (refreshLayout != null && !isRefresh) {
//                        Log.d("refresh_layout", "disabled");
//                        refreshLayout.setEnabled(false);
//                    }
//                }
//            }
//        });
//    }
//
//    public void setupWithViewPagerScrollingState(@NonNull ViewPager pager) {
//        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//            int mPreviousState = ViewPager.SCROLL_STATE_IDLE;
//
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
//
//            @Override
//            public void onPageSelected(int position) {}
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//                // All of this is to inhibit any scrollable container from consuming our touch events as the user is changing pages
//                if (mPreviousState == ViewPager.SCROLL_STATE_IDLE) {
//                    if (state == ViewPager.SCROLL_STATE_DRAGGING) {
//                        refreshLayout.setEnabled(false);
//                    }
//                } else {
//                    if (state == ViewPager.SCROLL_STATE_IDLE || state == ViewPager.SCROLL_STATE_SETTLING) {
//                        refreshLayout.setEnabled(true);
//                    }
//                }
//                mPreviousState = state;
//            }
//        });
//    }
//}
