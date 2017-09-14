package com.duyp.architecture.mvp.base;

import com.duyp.architecture.mvp.data.model.base.ErrorEntity;

/**
 * Created by air on 5/20/17.
 *
 */

public interface BaseView {

    void onError(ErrorEntity errorEntity);

    void showProgress();

    void showProgress(String message);

    void hideProgress();

    void showMessage(String message);
}