package com.duyp.architecture.mvp.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.duyp.androidutils.functions.PlainConsumer;
import com.duyp.architecture.mvp.R;
import com.duyp.architecture.mvp.dagger.qualifier.ApplicationContext;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import java.lang.ref.WeakReference;

import static com.duyp.architecture.mvp.ui.main.MainActivity.DRAWER_MENU_ITEM_GROUPS;
import static com.duyp.architecture.mvp.ui.main.MainActivity.DRAWER_MENU_ITEM_LOGOUT;
import static com.duyp.architecture.mvp.ui.main.MainActivity.DRAWER_MENU_ITEM_MESSAGE;
import static com.duyp.architecture.mvp.ui.main.MainActivity.DRAWER_MENU_ITEM_NOTIFICATIONS;
import static com.duyp.architecture.mvp.ui.main.MainActivity.DRAWER_MENU_ITEM_PROFILE;
import static com.duyp.architecture.mvp.ui.main.MainActivity.DRAWER_MENU_ITEM_REQUESTS;
import static com.duyp.architecture.mvp.ui.main.MainActivity.DRAWER_MENU_ITEM_SETTINGS;
import static com.duyp.architecture.mvp.ui.main.MainActivity.DRAWER_MENU_ITEM_TERMS;


@Layout(R.layout.view_drawer_item)
public class DrawerMenuItem {

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

    public DrawerMenuItem(@ApplicationContext Context context, CustomDrawerView drawerView, int menuPosition, @NonNull PlainConsumer<Integer> onItemClick) {
        mContext = context;
        mMenuPosition = menuPosition;
        mDrawerViewReference = new WeakReference<>(drawerView);
        this.onItemClick = onItemClick;
    }

    @Resolve
    private void onResolved() {
        switch (mMenuPosition){
            case DRAWER_MENU_ITEM_PROFILE:
                tvItemName.setText("Profile");
                imvItemIcon.setImageResource(R.drawable.ic_github_small);
                break;
            case DRAWER_MENU_ITEM_REQUESTS:
                tvItemName.setText("Requests");
                imvItemIcon.setImageResource(R.drawable.ic_github_small);
                break;
            case DRAWER_MENU_ITEM_GROUPS:
                tvItemName.setText("Groups");
                imvItemIcon.setImageResource(R.drawable.ic_github_small);
                break;
            case DRAWER_MENU_ITEM_MESSAGE:
                tvItemName.setText("Messages");
                imvItemIcon.setImageResource(R.drawable.ic_github_small);
                break;
            case DRAWER_MENU_ITEM_NOTIFICATIONS:
                tvItemName.setText("Notifications");
                imvItemIcon.setImageResource(R.drawable.ic_github_small);
                break;
            case DRAWER_MENU_ITEM_SETTINGS:
                tvItemName.setText("Settings");
                imvItemIcon.setImageResource(R.drawable.ic_github_small);
                break;
            case DRAWER_MENU_ITEM_TERMS:
                imvItemIcon.setImageResource(R.drawable.ic_github_small);
                tvItemName.setText("Terms");
                break;
            case DRAWER_MENU_ITEM_LOGOUT:
                imvItemIcon.setImageResource(R.drawable.ic_github_small);
                tvItemName.setText("Logout");
                break;
        }
        itemLayout.setBackgroundColor(mContext.getResources().getColor(
                mDrawerViewReference.get().getSelectedItem() == mMenuPosition ? R.color.white_gray : R.color.white));
    }

    @Click(R.id.itemLayout)
    private void onMenuItemClick(){
        onItemClick.accept(mMenuPosition);
    }
}