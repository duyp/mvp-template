package com.duyp.architecture.mvp.utils.api;

import com.duyp.architecture.mvp.data.model.base.ErrorEntity;

/**
 * Created by duypham on 7/10/17.
 *
 */

public interface OnRequestErrorListener {
    void onError(ErrorEntity error);
}
