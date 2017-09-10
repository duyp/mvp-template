//package com.duyp.architecture.mvp.base.fragment;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.support.annotation.CallSuper;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import butterknife.BindView;
//import butterknife.OnClick;
//import co.asktutor.app.R;
//import co.asktutor.app.R2;
//import co.asktutor.app.base.adapter.BaseHeaderFooterAdapter;
//import co.asktutor.app.base.presenter.BasePagingPresenter;
//import co.asktutor.app.mvp.view.listener.Destroyable;
//import co.asktutor.app.mvp.view.listener.LoadMorable;
//import co.asktutor.app.mvp.view.listener.Refreshable;
//import co.asktutor.app.mvp.view.util.WrapContentLinearLayoutManager;
//import co.asktutor.app.mvp.view.views.BaseView;
//import lombok.Getter;
//import lombok.Setter;
//
///**
// * Created by Duy Pham on 14/06/2017
// * Base Fragment with swipeable recycler view (in user scope)
// * @param <A> Adapter with addable header and footer
// * @param <V> View
// * @param <P> Presenter
// */
//
//public abstract class BaseSwipeableRecyclerViewFragment<
//        A extends BaseHeaderFooterAdapter,
//        V extends BaseView,
//        P extends BasePagingPresenter<V>>
//        extends BaseSwipeToRefreshFragment<V, P>
//        implements SwipeRefreshLayout.OnRefreshListener, Refreshable, LoadMorable {
//
//    private static final int DEFAULT_SCROLL_TOP_POSITION = 10;
//
//    @BindView(R2.id.recyclerView)
//    protected RecyclerView recyclerView;
//
//    @BindView(R2.id.rl_no_data)
//    protected ViewGroup rlNoData;
//
//    @BindView(R.id.scrollTop)
//    View viewScrollTop;
//
//    @Getter
//    private A adapter;
//
//    @Setter
//    private int scrollTopPosition = DEFAULT_SCROLL_TOP_POSITION;
//
//    private RecyclerView.LayoutManager layoutManager;
//
//    @NonNull
//    @Override
//    protected Integer getLayout() {
//        return R.layout.fragment_swipeable_recyclerview;
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        initRecyclerView();
//        super.onViewCreated(view, savedInstanceState);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (adapter != null)
//            if (adapter instanceof Destroyable) {
//            ((Destroyable)adapter).onDestroy();
//        }
//    }
//
//    @NonNull
//    protected abstract A createAdapter();
//
//    @CallSuper
//    protected void initRecyclerView() {
//        layoutManager = createLayoutManager();
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                int visibleItemCount = layoutManager.getChildCount();
//                int totalItemCount = layoutManager.getItemCount();
//
//                if ((visibleItemCount + getPastVisibleItems()) >= totalItemCount) {
//                    if (!isRefreshing() && adapter != null && getPresenter().canLoadMore()) {
//                        adapter.addFooter(getFooterView());
//                        loadMore();
//                    }
//                }
////                updateScrollTop(visibleItemCount, pastVisibleItems);
//            }
//        });
//
//        adapter = createAdapter();
//        recyclerView.setAdapter(adapter);
//    }
//
//    protected RecyclerView.LayoutManager createLayoutManager() {
//        WrapContentLinearLayoutManager lm = new WrapContentLinearLayoutManager(getContext());
//        lm.setOrientation(LinearLayoutManager.VERTICAL);
//        return lm;
//    }
//
//    @OnClick(R.id.scrollTop)
//    public void scrollTop() {
//        if (recyclerView != null) {
//            recyclerView.smoothScrollToPosition(0);
//        }
//    }
//
//    @Override
//    public void loadMore() {
//        isRefresh = true;
//        updateData();
//        getPresenter().loadMore();
//    }
//
//    public void updateData() {
//        if (adapter != null) {
//            adapter.notifyDataSetChanged();
//        }
//        doneRefresh();
//    }
//
//    protected boolean isDataEmpty() {
//        return getPresenter().isDataEmpty();
//    }
//
//    public void refresh() {
//        getPresenter().refresh();
//    }
//
//    @CallSuper
//    public void doneRefresh() {
//        super.doneRefresh();
//        updateNoDataState();
////        updateScrollTop();
//    }
//
//    // footer for load more state
//    View footerView;
//    protected View getFooterView() {
//        if (footerView == null && getActivity() != null) {
//            footerView = ((LayoutInflater) getActivity().getSystemService(
//                    Activity.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.progress_bar, null, false);
//        }
//        return footerView;
//    }
//
//    protected void updateNoDataState() {
//        if (rlNoData != null) {
//            if (isDataEmpty()) {
//                rlNoData.setVisibility(View.VISIBLE);
//            } else {
//                rlNoData.setVisibility(View.GONE);
//            }
//        }
//    }
//
//    private int getPastVisibleItems() {
//        if (layoutManager instanceof LinearLayoutManager) {
//            return ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
//        }
//        return 0;
//    }
//
//    protected void updateScrollTop() {
//        if (layoutManager != null) {
//            int visibleItemCount = layoutManager.getChildCount();
//            updateScrollTop(visibleItemCount, getPastVisibleItems());
//        }
//    }
//
//    private void updateScrollTop(int visibleItemCount, int pastVisiblesItems) {
//        if (viewScrollTop != null) {
//            if (recyclerView != null) {
//                if (visibleItemCount + pastVisiblesItems >= scrollTopPosition) {
//                    viewScrollTop.setVisibility(View.VISIBLE);
//                } else {
//                    viewScrollTop.setVisibility(View.GONE);
//                }
//            } else {
//                viewScrollTop.setVisibility(View.GONE);
//            }
//        }
//    }
//}
