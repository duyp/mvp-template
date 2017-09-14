package com.duyp.architecture.mvp.ui.repositories;

import android.content.Context;

import com.duyp.architecture.mvp.app.AppDatabase;
import com.duyp.architecture.mvp.dagger.qualifier.ActivityContext;
import com.duyp.architecture.mvp.data.local.RepositoryDao;
import com.duyp.architecture.mvp.data.local.user.UserManager;

import javax.inject.Inject;

/**
 * Created by phamd on 9/14/2017.
 * presenter for current user 's repositories fragment (IN USER SCOPE)
 */

public class UserRepositoryPresenter extends RepositoryPresenter {

    @Inject
    public UserRepositoryPresenter(@ActivityContext Context context, UserManager userManager, AppDatabase appDatabase, RepositoryAdapter adapter) {
        super(context, userManager, appDatabase, adapter);
    }
}
