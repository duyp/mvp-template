package com.duyp.architecture.mvp;

import android.content.Context;
import android.support.annotation.NonNull;

import com.duyp.architecture.mvp.data.local.user.UserManager;
import com.duyp.architecture.mvp.data.local.user.UserDataStore;
import com.duyp.architecture.mvp.data.remote.GithubService;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by duypham on 9/10/17.
 *
 */

public class TestUserManager extends UserManager {

    public TestUserManager(@NonNull Context context, @NonNull UserDataStore userDataStore, @NonNull EventBus eventBus, @NonNull GithubService service) {
        super(context, userDataStore, eventBus, service);
    }
}
