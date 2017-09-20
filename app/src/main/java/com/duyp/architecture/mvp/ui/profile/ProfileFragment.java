package com.duyp.architecture.mvp.ui.profile;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.duyp.androidutils.image.glide.GlideUtils;
import com.duyp.androidutils.navigator.NavigationUtils;
import com.duyp.architecture.mvp.R;
import com.duyp.architecture.mvp.base.fragment.BasePresenterFragment;
import com.duyp.architecture.mvp.app.Constants;
import com.duyp.architecture.mvp.data.model.User;
import com.duyp.architecture.mvp.utils.AvatarLoader;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.BindView;
import jp.wasabeef.blurry.Blurry;


/**
 * Created by duypham on 9/12/17.
 */

public class ProfileFragment extends BasePresenterFragment<ProfileView, ProfilePresenter> implements ProfileView {

    public static ProfileFragment newInstance(@Nullable User user) {
        return NavigationUtils.createFragmentInstance(new ProfileFragment(), bundle -> {
            bundle.putParcelable(Constants.EXTRA_DATA, Parcels.wrap(user));
        });
    }

    @BindView(R.id.bgContainer)
    FrameLayout bgContainer;
    @BindView(R.id.imvBackground)
    ImageView imvBackground;
    @BindView(R.id.imvAvatar)
    ImageView imvAvatar;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvLink)
    TextView tvLink;
    @BindView(R.id.tvBio)
    TextView tvBio;

    @BindView(R.id.btnFollow)
    ImageView btnFollow;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Inject
    AvatarLoader avatarLoader;

    @Inject
    ProfileTabAdapter adapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_profile_user;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentComponent().inject(this);
    }

    @Override
    protected void initialize(View view) {
        super.initialize(view);

        Bundle bundle = getArguments();
        if (bundle == null) {
            bundle = getActivity().getIntent().getExtras();
        }

        User user = Parcels.unwrap(bundle.getParcelable(Constants.EXTRA_DATA));
        getPresenter().initUser(user);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imvAvatar.setTransitionName(getString(R.string.transition_name_avatar));
        }
        viewPager.setAdapter(adapter);
        tab.setupWithViewPager(viewPager);
        getPresenter().refresh();
    }

    @Override
    public void onUserUpdated(@Nullable User user) {
        if (user != null) {
            tvName.setText(user.getName());
            tvLink.setText(user.getHtmlUrl());
            tvBio.setText(user.getBio());
            GlideUtils.loadImageBitmap(getContext(), user.getAvatarUrl(), bitmap -> {
                imvAvatar.setImageBitmap(bitmap);
                Blurry.with(getContext()).radius(25).from(bitmap).into(imvBackground);
            });
        }
    }
}
