package com.duyp.architecture.mvp.utils;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.duyp.androidutils.functions.PlainAction;
import com.duyp.androidutils.functions.PlainConsumer;

import io.reactivex.Completable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by duypham on 9/11/17.
 *
 */

public class DbTaskHelper {

    public static void doTaskOnBackground(@NonNull Action action) {
        doTaskOnBackground(action, throwable -> {});
    }

    public static void doTaskOnBackground(@NonNull Action action, PlainConsumer<Throwable> throwable) {
        Completable.create(e -> {
            action.run();
        }).subscribeOn(Schedulers.computation()).subscribe(()->{}, throwable);
    }
}
