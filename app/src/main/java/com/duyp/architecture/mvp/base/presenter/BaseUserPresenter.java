package com.duyp.architecture.mvp.base.presenter;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.duyp.architecture.mvp.base.BaseView;
import com.duyp.architecture.mvp.dagger.qualifier.ActivityContext;
import com.duyp.architecture.mvp.data.local.user.UserManager;
import com.duyp.architecture.mvp.data.model.User;
import com.duyp.architecture.mvp.data.remote.UserService;

import lombok.Getter;

/**
 * Created by air on 5/19/17.
 * Base Presenter for all activity / fragment in user scope
 * All presenter extending this base must be injected by user component or sub-components of user component
 * in USER SCOPE
 */

@Getter
public abstract class BaseUserPresenter<V extends BaseView> extends BasePresenter<V>{

    private final UserService userService;

    private final LiveData<User> userLiveData;

    public BaseUserPresenter(@ActivityContext Context context, LiveData<User> user, UserService userService, UserManager userManager) {
        super(context, userManager);
        this.userService = userService;
        userLiveData = user;
    }
}