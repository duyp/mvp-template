package com.duyp.architecture.mvp.base.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.duyp.androidutils.CustomSharedPreferences;
import com.duyp.architecture.mvp.base.BaseView;
import com.duyp.architecture.mvp.base.interfaces.Lifecycle;
import com.duyp.architecture.mvp.data.local.user.UserManager;
import com.duyp.architecture.mvp.data.local.user.UserRepo;
import com.duyp.architecture.mvp.data.model.base.BaseResponse;
import com.duyp.architecture.mvp.data.remote.GithubService;
import com.duyp.architecture.mvp.utils.api.ApiUtils;
import com.duyp.architecture.mvp.utils.api.OnRequestErrorListener;
import com.duyp.architecture.mvp.utils.api.OnRequestSuccessListener;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import lombok.Getter;

@Getter
public abstract class BasePresenter<V extends BaseView> implements Lifecycle {

    @Nullable
    private V mView;

    protected Context context;

    private UserManager userManager;

    private EventBus eventBus;

    @NonNull
    private CompositeDisposable mCompositeDisposable;

    public BasePresenter(Context context, UserManager userManager){
        this.context = context;
        this.userManager = userManager;
        eventBus = EventBus.getDefault();
        mCompositeDisposable = new CompositeDisposable();
    }

    /**
     * Called when view handled by this presenter is available.
     * It will be called no later than Timeline/Fragment onStart() method call.
     *
     * @param view Object representing MVP view layer
     */
    public void bindView(V view) {
        this.mView = view;
    }

    /**
     * Called when view is being unbind from presenter component.
     * It will be called no later than Timeline/Fragment onStop() method call.
     */
    public void unbindView() {
        this.mView = null;
    }

    /**
     * @return View layer
     */
    public V getView() {
        return mView;
    }

    public GithubService getGithubService() {
        return userManager.getGithubService();
    }

    public UserRepo getUserRepo() {
        return userManager.getUserRepo();
    }

    public CustomSharedPreferences getSharedPreference() {
        return getUserRepo().getSharedPreferences();
    }

    /**
     * NULL SAFE
     * Add new api request to {@link CompositeDisposable} and execute immediately
     * All error case and progress showing will be handled automatically
     * @param request           observable request
     * @param showProgress      true if should show loading progress
     *
     * @param successListener   callback for success response.
     * @param errorListener     callback for error case.
     *                          If both of these listeners are null, the request will be subscribed
     *                          on io thread without observing on main thread
     *                          * no update UI in case of both success and error are null
     * @param forceResponseWithoutCheckNullView the success result will be returned without check null for view
     * @param <T> Type of response, must extend {@link BaseResponse}
     */
    protected <T extends BaseResponse> void addRequest(
            Observable<T> request, boolean showProgress,
            boolean forceResponseWithoutCheckNullView,
            @Nullable OnRequestSuccessListener<T> successListener,
            @Nullable OnRequestErrorListener errorListener) {

        boolean shouldUpdateUI = showProgress || successListener != null || errorListener != null;

        ApiUtils.makeRequest(request, shouldUpdateUI, disposable -> {
            // on subscribe
            if (showProgress && mView != null) {
                mView.showProgress();
            }
            // add disposable to composite disposable, dispose all requests at #onDestroy()
            if (mCompositeDisposable.isDisposed()) {
                mCompositeDisposable = new CompositeDisposable();
            }
            mCompositeDisposable.add(disposable);
        }, () -> {
            // complete
            if (showProgress && mView != null) {
                mView.hideProgress();
            }
        }, response -> {
            if (successListener != null && (forceResponseWithoutCheckNullView || mView != null)) {
                successListener.onRequestSuccess(response);
            }
        }, error -> {
            if (errorListener != null && mView != null) {
                errorListener.onError(error);
            }
        });
    }

    /**
     * Add a request with success listener and error listener
     */
    protected <T extends BaseResponse> void addRequest(Observable<T> request, boolean showProgress,
                                                     @Nullable OnRequestSuccessListener<T> successListener,
                                                     @Nullable OnRequestErrorListener errorListener) {
        addRequest(request, showProgress, false, successListener, errorListener);
    }

    /**
     * Add a request with success listener
     */
    protected <T extends BaseResponse> void addRequest(Observable<T> request, boolean showProgress,
                                                           @Nullable OnRequestSuccessListener<T> successListener) {
        addRequest(request, showProgress, false, successListener, null);
    }

    /**
     * Add a request with success listener and forceResponseWithoutCheckNullView param
     */
    protected <T extends BaseResponse> void addRequest(Observable<T> request, boolean showProgress,
                                                     boolean forceResponseWithoutCheckNullView,
                                                     @Nullable OnRequestSuccessListener<T> successListener) {
        addRequest(request, showProgress, forceResponseWithoutCheckNullView, successListener, null);
    }

    /**
     * Add a request without handling error and no update UI
     */
    protected <T extends BaseResponse> void addRequest(Observable<T> request) {
        addRequest(request, false, false, null, null);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    /**
     * Dispose all subscribed subscriptions
     */
    @Override
    public void onDestroy() {
        mCompositeDisposable.dispose();
        unbindView();
    }
}
