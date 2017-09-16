package com.duyp.architecture.mvp.ui.main;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import com.duyp.architecture.mvp.R;
import com.duyp.architecture.mvp.base.activity.BasePresenterActivity;
import com.duyp.architecture.mvp.data.model.User;
import com.duyp.architecture.mvp.ui.listeners.AccountListener;
import com.duyp.architecture.mvp.ui.profile.ProfileFragment;
import com.duyp.architecture.mvp.utils.AvatarLoader;
import com.duyp.architecture.mvp.utils.interfaces.SimpleActionBarToggle;

import static com.duyp.architecture.mvp.ui.main.DrawerMenuItem.*;
import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends BasePresenterActivity<MainView, MainPresenter> implements MainView, AccountListener {

    private static final String TAG = "MainActivity";

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
        setSupportActionBar(mToolbar);
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
        mDrawerView.addView(new DrawerHeader(this, avatarLoader, mDrawerView, () -> this.onItemSelected(0)));
        for (int i = 0; i < 8; i++) {
            mDrawerView.addView(new DrawerMenuItem(this.getApplicationContext(), mDrawerView, i, this::onItemSelected));
        }
    }

    private void onItemSelected(int position) {
        setTitle(MENU_TITLES[position]);
        mDrawer.closeDrawers();
        mDrawerView.setSelectedItem(position);
        new Handler().postDelayed(() -> mDrawerView.refresh(), 800);
        getPresenter().onItemCLick(position);
    }

    private void onProfileClick() {
        getPresenter().navigateProfile();
        mDrawer.closeDrawers();
    }

    @Override
    public void onUserUpdated(User user) {
        if (mDrawerView != null) {
            mDrawerView.updateUser(user);
        }
    }

    @Override
    public void setTitle(String title) {
        // noinspection ConstantConditions
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void forceLogin() {
        getPresenter().navigateLogin();
    }
}