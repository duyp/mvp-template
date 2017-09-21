package com.duyp.architecture.mvp.dagger;

import com.duyp.architecture.mvp.dagger.component.AppComponent;
import com.duyp.architecture.mvp.dagger.module.DataModule;
import com.duyp.architecture.mvp.dagger.module.NetworkModule;
import com.duyp.architecture.mvp.data.local.dao.IssueDaoTest;
import com.duyp.architecture.mvp.data.local.dao.RepositoryDaoTest;
import com.duyp.architecture.mvp.data.local.dao.UserDaoTest;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by duypham on 9/21/17.
 *
 */

@Component(
        modules = {TestAppModule.class, TestDataModule.class, NetworkModule.class}
)
@Singleton
public interface TestAppComponent extends AppComponent {

    void inject(UserDaoTest test);
    void inject(IssueDaoTest test);
    void inject(RepositoryDaoTest test);
}
