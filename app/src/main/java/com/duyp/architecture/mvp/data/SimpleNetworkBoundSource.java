package com.duyp.architecture.mvp.data;


import android.support.annotation.Nullable;
import android.util.Log;

import com.duyp.architecture.mvp.utils.api.ApiUtils;

import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 * <p>
 * You can read more about it in the <a href="https://developer.android.com/arch">Architecture
 * Guide</a>.
 */
public abstract class SimpleNetworkBoundSource<T> {

    public static final String TAG = "resource";

    private boolean apiRespond = false;

    public SimpleNetworkBoundSource(FlowableEmitter<Resource<T>> emitter) {

        final Flowable<T> local = getLocal();
        final Single<Response<T>> remote = getRemote();

        Disposable firstDataDisposable = local == null ? null : local.map(Resource::loading)
                .subscribe(resource -> {
                    Log.d(TAG, "SimpleNetworkBoundSource: loaded from local success!: " + resource);
                    if (remote != null && !apiRespond) {
                        emitter.onNext(resource);
                    } else {
                        emitter.onNext(Resource.success(resource.data));
                    }
                }, throwable -> {
                    Log.d(TAG, "SimpleNetworkBoundSource: loaded from local ERROR: " + throwable.toString());
                    if (remote == null) {
                        emitter.onNext(Resource.error(throwable.getMessage(), null));
                    }
                });

        if (remote != null) {
            ApiUtils.makeRequest(remote, false, response -> {
                Log.d(TAG, "SimpleNetworkBoundSource: call API success!");
                apiRespond = true;
                saveCallResult(response);
            }, errorEntity -> {
                Log.d(TAG, "SimpleNetworkBoundSource: call API error: " + errorEntity.getMessage());
                emitter.onNext(Resource.error(errorEntity.getMessage(), null));
            });
        }
    }

    @Nullable
    public abstract Single<Response<T>> getRemote();

    @Nullable
    public abstract Flowable<T> getLocal();

    public abstract void saveCallResult(T data);
}