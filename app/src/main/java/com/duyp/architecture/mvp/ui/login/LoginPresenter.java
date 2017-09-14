package com.duyp.architecture.mvp.ui.login;

import android.content.Context;

import com.duyp.androidutils.StringUtils;
import com.duyp.architecture.mvp.base.presenter.BasePresenter;
import com.duyp.architecture.mvp.dagger.qualifier.ActivityContext;
import com.duyp.architecture.mvp.dagger.scopes.PerFragment;
import com.duyp.architecture.mvp.data.local.user.UserManager;

import javax.inject.Inject;

/**
 * Created by duypham on 9/12/17.
 * Presenter for login fragment
 */

class LoginPresenter extends BasePresenter<LoginView> {

    @Inject
    LoginPresenter(@ActivityContext Context context, UserManager userManager) {
        super(context, userManager);
    }

    /**
     * Login github user by Basic Auth (username and password)
     * @param userName username
     * @param password password
     */
    void loginUser(String userName, String password) {
        String basicAuth = StringUtils.getBasicAuth(userName, password);
        addRequest(getGithubService().login(basicAuth), true, response -> {
            getUserManager().startUserSession(response, basicAuth);
            getView().onLoginSuccess(response);
        });
    }
}
