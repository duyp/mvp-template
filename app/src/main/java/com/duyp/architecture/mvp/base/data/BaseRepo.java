package com.duyp.architecture.mvp.base.data;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.support.annotation.Nullable;

import com.duyp.androidutils.functions.PlainConsumer;
import com.duyp.architecture.mvp.data.Resource;
import com.duyp.architecture.mvp.data.SimpleNetworkBoundSource;
import com.duyp.architecture.mvp.data.SimpleNetworkBoundSourceLiveData;
import com.duyp.architecture.mvp.data.local.RealmDatabase;
import com.duyp.architecture.mvp.data.remote.GithubService;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Single;
import lombok.Getter;
import retrofit2.Response;

/**
 * Created by duypham on 9/15/17.
 *
 */

@Getter
public abstract class BaseRepo {

    private final GithubService githubService;

    private final LifecycleOwner owner;

    private final RealmDatabase realmDatabase;

    public BaseRepo(LifecycleOwner owner, GithubService githubService, RealmDatabase realmDatabase) {
        this.githubService = githubService;
        this.owner = owner;
        this.realmDatabase = realmDatabase;
    }

    /**
     * Create resource flowable from given remote api and local persistence by using {@link SimpleNetworkBoundSourceLiveData}
     * @param remote
     * @param local
     * @param onSave
     * @param <T>
     * @return
     */
    protected <T> Flowable<Resource<T>> createResource(@Nullable Single<Response<T>> remote,
                                                       @Nullable LiveData<T> local,
                                                       @Nullable PlainConsumer<T> onSave) {
        // if our local 's had observers already, no need to send it to SimpleNetworkBoundSourceLiveData,
        // since SimpleNetworkBoundSourceLiveData will start other observer on it
        final LiveData<T> targetLocal = (local != null && local.hasObservers()) ? null : local;
        return Flowable.create(emitter -> {

            new SimpleNetworkBoundSourceLiveData<T>(owner, emitter) {
                @Override
                public Single<Response<T>> getRemote() {
                    return remote;
                }

                @Override
                public LiveData<T> getLocal() {
                    return targetLocal;
                }

                @Override
                public void saveCallResult(T data) {
                    if (onSave != null) {
                        onSave.accept(data);
                    }
                }
            };
        }, BackpressureStrategy.BUFFER);
    }

    protected <T> Flowable<Resource<T>> createResource(@Nullable Single<Response<T>> remote,
                                                       @Nullable PlainConsumer<T> onSave) {
        return Flowable.create(emitter -> {
            new SimpleNetworkBoundSource<T>(emitter) {

                @Override
                public Single<Response<T>> getRemote() {
                    return remote;
                }

                @Override
                public void saveCallResult(T data) {
                    if (onSave != null) {
                        onSave.accept(data);
                    }
                }
            };
        }, BackpressureStrategy.BUFFER);
    }
}