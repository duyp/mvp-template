package com.duyp.architecture.mvp.dagger.component;

import com.duyp.architecture.mvp.dagger.module.UserModule;
import com.duyp.architecture.mvp.dagger.scopes.UserScope;

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

//    // data
//    UserService getUserService();
//    UserFirebaseManager getUserFirebaseManager();
//
//    // subComponents
//    UserFragmentComponent getUserFragmentComponent(FragmentModule module);
//    UserActivityComponent getUserActivityComponent(ActivityModule module);
//    CustomViewComponent getCustomViewComponent(CustomViewModule module);
//
//    // inject
//    void inject(CustomClassBottomBarView view);
//    void inject(BecomeTutorDialog dialog);
//    void inject(JoinClassNoteDialog dialog);
//    void inject(CustomUnRateAnswerListView view);
}
