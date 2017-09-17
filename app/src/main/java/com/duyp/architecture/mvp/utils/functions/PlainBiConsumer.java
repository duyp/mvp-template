package com.duyp.architecture.mvp.utils.functions;

/**
 * Created by duypham on 9/17/17.
 */

public interface PlainBiConsumer<T1, T2> extends io.reactivex.functions.BiConsumer<T1, T2> {
    /**
     * Performs an operation on the given values.
     * @param t1 the first value
     * @param t2 the second value
     */
    void accept(T1 t1, T2 t2);
}
