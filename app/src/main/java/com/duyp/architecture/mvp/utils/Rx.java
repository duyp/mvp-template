package com.duyp.architecture.mvp.utils;

import android.support.annotation.NonNull;

import com.duyp.androidutils.rx.functions.PlainFunction;
import com.duyp.androidutils.rx.functions.PlainPredicate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by duypham on 7/31/17.
 * Utilities functions for functional reactive programming
 * Reference: http://blog.danlew.net/2017/07/27/an-introduction-to-functional-reactive-programming/
 */

public class Rx {

    /**
     * Map a input list to output list by a {@link com.duyp.androidutils.rx.functions.PlainFunction}
     * @param input input List
     * @param func function to map
     * @param <T> Input Type
     * @param <R> Return Type
     * @return mapped list
     */
    @NonNull
    public static <T, R> List<R> map(@NonNull List<T> input, @NonNull PlainFunction<T, R> func) {
        List<R> output = new ArrayList<>();
        for (T t : input) {
            output.add(func.apply(t));
        }
        return output;
    }

    /**
     * Filter a list by a {@link com.duyp.androidutils.rx.functions.PlainPredicate}
     * @param input input list
     * @param predicate predicate to filter
     * @param <T> input Type
     * @return filtered list
     */
    @NonNull
    public static <T> List<T> filter(@NonNull List<T> input, @NonNull PlainPredicate<T> predicate) {
        List<T> out = new ArrayList<T>();
        for (T t : input) {
            if (predicate.test(t)) {
                out.add(t);
            }
        }
        return out;
    }

    /**
     *
     * Filter a array by a {@link PlainPredicate}
     * @param input input array
     * @param predicate predicate to filter
     * @param <T> input Type
     * @return filtered list
     */
    @NonNull
    public static <T> List<T> filter(@NonNull T[] input, @NonNull PlainPredicate<T> predicate) {
        List<T> out = new ArrayList<T>();
        for (T t : input) {
            if (predicate.test(t)) {
                out.add(t);
            }
        }
        return out;
    }

    /**
     *
     * Filter indices of a list by a {@link PlainPredicate}
     * @param input input array
     * @param predicate predicate to filter
     * @param <T> input Type
     * @return array contains list of boolean which be filtered
     */
    @NonNull
    public static <T> boolean[] filterIndex(@NonNull List<T> input, @NonNull PlainPredicate<T> predicate) {
        boolean[] result = new boolean[input.size()];
        int i = 0;
        for (T t : input) {
            result[i++] = predicate.test(t);
        }
        return result;
    }
}
