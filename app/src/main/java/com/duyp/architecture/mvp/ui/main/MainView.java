package com.duyp.architecture.mvp.ui.main;

import com.duyp.architecture.mvp.base.BaseView;
import com.duyp.architecture.mvp.data.model.User;

/**
 * Created by duypham on 9/12/17.
 *
 */

public interface MainView extends BaseView {
    void onUserUpdated(User user);
}
