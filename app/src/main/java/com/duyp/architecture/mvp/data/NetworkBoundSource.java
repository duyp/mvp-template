package com.duyp.architecture.mvp.data;


import android.support.annotation.Nullable;

import com.duyp.androidutils.rx.functions.PlainConsumer;

import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 * <p>
 * You can read more about it in the <a href="https://developer.android.com/arch">Architecture
 * Guide</a>.
 * @param <LocalType>
 * @param <RemoteType>
 */
public class NetworkBoundSource<LocalType, RemoteType> {

    @Nullable
    Flowable<LocalType> local;

    @Nullable
    Single<Response<RemoteType>> remote;

    @Nullable
    PlainConsumer<LocalType> saveConsumer;

    @Nullable
    Function<Response<RemoteType>, LocalType> mapper;

    public NetworkBoundSource(FlowableEmitter<Resource<LocalType>> emitter) {
        Disposable firstDataDisposable =
                local == null ? null : local.map(Resource::loading).subscribe(emitter::onNext);
        remote.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(mapper)
                .subscribe(localTypeData -> {
                    if (firstDataDisposable != null) {
                        firstDataDisposable.dispose();
                    }
                    saveConsumer.accept(localTypeData);
                    local.map(Resource::success).subscribe(emitter::onNext);
                });
//        getRemote().map(mapper())
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(Schedulers.io())
//                .subscribe(localTypeData -> {
//                    firstDataDisposable.dispose();
//                    saveCallResult(localTypeData);
//                    getLocal().map(Resource::success).subscribe(emitter::onNext);
//                });
    }

    public NetworkBoundSource<LocalType, RemoteType> local(Flowable<LocalType> local) {
        this.local = local;
        return this;
    }

    public NetworkBoundSource<LocalType, RemoteType> remote(Single<Response<RemoteType>> remote) {
        this.remote = remote;
        return this;
    }

    public NetworkBoundSource<LocalType, RemoteType> mapper(Function<Response<RemoteType>, LocalType> mapper) {
        this.mapper = mapper;
        return this;
    }

    public NetworkBoundSource<LocalType, RemoteType> onSave(PlainConsumer<LocalType> consumer) {
        this.saveConsumer = consumer;
        return this;
    }



//    public abstract Observable<Response<RemoteType>> getRemote();
//
//    public abstract Flowable<LocalType> getLocal();
//
//    public abstract void saveCallResult(LocalType data);
//
//    public abstract Function<Response<RemoteType>, LocalType> mapper();



}