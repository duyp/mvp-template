//package com.duyp.architecture.mvp.base.presenter;
//
//import android.content.Context;
//import android.support.annotation.CallSuper;
//import android.support.annotation.NonNull;
//
//import com.duyp.architecture.mvp.base.BaseView;
//
//import co.asktutor.app.api.UserService;
//import co.asktutor.app.mvp.model.BaseEntity;
//import co.asktutor.app.mvp.model.PaginatorEntity;
//import co.asktutor.app.user.UserManager;
//import lombok.Getter;
//import lombok.Setter;
//
///**
// * Created by Duy Pham on 6/20/2017.
// * Base presenter with paging
// */
//
//@Getter
//public abstract class BasePagingPresenter<V extends BaseView> extends BaseUserPresenter<V> {
//
//    private static final int DEFAULT_PAGE = 1;
//
//    private int currentPage = DEFAULT_PAGE;
//
//    @NonNull
//    private PaginatorEntity paginatorEntity;
//
//    @Setter
//    private boolean isRefresh = false;
//
//    public BasePagingPresenter(@NonNull Context context, @NonNull UserService userService, @NonNull UserManager userManager) {
//        super(context, userService, userManager);
//        paginatorEntity = new PaginatorEntity(DEFAULT_PAGE);
//    }
//
//    /**
//     * update paginator data
//     * @param entity respond from server
//     */
//    protected void updatePaginator(BaseEntity entity) {
//        if (entity.getPaginator() != null) {
//            paginatorEntity = entity.getPaginator();
//        }
//    }
//
//    /**
//     * @return true if should clear data (first page or refreshing)
//     */
//    protected boolean shouldClearData() {
//        return isRefresh || currentPage == DEFAULT_PAGE;
//    }
//
//    /**
//     * refresh all paging date and re-fetch data
//     */
//    @CallSuper
//    public void refresh() {
//        resetPaging();
//        isRefresh = true;
//        fetchData();
//    }
//
//    /**
//     * load next page
//     */
//    @CallSuper
//    public void loadMore() {
//        if (canLoadMore()) {
//            currentPage++;
//            fetchData();
//        }
//    }
//
//    /**
//     * @return true if have rest pages
//     */
//    public boolean canLoadMore() {
//        return currentPage < paginatorEntity.getLast_page();
//    }
//
//    /**
//     * Fetch data from server
//     */
//    protected abstract void fetchData();
//
//    protected void onResponse(BaseEntity entity) {
//        if (shouldClearData()) {
//            clearData();
//        }
//        setRefresh(false);
//        updatePaginator(entity);
//    }
//
//    protected abstract void clearData();
//
//    // Reset paging
//    protected void resetPaging() {
//        paginatorEntity = new PaginatorEntity(DEFAULT_PAGE);
//        currentPage = DEFAULT_PAGE;
//    }
//
//    public abstract boolean isDataEmpty();
//}
