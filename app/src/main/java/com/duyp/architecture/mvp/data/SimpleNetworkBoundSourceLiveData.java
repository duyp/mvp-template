package com.duyp.architecture.mvp.data;


import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.support.annotation.Nullable;
import android.util.Log;

import com.duyp.architecture.mvp.utils.api.ApiUtils;

import io.reactivex.FlowableEmitter;
import io.reactivex.Single;
import retrofit2.Response;

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 * <p>
 * You can read more about it in the <a href="https://developer.android.com/arch">Architecture
 * Guide</a>.
 */
public abstract class SimpleNetworkBoundSourceLiveData<T> {

    public static final String TAG = "resource";

    private boolean apiRespond = false;

    private T data;

    public SimpleNetworkBoundSourceLiveData(LifecycleOwner lifecycleOwner, FlowableEmitter<Resource<T>> emitter) {

        final LiveData<T> local = getLocal();
        final Single<Response<T>> remote = getRemote();

        if (local != null) {
            local.observe(lifecycleOwner, t -> {
                data = t;
                Log.d(TAG, "SimpleNetworkBoundSource: loaded from local success!: " + data);
                if (remote != null && !apiRespond) {
                    emitter.onNext(Resource.loading(data));
                } else {
                    emitter.onNext(Resource.success(data));
                }
            });
        }

        if (remote != null) {
            ApiUtils.makeRequest(remote, false, response -> {
                Log.d(TAG, "SimpleNetworkBoundSource: call API success!");
                apiRespond = true;
                saveCallResult(response);
                emitter.onNext(Resource.success(data));
            }, errorEntity -> {
                Log.d(TAG, "SimpleNetworkBoundSource: call API error: " + errorEntity.getMessage());
                emitter.onNext(Resource.error(errorEntity.getMessage(), null));
            });
        }
    }

    @Nullable
    public abstract Single<Response<T>> getRemote();

    @Nullable
    public abstract LiveData<T> getLocal();

    public abstract void saveCallResult(T data);
}