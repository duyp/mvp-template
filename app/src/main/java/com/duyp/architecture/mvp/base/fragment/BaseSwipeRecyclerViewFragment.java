package com.duyp.architecture.mvp.base.fragment;

import android.app.Activity;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.duyp.androidutils.adapter.BaseHeaderFooterAdapter;
import com.duyp.architecture.mvp.R;
import com.duyp.architecture.mvp.base.BaseView;
import com.duyp.architecture.mvp.base.interfaces.LoadMoreable;
import com.duyp.architecture.mvp.base.presenter.BasePresenter;
import com.duyp.architecture.mvp.utils.BaseRecyclerViewAdapter;

import butterknife.BindView;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Duy Pham on 14/06/2017
 * Base Fragment with swipe to refresh and recycler view
 * All child fragments 's layout must have refresh layout with id <b>srl</b> and RecyclerView with id <b>rcv</b>
 * @param <A> Adapter with list and addable headers and footers
 * @param <V> View
 * @param <P> Presenter
 */

public abstract class BaseSwipeRecyclerViewFragment<
        A extends BaseRecyclerViewAdapter,
        V extends BaseView,
        P extends BasePresenter<V>>
        extends BaseSwipeToRefreshFragment<V, P> implements SwipeRefreshLayout.OnRefreshListener {

    private static final int DEFAULT_SCROLL_TOP_POSITION = 10;

    @BindView(R.id.rcv)
    protected RecyclerView recyclerView;

    @Nullable
    protected View noDataView;

    @Nullable
    protected View scrollTopView;

    @Getter
    private A adapter;

    @Setter
    private int scrollTopPosition = DEFAULT_SCROLL_TOP_POSITION;

    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected int getLayout() {
        return R.layout.swipe_recycler_view;
    }

    @Override
    protected void initialize(View view) {
        super.initialize(view);
        initRecyclerView();
        scrollTopView = view.findViewById(R.id.scrollTop);
        noDataView = view.findViewById(R.id.noDataView);
        if (scrollTopView != null) {
            scrollTopView.setVisibility(View.GONE);
            scrollTopView.setOnClickListener(view1 -> {
                recyclerView.scrollToPosition(0);
            });
        }
        if (noDataView != null) {
            noDataView.setVisibility(View.GONE);
        }
    }

    /**
     * Create recycler view adapter
     * @return adapter
     */
    @NonNull
    protected abstract A createAdapter();

    @CallSuper
    protected void initRecyclerView() {
        layoutManager = createLayoutManager();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                if(layoutManager instanceof LinearLayoutManager) {
                    int pastVisibleItems = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                    updateScrollTop(visibleItemCount, pastVisibleItems);
                }

                if (!isRefresh && (visibleItemCount + getPastVisibleItems()) >= totalItemCount) {
                    loadMore();
                }
            }
        });

        adapter = createAdapter();
        recyclerView.setAdapter(adapter);
    }

    protected RecyclerView.LayoutManager createLayoutManager() {
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        return lm;
    }

    /**
     * Load more item after scrolling down to bottom of current list
     */
    public void loadMore() {
        if (presenter instanceof LoadMoreable && ((LoadMoreable) presenter).canLoadMore()) {
            ((LoadMoreable) presenter).loadMore();
            isRefresh = true;
            adapter.addFooter(getFooterView());
        }
    }

    /**
     * @return true if our adapter has no data
     */
    protected boolean isDataEmpty() {
        return adapter != null && adapter.getData() != null && adapter.getData().isEmpty();
    }

    /**
     * refresh list by presenter
     */
    @Override
    public void refresh() {
        if (presenter != null) {
            presenter.refresh();
        }
    }

    /**
     * Called after refreshing success, for both case of success or fail
     * Should be called after {@link BaseFragment#hideProgress()}
     */
    @CallSuper
    public void doneRefresh() {
        if (adapter != null) {
            adapter.removeFooter(getFooterView());
        }
        updateNoDataState();
        updateScrollTop();
        new android.os.Handler().postDelayed(super::doneRefresh, 300);
    }

    // footer for load more state
    View footerView;
    protected View getFooterView() {
        if (footerView == null && getActivity() != null) {
            footerView = ((LayoutInflater) getActivity().getSystemService(
                    Activity.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_progress_bar, null, false);
        }
        return footerView;
    }

    /**
     * Show no data view if current adapter data is empty
     * must be call inside or after {@link #doneRefresh()}
     */
    protected void updateNoDataState() {
        if (noDataView != null) {
            if (isDataEmpty()) {
                noDataView.setVisibility(View.VISIBLE);
            } else {
                noDataView.setVisibility(View.GONE);
            }
        }
    }

    private int getPastVisibleItems() {
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        }
        return 0;
    }

    /**
     * Show scroll top view (click on it to scroll recycler view to top)
     * if user scroll down more than {@link #DEFAULT_SCROLL_TOP_POSITION}
     */
    protected void updateScrollTop() {
        if (layoutManager != null) {
            int visibleItemCount = layoutManager.getChildCount();
            updateScrollTop(visibleItemCount, getPastVisibleItems());
        }
    }

    /**
     * Show scroll top view (click on it to scroll recycler view to top)
     * if user scroll down more than {@link #DEFAULT_SCROLL_TOP_POSITION}
     */
    private void updateScrollTop(int visibleItemCount, int pastVisibleItems) {
        if (scrollTopView != null) {
            if (recyclerView != null) {
                if (visibleItemCount + pastVisibleItems >= scrollTopPosition) {
                    scrollTopView.setVisibility(View.VISIBLE);
                } else {
                    scrollTopView.setVisibility(View.GONE);
                }
            } else {
                scrollTopView.setVisibility(View.GONE);
            }
        }
    }
}
