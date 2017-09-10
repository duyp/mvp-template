package com.duyp.architecture.mvp.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.duyp.architecture.mvp.dagger.component.AppComponent;
import com.duyp.architecture.mvp.dagger.component.DaggerAppComponent;
import com.duyp.architecture.mvp.dagger.component.UserComponent;
import com.duyp.architecture.mvp.dagger.module.AppModule;
import com.duyp.architecture.mvp.dagger.module.NetworkModule;
import com.duyp.architecture.mvp.data.local.user.UserManager;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Duy Pham on 09/2017.
 *
 */
public class MyApplication extends Application {

    @Setter
    protected AppComponent appComponent;

    @Getter
    @Inject
    UserManager userManager;

    @Getter
    RefWatcher refWatcher;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        refWatcher = LeakCanary.install(this);
    }

    // component
    protected void setupAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule(this))
                .build();
        appComponent.inject(this);
    }

    public AppComponent getAppComponent() {
        if (appComponent == null) {
            setupAppComponent();
        }
        return appComponent;
    }

    @Nullable
    public UserComponent getUserComponent() {
        return userManager.getUserComponent();
    }

    public static MyApplication get(Activity activity) {
        return (MyApplication)activity.getApplication();
    }

    public static MyApplication get(Fragment fragment) {
        return get(fragment.getActivity());
    }

    public static MyApplication get(Context context) {
        return (MyApplication)context.getApplicationContext();
    }
}
