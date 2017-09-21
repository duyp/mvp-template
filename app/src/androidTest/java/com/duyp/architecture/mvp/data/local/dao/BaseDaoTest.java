package com.duyp.architecture.mvp.data.local.dao;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.duyp.architecture.mvp.BaseTest;
import com.duyp.architecture.mvp.TestApplication;
import com.duyp.architecture.mvp.dagger.TestAppComponent;
import com.duyp.architecture.mvp.data.local.RealmDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by duypham on 9/21/17.
 *
 */
public abstract class BaseDaoTest extends BaseTest{

    @Inject
    RealmDatabase realmDatabase;

    @Before
    public void setUp() throws Exception {
        inject(getTestApplication().getAppComponent());
    }

    @After
    public void tearDown() throws Exception {
        realmDatabase.close();
    }

    public abstract void inject(TestAppComponent appComponent);
}
