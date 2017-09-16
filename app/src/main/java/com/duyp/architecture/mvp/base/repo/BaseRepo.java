package com.duyp.architecture.mvp.base.repo;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.support.annotation.Nullable;

import com.duyp.androidutils.functions.PlainConsumer;
import com.duyp.architecture.mvp.data.Resource;
import com.duyp.architecture.mvp.data.SimpleNetworkBoundSourceLiveData;
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

    public BaseRepo(LifecycleOwner owner, GithubService githubService) {
        this.githubService = githubService;
        this.owner = owner;
    }

    protected <T> Flowable<Resource<T>> createResource(@Nullable Single<Response<T>> remote,
                                                       @Nullable LiveData<T> local,
                                                       @Nullable PlainConsumer<T> onSave) {
        return Flowable.create(emitter -> {

            new SimpleNetworkBoundSourceLiveData<T>(owner, emitter) {
                @Override
                public Single<Response<T>> getRemote() {
                    return remote;
                }

                @Override
                public LiveData<T> getLocal() {
                    return local;
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