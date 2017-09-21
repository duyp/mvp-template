package com.duyp.architecture.mvp;

import com.duyp.architecture.mvp.app.MyApplication;
import com.duyp.architecture.mvp.dagger.DaggerTestAppComponent;
import com.duyp.architecture.mvp.dagger.TestAppComponent;
import com.duyp.architecture.mvp.dagger.TestAppModule;
import com.duyp.architecture.mvp.dagger.TestDataModule;
import com.duyp.architecture.mvp.dagger.module.DataModule;
import com.duyp.architecture.mvp.dagger.module.NetworkModule;

/**
 * Created by duypham on 9/21/17.
 * Test application
 */

public class TestApplication extends MyApplication{


    @Override
    protected void setupAppComponent() {
        appComponent = DaggerTestAppComponent.builder()
                .testAppModule(new TestAppModule(this))
                .networkModule(new NetworkModule(this))
                .testDataModule(new TestDataModule(this))
                .build();
        appComponent.inject(this);
    }

    @Override
    public TestAppComponent getAppComponent() {
        if (appComponent == null) {
            setupAppComponent();
        }
        return (TestAppComponent)appComponent;
    }
}
