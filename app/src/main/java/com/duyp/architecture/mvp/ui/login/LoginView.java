package com.duyp.architecture.mvp.ui.login;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.duyp.architecture.mvp.base.BaseView;
import com.duyp.architecture.mvp.data.model.User;

/**
 * Created by duypham on 9/12/17.
 *
 */

public interface LoginView extends BaseView {
    void onLoginSuccess(User user);
}