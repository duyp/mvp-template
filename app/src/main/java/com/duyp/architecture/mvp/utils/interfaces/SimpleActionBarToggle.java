package com.duyp.architecture.mvp.utils.interfaces;

import android.app.Activity;
import android.support.annotation.StringRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import com.duyp.architecture.mvp.R;

/**
 * Created by duypham on 9/12/17.
 */

public class SimpleActionBarToggle extends ActionBarDrawerToggle {

    public SimpleActionBarToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar) {
        super(activity, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
    }
}
