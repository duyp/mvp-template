package com.duyp.architecture.mvp.ui.repository_detail.commits;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.duyp.architecture.mvp.R;
import com.duyp.architecture.mvp.dagger.qualifier.ActivityContext;
import com.duyp.architecture.mvp.data.model.Commit;
import com.duyp.architecture.mvp.data.model.User;
import com.duyp.architecture.mvp.utils.AvatarLoader;
import com.duyp.architecture.mvp.utils.BaseRecyclerViewAdapter;
import com.duyp.architecture.mvp.utils.BaseViewHolder;
import com.duyp.architecture.mvp.utils.RelativeTimeTextView;
import com.mikhaellopez.circularimageview.CircularImageView;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by duypham on 9/18/17.
 */

public class CommitsAdapter extends BaseRecyclerViewAdapter<Commit> {

    private final AvatarLoader avatarLoader;

    @Inject
    public CommitsAdapter(@ActivityContext @NonNull Context context, AvatarLoader avatarLoader) {
        super(context);
        this.avatarLoader = avatarLoader;
    }

    @Override
    protected void bindHolder(RecyclerView.ViewHolder viewHolder, @NonNull Commit item) {
        ((CommitHolder)viewHolder).bindData(item);
    }

    @Override
    protected RecyclerView.ViewHolder createHolder(ViewGroup viewGroup, int i) {
        return new CommitHolder(mInflater.inflate(R.layout.view_item_commit, viewGroup, false));
    }

    class CommitHolder extends BaseViewHolder {

        @BindView(R.id.imvAvatar)
        CircularImageView imvAvatar;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvSha)
        TextView tvSha;
        @BindView(R.id.tvDes)
        TextView tvDes;
        @BindView(R.id.tvTime)
        RelativeTimeTextView tvTime;

        public CommitHolder(View itemView) {
            super(itemView);
        }

        void bindData(@NonNull Commit commit) {
            User user = commit.getUser();
            if (user!=null) {
                avatarLoader.loadImage(user.getAvatarUrl(), imvAvatar);
                tvDes.setText(getContext().getString(R.string.commit_description_format, user.getLogin()));
            }
            tvTitle.setText(commit.getCommitDetail().getMessage());
            tvSha.setText(commit.getSha());
            tvTime.setReferenceTime(commit.getCommitDetail().getCommitter().getDate());
        }
    }
}
