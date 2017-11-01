package com.duyp.architecture.mvp.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.duyp.androidutils.rx.functions.PlainConsumer;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by duypham on 9/11/17.
 *
 */

public class DbTaskHelper {

    public static void doTaskOnBackground(@NonNull Action action) {
        doTaskOnBackground(action, null, null);
    }

    public static void doTaskOnBackground(@NonNull Action action, @Nullable Action onComplete) {
        doTaskOnBackground(action, onComplete, null);
    }

    public static void doTaskOnBackground(@NonNull Action action, @Nullable PlainConsumer<Throwable> throwable) {
        doTaskOnBackground(action, null, throwable);
    }

    public static void doTaskOnBackground(@NonNull Action action, @Nullable Action onComplete, @Nullable PlainConsumer<Throwable> throwable) {
        Completable.create(e -> {
            action.run(); // run on thread which is subscribe on (upstream)
            e.onComplete(); // -> onComplete will run on thread which is ObserveOn (downstream)
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(onComplete != null ? onComplete : () -> {},
                        throwable != null ? throwable : throwable1 -> {});
    }
}
