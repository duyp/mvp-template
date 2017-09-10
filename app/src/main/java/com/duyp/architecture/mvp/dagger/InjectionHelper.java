package com.duyp.architecture.mvp.dagger;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.duyp.architecture.mvp.app.MyApplication;
import com.duyp.architecture.mvp.dagger.component.AppComponent;
import com.duyp.architecture.mvp.dagger.component.UserComponent;

public class InjectionHelper {

    public static AppComponent getAppComponent(Context context) {
        return MyApplication.get(context).getAppComponent();
    }
    public static AppComponent getAppComponent(Fragment fragment) {
        return getAppComponent(fragment.getActivity());
    }

    public static AppComponent getAppComponent(Activity activity) {
        return MyApplication.get(activity).getAppComponent();
    }

    public static UserComponent getUserComponent(Fragment fragment) {
        return getUserComponent(fragment.getActivity());
    }

    public static UserComponent getUserComponent(Activity activity) {
        return MyApplication.get(activity).getUserComponent();
    }

    public static UserComponent getUserComponent(Context context) {
        return MyApplication.get(context).getUserComponent();
    }
}
