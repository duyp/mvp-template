package com.duyp.architecture.mvp.ui.repositories;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.duyp.androidutils.adapter.BaseRecyclerAdapter;
import com.duyp.androidutils.functions.PlainConsumer;
import com.duyp.architecture.mvp.R;
import com.duyp.architecture.mvp.dagger.qualifier.ActivityContext;
import com.duyp.architecture.mvp.data.model.Repository;
import com.duyp.architecture.mvp.ui.customviews.CustomRepositoryView;
import com.duyp.architecture.mvp.utils.AvatarLoader;
import com.duyp.architecture.mvp.utils.BaseRecyclerViewAdapter;
import com.duyp.architecture.mvp.utils.BaseViewHolder;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import lombok.Setter;

/**
 * Created by phamd on 9/14/2017.
 *
 */

public class RepositoryAdapter extends BaseRecyclerViewAdapter<Repository> {

    private final AvatarLoader mAvatarLoader;

    @Inject
    public RepositoryAdapter(@ActivityContext @NonNull Context context, AvatarLoader avatarLoader) {
        super(context);
        mAvatarLoader = avatarLoader;
    }

    @Override
    protected RecyclerView.ViewHolder createHolder(ViewGroup viewGroup, int i) {
        return new RepoViewHolder(mInflater.inflate(R.layout.view_item_repository, viewGroup, false));
    }

    @Override
    protected void bindHolder(RecyclerView.ViewHolder viewHolder, @NonNull Repository repository) {
        ((RepoViewHolder)viewHolder).repositoryView.bindData(repository, mAvatarLoader);
    }

    class RepoViewHolder extends BaseViewHolder {

        @BindView(R.id.repositoryView)
        CustomRepositoryView repositoryView;

        public RepoViewHolder(View itemView) {
            super(itemView);
        }
    }
}
