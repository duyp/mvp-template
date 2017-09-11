package com.duyp.architecture.mvp.dagger.component;

import android.arch.lifecycle.LiveData;

import com.duyp.architecture.mvp.dagger.module.ActivityModule;
import com.duyp.architecture.mvp.dagger.module.CustomViewModule;
import com.duyp.architecture.mvp.dagger.module.FragmentModule;
import com.duyp.architecture.mvp.dagger.module.UserModule;
import com.duyp.architecture.mvp.dagger.scopes.UserScope;
import com.duyp.architecture.mvp.data.model.User;
import com.duyp.architecture.mvp.data.remote.UserService;

import dagger.Subcomponent;

/**
 * Created by air on 5/1/17.
 * Component for user manager
 */

@UserScope
@Subcomponent(
        modules = UserModule.class
)
public interface UserComponent {

    UserService getUserService();

    LiveData<User> getUserLiveData();

    // subComponents
    UserFragmentComponent getUserFragmentComponent(FragmentModule module);
    UserActivityComponent getUserActivityComponent(ActivityModule module);
    CustomViewComponent getCustomViewComponent(CustomViewModule module);

//    // inject
//    void inject(CustomClassBottomBarView view);
//    void inject(BecomeTutorDialog dialog);
//    void inject(JoinClassNoteDialog dialog);
//    void inject(CustomUnRateAnswerListView view);
}
