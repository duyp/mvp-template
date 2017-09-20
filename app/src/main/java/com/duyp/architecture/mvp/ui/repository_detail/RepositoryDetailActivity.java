package com.duyp.architecture.mvp.ui.repository_detail;

import com.duyp.architecture.mvp.base.activity.BaseSingleFragmentActivity;

/**
 * Created by duypham on 9/17/17.
 *
 */

public class RepositoryDetailActivity extends BaseSingleFragmentActivity<RepositoryDetailFragment> {

    @Override
    protected RepositoryDetailFragment createFragment() {
        return new RepositoryDetailFragment();
    }

    @Override
    protected void finishWithAnimation() {
        finish();
    }
}
