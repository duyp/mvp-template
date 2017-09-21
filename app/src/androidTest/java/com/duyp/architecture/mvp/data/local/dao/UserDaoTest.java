package com.duyp.architecture.mvp.data.local.dao;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.duyp.architecture.mvp.base.data.LiveRealmObject;
import com.duyp.architecture.mvp.dagger.TestAppComponent;
import com.duyp.architecture.mvp.data.model.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.realm.Realm;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by duypham on 9/21/17.
 *
 */

public class UserDaoTest extends BaseDaoTest {

    private UserDao userDao;

    @Override
    public void inject(TestAppComponent appComponent) {
        appComponent.inject(this);
        userDao = realmDatabase.getUserDao();
    }

    @Test
    public void saveAndGetUser() {
        Log.d(TAG, "saveAndGetUser: ");
        User user = sampleUser(123L);
        userDao.addOrUpdate(user);

        User savedUser = userDao.getById(user.getId()).getData();
        assertThat(savedUser.getLogin(), equalTo(user.getLogin()));
        assertThat(savedUser.getBio(), equalTo(user.getBio()));

        User savedUser1 = userDao.getUser(user.getLogin()).getData();
        assertThat(savedUser1.getLogin(), equalTo(user.getLogin()));
        assertThat(savedUser1.getBio(), equalTo(user.getBio()));
    }

    @Test
    public void getNotExistUser() {
        Log.d(TAG, "getNotExistUser: ");
        User user = userDao.getUser("fgsdfg").getData();
        assertThat(user, equalTo(null));
    }
}