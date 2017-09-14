package com.duyp.architecture.mvp.utils.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.duyp.architecture.mvp.data.model.base.ApiResponse;
import com.duyp.architecture.mvp.data.model.base.BaseResponse;
import com.duyp.architecture.mvp.data.model.base.ErrorEntity;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by duypham on 9/10/17.
 *
 */

public final class ApiUtils {

    /**
     * Create new retrofit api request
     * @param request           observable request
     * @param shouldUpdateUi true if should update UI after response returned
     * @param onSubscribe consumer that consume request disposable, call before performing request
     * @param onComplete action after request completed, in both case of success and error
     * @param successListener   callback for success response.
     * @param errorListener     callback for error case.
     *
     * @param <T> Type of response body
     */
    public static <T> void makeRequest(
            Observable<Response<T>> request, boolean shouldUpdateUi,
            @Nullable Consumer<Disposable> onSubscribe,
            @Nullable Action onComplete,
            @Nullable OnRequestSuccessListener<T> successListener,
            @Nullable OnRequestErrorListener errorListener) {

        Observable<Response<T>> observable = request.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io());
        if (shouldUpdateUi) {
            observable = observable.observeOn(AndroidSchedulers.mainThread());
        }

        observable.subscribe(response -> {
            if (response.isSuccessful()) {
                if (successListener != null) {
                    successListener.onRequestSuccess(response.body());
                }
            } else {
                // get error message
                ErrorEntity error = ErrorEntity.getError(response.message());

                // handle error
                if (errorListener != null) {
                    errorListener.onError(error);
                }
            }
        }, throwable -> {
            if (throwable instanceof RuntimeException) {
                // must be fixed while developing
                throw new Exception(throwable);
            }
            // get error by throwable
            ErrorEntity error = ErrorEntity.getError(throwable);

            // handle error
            if (errorListener != null) {
                errorListener.onError(error);
            }

            if (onComplete != null) {
                onComplete.run();
            }
        }, onComplete != null ? onComplete : () -> {}
        , onSubscribe != null ? onSubscribe: disposable -> {});
    }

    /**
     * make simple request without updating UI and handling error
     * @param request request
     * @param successListener callback for success response
     * @param <T> type of response data
     */
    public static <T> void makeRequest(Observable<Response<T>> request, OnRequestSuccessListener<T> successListener) {
        makeRequest(request, false, null, null, successListener, null);
    }

    /**
     * make simple request without handling error
     * @param request request
     * @param shouldUpdateUi true if should update UI after response returned
     * @param successListener callback for success response
     * @param <T> type of response data
     */
    public static <T> void makeRequest(Observable<Response<T>> request, boolean shouldUpdateUi,
                                                            OnRequestSuccessListener<T> successListener) {
        makeRequest(request, shouldUpdateUi, null, null, successListener, null);
    }

    /**
     * Make a request with success and error listener
     * @param request request
     * @param shouldUpdateUi true if should update UI after response returned
     * @param successListener callback for success response
     * @param errorListener callback in error case
     * @param <T> type of response data
     */
    public static <T> void makeRequest(Observable<Response<T>> request, boolean shouldUpdateUi,
                                                            OnRequestSuccessListener<T> successListener,
                                                            OnRequestErrorListener errorListener) {
        makeRequest(request, shouldUpdateUi, null, null, successListener, errorListener);
    }
}
