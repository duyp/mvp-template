package com.duyp.architecture.mvp.ui.repository_detail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.duyp.androidutils.adapter.BaseFragmentStatePagerAdapter;
import com.duyp.architecture.mvp.R;
import com.duyp.architecture.mvp.base.adapter.BasePagerAdapterWithIcon;
import com.duyp.architecture.mvp.base.fragment.BaseFragment;
import com.duyp.architecture.mvp.dagger.qualifier.ChildFragmentManager;
import com.duyp.architecture.mvp.dagger.scopes.PerFragment;
import com.duyp.architecture.mvp.data.model.Repository;
import com.duyp.architecture.mvp.ui.profile.ProfileFragment;
import com.duyp.architecture.mvp.ui.repository_detail.commits.CommitFragment;
import com.duyp.architecture.mvp.ui.repository_detail.issues.IssuesFragment;

import javax.inject.Inject;

import lombok.Setter;

@PerFragment
class RepoTabAdapter extends BasePagerAdapterWithIcon {

    public static final int[] ICONS = new int[] {R.drawable.ic_clock, R.drawable.ic_alert, R.drawable.ic_commit, R.drawable.ic_tag};
    public static final String[] TITLES = new String[] {"Commits", "Issues", "Branches", "Releases"};

    @Setter
    private Long repoId;

    @Inject
    RepoTabAdapter(@ChildFragmentManager FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return CommitFragment.newInstance(repoId);
            case 1:
                return IssuesFragment.newInstance(repoId);
            default:
                return CommitFragment.newInstance(repoId);
        }
    }

    @Override
    public int getCount() {
            return TITLES.length;
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