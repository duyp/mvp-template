package com.duyp.architecture.mvp.ui.repository_detail;

import android.content.Context;

import com.duyp.architecture.mvp.base.presenter.BasePresenter;
import com.duyp.architecture.mvp.dagger.qualifier.ActivityContext;
import com.duyp.architecture.mvp.dagger.scopes.PerFragment;
import com.duyp.architecture.mvp.data.local.user.UserManager;
import com.duyp.architecture.mvp.ui.repositories.RepositoryView;

import javax.inject.Inject;

/**
 * Created by duypham on 9/17/17.
 */

@PerFragment
public class RepositoryDetailPresenter extends BasePresenter<RepositoryDetailView> {

    @Inject
    public RepositoryDetailPresenter(@ActivityContext Context context, UserManager userManager) {
        super(context, userManager);
    }
}
