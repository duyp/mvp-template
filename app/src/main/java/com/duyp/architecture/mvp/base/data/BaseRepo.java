package com.duyp.architecture.mvp.base.data;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.support.annotation.Nullable;

import com.duyp.androidutils.rx.functions.PlainConsumer;
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
     * For single data
     * @param remote
     * @param onSave
     * @param <T>
     * @return
     */
    protected <T> Flowable<Resource<T>> createResource(@Nullable Single<Response<T>> remote,
                                                       @Nullable PlainConsumer<T> onSave) {
        return Flowable.create(emitter -> {
            new SimpleNetworkBoundSource<T>(emitter, true) {

                @Override
                public Single<Response<T>> getRemote() {
                    return remote;
                }

                @Override
                public void saveCallResult(T data, boolean isRefresh) {
                    if (onSave != null) {
                        onSave.accept(data);
                    }
                }
            };
        }, BackpressureStrategy.BUFFER);
    }

    /**
     * For a list of data
     * @param isRefresh
     * @param remote
     * @param onSave
     * @param <T>
     * @return
     */
    protected <T> Flowable<Resource<T>> createResource(boolean isRefresh, @Nullable Single<Response<T>> remote,
                                                       @Nullable OnSaveResultListener<T> onSave) {
        return Flowable.create(emitter -> {
            new SimpleNetworkBoundSource<T>(emitter, isRefresh) {

                @Override
                public Single<Response<T>> getRemote() {
                    return remote;
                }

                @Override
                public void saveCallResult(T data, boolean isRefresh) {
                    if (onSave != null) {
                        onSave.onSave(data, isRefresh);
                    }
                }
            };
        }, BackpressureStrategy.BUFFER);
    }

    protected interface OnSaveResultListener<T> {
        void onSave(T data, boolean isRefresh);
    }
}