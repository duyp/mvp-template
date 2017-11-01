package com.duyp.architecture.mvp.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.duyp.androidutils.rx.functions.PlainConsumer;
import com.duyp.architecture.mvp.R;
import com.duyp.architecture.mvp.dagger.qualifier.ApplicationContext;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import java.lang.ref.WeakReference;

@Layout(R.layout.view_drawer_item)
public class DrawerMenuItem {

    public static final int DRAWER_MENU_ITEM_PROFILE = 0;
    public static final int DRAWER_MENU_ITEM_MY_REPOSITORIES = 1;
    public static final int DRAWER_MENU_ITEM_ALL_REPOSITORIES = 2;
    public static final int DRAWER_MENU_ITEM_MESSAGE = 3;
    public static final int DRAWER_MENU_ITEM_NOTIFICATIONS = 4;
    public static final int DRAWER_MENU_ITEM_SETTINGS = 5;
    public static final int DRAWER_MENU_ITEM_TERMS = 6;
    public static final int DRAWER_MENU_ITEM_LOGOUT = 7;

    public static final String[] MENU_TITLES = new String[] {"Profile", "My Repositories", "All public repositories",
            "Messages", "Notifications", "Settings", "Terms", "Logout"};

    public static final int[] MENU_ICONS = new int[] {R.drawable.ic_github_small, R.drawable.ic_github_small, R.drawable.ic_github_small,
            R.drawable.ic_github_small, R.drawable.ic_github_small, R.drawable.ic_github_small, R.drawable.ic_github_small, R.drawable.ic_github_small};

    @View(R.id.tvItemName)
    private TextView tvItemName;

    @View(R.id.imvItemIcon)
    private ImageView imvItemIcon;

    @View(R.id.itemLayout)
    private LinearLayout itemLayout;

    private int mMenuPosition;

    private WeakReference<CustomDrawerView> mDrawerViewReference;

    private @ApplicationContext Context mContext;

    private final PlainConsumer<Integer> onItemClick;

    public DrawerMenuItem(@ApplicationContext Context context, CustomDrawerView drawerView,
                          int menuPosition,
                          @NonNull PlainConsumer<Integer> onItemClick) {
        mContext = context;
        mMenuPosition = menuPosition;
        mDrawerViewReference = new WeakReference<>(drawerView);
        this.onItemClick = onItemClick;
    }

    @Resolve
    private void onResolved() {
        tvItemName.setText(MENU_TITLES[mMenuPosition]);
        imvItemIcon.setImageResource(MENU_ICONS[mMenuPosition]);
        itemLayout.setBackgroundColor(mContext.getResources().getColor(
                mDrawerViewReference.get().getSelectedItem() == mMenuPosition ? R.color.white_gray : R.color.white));
    }

    @Click(R.id.itemLayout)
    private void onMenuItemClick(){
        onItemClick.accept(mMenuPosition);
    }
}