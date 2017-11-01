package com.duyp.architecture.mvp.ui.profile;

import com.duyp.architecture.mvp.base.activity.BaseSingleFragmentActivity;

/**
 * Created by duypham on 9/20/17.
 */

public class ProfileActivity extends BaseSingleFragmentActivity<ProfileFragment> {

    @Override
    protected ProfileFragment createFragment() {
        return new ProfileFragment();
    }
}
