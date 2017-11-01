package com.duyp.architecture.mvp.ui.profile;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.duyp.architecture.mvp.R;
import com.duyp.architecture.mvp.base.adapter.BasePagerAdapterWithIcon;
import com.duyp.architecture.mvp.dagger.qualifier.ChildFragmentManager;
import com.duyp.architecture.mvp.data.model.User;
import com.duyp.architecture.mvp.ui.login.LoginFragment;
import com.duyp.architecture.mvp.ui.user_repositories.UserRepositoryFragment;

import javax.inject.Inject;

import lombok.Setter;

/**
 * Created by duypham on 9/20/17.
 *
 */

public class ProfileTabAdapter extends BasePagerAdapterWithIcon {

    public static final int[] ICONS = new int[] {R.drawable.ic_clock, R.drawable.ic_alert, R.drawable.ic_commit, R.drawable.ic_tag};
    public static final String[] TITLES = new String[] {"Repositories", "Issues", "Branches", "Releases"};

    @Setter
    private User user;

    @Inject
    public ProfileTabAdapter(@ChildFragmentManager FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return UserRepositoryFragment.createInstance(user);
            default: return new LoginFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public int getPageIcon(int position) {
        return ICONS[position];
    }
}
