package com.duyp.architecture.mvp.ui.profile;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.transition.TransitionInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.duyp.architecture.mvp.R;
import com.duyp.architecture.mvp.base.fragment.BaseSwipeToRefreshFragment;
import com.duyp.architecture.mvp.data.model.User;
import com.duyp.architecture.mvp.ui.listeners.AccountListener;
import com.duyp.architecture.mvp.utils.AvatarLoader;

import javax.inject.Inject;

import butterknife.BindView;
/**
 * Created by duypham on 9/12/17.
 *
 */

public class ProfileFragment extends BaseSwipeToRefreshFragment<ProfileView, ProfilePresenter> implements ProfileView{

    @BindView(R.id.imvAvatar)
    ImageView imvAvatar;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvLink)
    TextView tvLink;

    @Inject
    AvatarLoader avatarLoader;

    @Nullable
    private AccountListener mCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AccountListener) {
            mCallback = (AccountListener)context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_profile;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ensureInUserScope(userFragmentComponent -> userFragmentComponent.inject(this), this::forceLogin);
    }

    @Override
    protected void initialize(View view) {
        super.initialize(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imvAvatar.setTransitionName(getString(R.string.transition_name_avatar));
        }
    }

    @Override
    public void refresh() {
        getPresenter().updateMyUser();
    }

    @Override
    public void onUserUpdated(@Nullable User user) {
        if (user != null) {
            tvName.setText(user.getName());
            tvLink.setText(user.getHtmlUrl());
            avatarLoader.loadImage(user.getAvatarUrl(), imvAvatar);
        } else {
            forceLogin();
        }
    }

    private void forceLogin() {
        if (mCallback != null) {
            mCallback.forceLogin();
        }
    }
}
