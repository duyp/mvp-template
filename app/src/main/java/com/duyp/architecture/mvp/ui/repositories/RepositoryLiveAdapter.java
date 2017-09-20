package com.duyp.architecture.mvp.ui.repositories;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.duyp.architecture.mvp.R;
import com.duyp.architecture.mvp.base.adapter.BaseRealmLiveDataAdapter;
import com.duyp.architecture.mvp.dagger.qualifier.ActivityContext;
import com.duyp.architecture.mvp.data.model.Repository;
import com.duyp.architecture.mvp.ui.customviews.CustomRepositoryView;
import com.duyp.architecture.mvp.utils.AvatarLoader;
import com.duyp.architecture.mvp.utils.BaseViewHolder;
import com.duyp.architecture.mvp.utils.NavigatorHelper;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by phamd on 9/14/2017.
 *
 */

public class RepositoryLiveAdapter extends BaseRealmLiveDataAdapter<Repository, RepositoryLiveAdapter.RepoViewHolder> {

    private final AvatarLoader mAvatarLoader;

    private final NavigatorHelper mNavigator;

    @Inject
    public RepositoryLiveAdapter(@ActivityContext Context context, LifecycleOwner owner,
                                 AvatarLoader avatarLoader, NavigatorHelper navigatorHelper) {
        super(context, owner);
        mAvatarLoader = avatarLoader;
        mNavigator = navigatorHelper;
    }

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new RepoViewHolder(mInflater.inflate(R.layout.view_item_repository, viewGroup, false));
    }

    @Override
    public void bindHolder(RepoViewHolder viewHolder, @NonNull Repository repository) {
        ((RepoViewHolder)viewHolder).repositoryView.bindData(repository, mAvatarLoader);
    }

    class RepoViewHolder extends BaseViewHolder {

        @BindView(R.id.repositoryView)
        CustomRepositoryView repositoryView;

        public RepoViewHolder(View itemView) {
            super(itemView);
            repositoryView.setAvatarClickListener(mNavigator::navigateUserProfileActivity);
            repositoryView.setItemClickListener(mNavigator::navigateRepositoryDetail);
        }
    }
}
