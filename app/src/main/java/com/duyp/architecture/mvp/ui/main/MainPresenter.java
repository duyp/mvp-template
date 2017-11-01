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

import static com.duyp.architecture.mvp.ui.main.DrawerMenuItem.DRAWER_MENU_ITEM_ALL_REPOSITORIES;
import static com.duyp.architecture.mvp.ui.main.DrawerMenuItem.DRAWER_MENU_ITEM_LOGOUT;
import static com.duyp.architecture.mvp.ui.main.DrawerMenuItem.DRAWER_MENU_ITEM_MESSAGE;
import static com.duyp.architecture.mvp.ui.main.DrawerMenuItem.DRAWER_MENU_ITEM_MY_REPOSITORIES;
import static com.duyp.architecture.mvp.ui.main.DrawerMenuItem.DRAWER_MENU_ITEM_NOTIFICATIONS;
import static com.duyp.architecture.mvp.ui.main.DrawerMenuItem.DRAWER_MENU_ITEM_PROFILE;
import static com.duyp.architecture.mvp.ui.main.DrawerMenuItem.DRAWER_MENU_ITEM_SETTINGS;
import static com.duyp.architecture.mvp.ui.main.DrawerMenuItem.DRAWER_MENU_ITEM_TERMS;

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
        navigateProfile();
    }

    void onItemCLick(int position) {
        switch (position){
            case DRAWER_MENU_ITEM_PROFILE:
                navigateProfile();
                break;
            case DRAWER_MENU_ITEM_MY_REPOSITORIES:
                navigateMyRepositories();
                break;
            case DRAWER_MENU_ITEM_ALL_REPOSITORIES:
                mNavigatorHelper.navigateAllRepositoriesFragment(getContainerId());
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

    private void navigateProfile() {
        if (getUserManager().isUserSessionStarted()) {
            mNavigatorHelper.navigateUserProfile(getContainerId(), null);
        } else {
            navigateLogin();
        }
    }

    void navigateLogin() {
        getView().setTitle("Login");
        getView().updateSelectedItem(DRAWER_MENU_ITEM_PROFILE);
        mNavigatorHelper.navigateLoginFragment(getContainerId());
    }

    private void navigateMyRepositories() {
        if (getUserManager().isUserSessionStarted()) {
            mNavigatorHelper.navigateMyRepositoriesFragment(getContainerId());
        } else {
            navigateLogin();
        }
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

    private int getContainerId() {
        return R.id.container;
    }
}
