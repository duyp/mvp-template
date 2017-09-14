package com.duyp.architecture.mvp.ui.main;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import com.duyp.architecture.mvp.R;
import com.duyp.architecture.mvp.base.activity.BasePresenterActivity;
import com.duyp.architecture.mvp.data.model.User;
import com.duyp.architecture.mvp.ui.profile.ProfileFragment;
import com.duyp.architecture.mvp.utils.AvatarLoader;
import com.duyp.architecture.mvp.utils.interfaces.SimpleActionBarToggle;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends BasePresenterActivity<MainView, MainPresenter> implements MainView, ProfileFragment.Callback {

    private static final String TAG = "MainActivity";

    public static final int DRAWER_MENU_ITEM_PROFILE = 0;
    public static final int DRAWER_MENU_ITEM_REQUESTS = 1;
    public static final int DRAWER_MENU_ITEM_GROUPS = 2;
    public static final int DRAWER_MENU_ITEM_MESSAGE = 3;
    public static final int DRAWER_MENU_ITEM_NOTIFICATIONS = 4;
    public static final int DRAWER_MENU_ITEM_SETTINGS = 5;
    public static final int DRAWER_MENU_ITEM_TERMS = 6;
    public static final int DRAWER_MENU_ITEM_LOGOUT = 7;

    @BindView(R.id.drawerView)
    CustomDrawerView mDrawerView;
    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawer;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Inject
    AvatarLoader avatarLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ensureInUserScope(userActivityComponent -> {
            userActivityComponent.inject(this);
            initialize();
        }, () -> {
            activityComponent().inject(this);
            initialize();
        });
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initialize(){
        super.initialize();
        initDrawerItems();
        ActionBarDrawerToggle drawerToggle = new SimpleActionBarToggle(this, mDrawer, mToolbar);
        mDrawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getPresenter().init();
    }

    private void initDrawerItems() {
        mDrawerView.addView(new DrawerHeader(this, avatarLoader, mDrawerView, this::onProfileClick));
        for (int i = 0; i < 8; i++) {
            mDrawerView.addView(new DrawerMenuItem(this.getApplicationContext(), mDrawerView, i, this::onItemSelected));
        }
    }

    private void onItemSelected(int position) {
        mDrawer.closeDrawers();
        mDrawerView.setSelectedItem(position);
        new Handler().postDelayed(() -> mDrawerView.refresh(), 800);
        getPresenter().onItemCLick(position);
    }

    private void onProfileClick() {
        getPresenter().onProfileClick();
        mDrawer.closeDrawers();
    }

    @Override
    public void onUserUpdated(User user) {
        if (mDrawerView != null) {
            mDrawerView.updateUser(user);
        }
    }

    @Override
    public void onForceLogin() {
        getPresenter().navigateLogin();
    }
}