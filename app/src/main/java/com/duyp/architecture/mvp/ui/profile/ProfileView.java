package com.duyp.architecture.mvp.ui.profile;

import android.support.annotation.Nullable;

import com.duyp.architecture.mvp.base.BaseView;
import com.duyp.architecture.mvp.data.model.User;

/**
 * Created by duypham on 9/12/17.
 *
 */

public interface ProfileView extends BaseView{
    void onUserUpdated(@Nullable User user);
}
