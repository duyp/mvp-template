package com.duyp.architecture.mvp.base;

/**
 * Created by air on 5/20/17.
 *
 */

public interface BaseView {

    void onError(int code, String message);

    void showProgress();

    void showProgress(String message);

    void hideProgress();
}