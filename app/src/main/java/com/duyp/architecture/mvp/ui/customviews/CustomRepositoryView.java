package com.duyp.architecture.mvp.ui.customviews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.duyp.androidutils.functions.PlainConsumer;
import com.duyp.architecture.mvp.R;
import com.duyp.architecture.mvp.data.model.Repository;
import com.duyp.architecture.mvp.utils.AvatarLoader;
import com.mikhaellopez.circularimageview.CircularImageView;

import butterknife.BindView;

/**
 * Created by phamd on 9/14/2017.
 *
 */

public class CustomRepositoryView extends BaseRelativeLayout<Repository> {


    @BindView(R.id.imvAvatar)
    CircularImageView imvAvatar;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvDes)
    TextView tvDes;
    @BindView(R.id.tvLanguage)
    TextView tvLanguage;
    @BindView(R.id.tvAccess)
    TextView tvAccess;

    public CustomRepositoryView(Context context) {
        super(context);
    }

    public CustomRepositoryView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initView(Context context) {
        super.initView(context);
    }

    @Override
    protected int getLayout() {
        return R.layout.view_custom_repository;
    }

    public void bindData(Repository data, AvatarLoader avatarLoader) {
        super.bindData(data);
        tvName.setText(data.getFullName());
        tvDes.setText(data.getDescription());
        avatarLoader.loadImage(data.getOwner().getAvatarUrl(), imvAvatar);
        tvLanguage.setText(getContext().getString(R.string.repo_language_format, data.getLanguage()));
        tvAccess.setText(getContext().getString(R.string.repo_access_format, data.get_private() ? "Private" : "Public"));
    }
}
