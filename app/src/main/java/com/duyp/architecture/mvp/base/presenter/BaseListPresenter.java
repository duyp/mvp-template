package com.duyp.architecture.mvp.base.presenter;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import com.duyp.architecture.mvp.base.BaseView;
import com.duyp.architecture.mvp.base.interfaces.LoadMoreable;
import com.duyp.architecture.mvp.base.interfaces.Refreshable;
import com.duyp.architecture.mvp.data.local.user.UserManager;
import com.duyp.architecture.mvp.data.model.User;
import com.duyp.architecture.mvp.data.model.base.BaseResponse;
import com.duyp.architecture.mvp.data.remote.UserService;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Duy Pham on 6/20/2017.
 * Base presenter with paging
 */

@Getter
public abstract class BaseListPresenter<V extends BaseView> extends BasePresenter<V> implements Refreshable, LoadMoreable {

    @Setter
    private boolean isRefreshed = false;

    public BaseListPresenter(Context context, UserManager userManager) {
        super(context, userManager);
    }

    /**
     * refresh all paging date and re-fetch data
     */
    @CallSuper
    @Override
    public void refresh() {
        isRefreshed = true;
        fetchData();
    }

    /**
     * load next page
     */
    @CallSuper
    public void loadMore() {
        if (canLoadMore()) {
            fetchData();
        }
    }

    /**
     * Fetch data from server
     */
    protected abstract void fetchData();
}
