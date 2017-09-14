package com.duyp.architecture.mvp.utils.api;

import com.duyp.architecture.mvp.data.model.base.ApiResponse;
import com.duyp.architecture.mvp.data.model.base.BaseResponse;

/**
 * Created by duypham on 6/23/17.
 *
 */

public interface OnRequestSuccessListener<T> {
    void onRequestSuccess(T response);
}