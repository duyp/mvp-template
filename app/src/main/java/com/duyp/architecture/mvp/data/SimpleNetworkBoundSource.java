package com.duyp.architecture.mvp.data;

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
public abstract class SimpleNetworkBoundSource<T> {

    public static final String TAG = "resource";

    public SimpleNetworkBoundSource(FlowableEmitter<Resource<T>> emitter, final boolean isRefresh) {
        emitter.onNext(Resource.loading(null));
        // since realm was create on Main Thread, so if we need to touch on realm database after calling
        // api, must make request on main thread by setting shouldUpdateUi params = true
        ApiUtils.makeRequest(getRemote(), true, response -> {
            Log.d(TAG, "SimpleNetworkBoundSource: call API success!");
            saveCallResult(response, isRefresh);
            emitter.onNext(Resource.success(response));
        }, errorEntity -> {
            Log.d(TAG, "SimpleNetworkBoundSource: call API error: " + errorEntity.getMessage());
            emitter.onNext(Resource.error(errorEntity.getMessage(), null));
        });
    }

    public abstract Single<Response<T>> getRemote();

    public abstract void saveCallResult(T data, boolean isRefresh);
}