package com.duyp.architecture.mvp.data.repos;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;

import com.duyp.architecture.mvp.base.data.BaseRepo;
import com.duyp.architecture.mvp.base.data.LiveRealmObject;
import com.duyp.architecture.mvp.data.Resource;
import com.duyp.architecture.mvp.data.local.dao.UserDao;
import com.duyp.architecture.mvp.data.model.User;
import com.duyp.architecture.mvp.data.remote.GithubService;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by duypham on 9/20/17.
 *
 */

public class UserRepo extends BaseRepo{

    private final UserDao userDao;

    private LiveRealmObject<User> user;

    @Inject
    public UserRepo(LifecycleOwner owner, GithubService githubService, UserDao userDao) {
        super(owner, githubService);
        this.userDao = userDao;
    }

    public LiveRealmObject<User> initUser(@NonNull String userLogin) {
        return user = userDao.getUser(userLogin);
    }

    public Flowable<Resource<User>> fetchUser() {
        return createResource(getGithubService().getUser(user.getData().getLogin()), userDao::addOrUpdate);
    }

}
