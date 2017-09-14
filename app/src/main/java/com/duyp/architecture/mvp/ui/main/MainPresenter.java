package com.duyp.architecture.mvp.ui.main;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.duyp.architecture.mvp.R;
import com.duyp.architecture.mvp.base.presenter.BasePresenter;
import com.duyp.architecture.mvp.dagger.qualifier.ActivityContext;
import com.duyp.architecture.mvp.data.local.user.UserManager;
import com.duyp.architecture.mvp.data.model.User;
import com.duyp.architecture.mvp.utils.NavigatorHelper;

import javax.inject.Inject;

import static com.duyp.architecture.mvp.ui.main.MainActivity.DRAWER_MENU_ITEM_GROUPS;
import static com.duyp.architecture.mvp.ui.main.MainActivity.DRAWER_MENU_ITEM_LOGOUT;
import static com.duyp.architecture.mvp.ui.main.MainActivity.DRAWER_MENU_ITEM_MESSAGE;
import static com.duyp.architecture.mvp.ui.main.MainActivity.DRAWER_MENU_ITEM_NOTIFICATIONS;
import static com.duyp.architecture.mvp.ui.main.MainActivity.DRAWER_MENU_ITEM_PROFILE;
import static com.duyp.architecture.mvp.ui.main.MainActivity.DRAWER_MENU_ITEM_REQUESTS;
import static com.duyp.architecture.mvp.ui.main.MainActivity.DRAWER_MENU_ITEM_SETTINGS;
import static com.duyp.architecture.mvp.ui.main.MainActivity.DRAWER_MENU_ITEM_TERMS;

/**
 * Created by duypham on 9/12/17.
 * Presenter for {@link MainActivity}
 */
 class MainPresenter extends BasePresenter<MainView> {

    private final NavigatorHelper mNavigatorHelper;

    private LiveData<User> mUserLiveData;

    @Inject
    MainPresenter(@ActivityContext Context context, UserManager userManager, NavigatorHelper navigatorHelper) {
        super(context, userManager);
        mNavigatorHelper = navigatorHelper;
    }

    void init() {
        initUserLiveData(getUserRepo().getUserLiveData());
        if (getUserManager().isUserSessionStarted()) {
            navigateUserProfile();
        } else {
            navigateLogin();
        }
    }

    void onItemCLick(int position) {
        switch (position){
            case DRAWER_MENU_ITEM_PROFILE:
                onProfileClick();
                break;
            case DRAWER_MENU_ITEM_REQUESTS:
                mNavigatorHelper.replaceAllRepositoriesFragment(getContainerId());
                break;
            case DRAWER_MENU_ITEM_GROUPS:
                break;
            case DRAWER_MENU_ITEM_MESSAGE:
                break;
            case DRAWER_MENU_ITEM_NOTIFICATIONS:
                break;
            case DRAWER_MENU_ITEM_SETTINGS:
                break;
            case DRAWER_MENU_ITEM_TERMS:
                break;
            case DRAWER_MENU_ITEM_LOGOUT:
                getUserManager().logout();
                break;
        }
    }

    private void navigateUserProfile() {
        mNavigatorHelper.navigateUserProfile(getContainerId());
    }

    void onProfileClick() {
        if (getUserManager().isUserSessionStarted()) {
            mNavigatorHelper.navigateUserProfile(getContainerId());
        } else {
            navigateLogin();
        }
    }

    void navigateLogin() {
        mNavigatorHelper.replaceLoginFragment(getContainerId());
    }

    private void initUserLiveData(@NonNull LiveData<User> userLiveData) {
        if (mUserLiveData == null || !mUserLiveData.equals(userLiveData)) {
            mUserLiveData = userLiveData;
            mUserLiveData.observe(getLifeCircleOwner(), user -> {
                if (getView() != null) {
                    getView().onUserUpdated(user);
                }
            });
        }
    }

    int getContainerId() {
        return R.id.container;
    }
}
