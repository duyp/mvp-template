package com.duyp.architecture.mvp.ui.main;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.duyp.androidutils.functions.PlainAction;
import com.duyp.androidutils.image.glide.loader.GlideLoader;
import com.duyp.architecture.mvp.R;
import com.duyp.architecture.mvp.data.model.User;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import java.lang.ref.WeakReference;

@NonReusable
@Layout(R.layout.view_drawer_item_header)
class DrawerHeader {

    @View(R.id.imvAvatar)
    private ImageView imvAvatar;

    @View(R.id.tvName)
    private TextView tvName;

    @View(R.id.tvLink)
    private TextView tvLink;

    private final GlideLoader mGlideLoader;
    private final Context mContext;
    private final PlainAction onItemClick;
    private final WeakReference<CustomDrawerView> mDrawerViewReference;

    DrawerHeader(Context context, GlideLoader avatarLoader, CustomDrawerView drawerView, PlainAction onItemClick) {
        mGlideLoader = avatarLoader;
        mContext = context;
        this.onItemClick = onItemClick;
        mDrawerViewReference = new WeakReference<CustomDrawerView>(drawerView);
    }

    @Resolve
    private void onResolved() {
        User user = mDrawerViewReference.get().getUser();
        if (user != null) {
            tvName.setText(user.getDisplayName());
            tvLink.setText(user.getHtmlUrl());
            mGlideLoader.loadImage(user.getAvatarUrl(), imvAvatar);
        } else {
            tvName.setText(R.string.alert_login);
            tvLink.setText("");
            imvAvatar.setImageResource(R.drawable.ic_account_circle_black_48dp);
        }
    }

    @Click(R.id.rootLayout)
    void onClick() {
        onItemClick.run();
    }
}