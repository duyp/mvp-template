//package com.duyp.architecture.mvp.base.fragment;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.view.View;
//
//import co.asktutor.app.api.UserService;
//import co.asktutor.app.dagger.InjectionHelper;
//import co.asktutor.app.dagger.component.UserComponent;
//import co.asktutor.app.dagger.component.UserFragmentComponent;
//import co.asktutor.app.dagger.module.FragmentModule;
//import co.asktutor.app.user.UserManager;
//import lombok.Getter;
//
///**
// * Created by Duy Pham on 5/25/2017.
// * Base Fragment with presenter and component in user scope
// */
//
//public abstract class BaseUserFragment extends BaseFragment {
//
//    @Nullable
//    UserComponent mUserComponent;
//
//    private boolean isUserSessionStarted;
//
//    @Getter
//    UserFragmentComponent userFragmentComponent;
//
//    // must be override to use presenter
//    protected abstract void inject(UserFragmentComponent component);
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setupUserComponent();
//    }
//
//    // info - Application process was killed by the system
//    // info - must call in onCreate
//    protected void setupUserComponent() {
//        UserManager dataManager = InjectionHelper.getAppComponent(this).userManager();
//        isUserSessionStarted = dataManager.checkForSavedUserAndStartSessionIfHas();
//        mUserComponent = dataManager.getUserComponent();
//        if (mUserComponent != null) {
//            onUserComponentSetup(mUserComponent);
//        }
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        if (isUserSessionStarted() || ignoreOnStartUserSessionError()) {
//            initialize();
//        } else {
//            getActivity().finish();
//        }
//
//    }
//
//    /**
//     * Views and data should be initialized here (after views and presenter are created)
//     */
//    protected abstract void initialize();
//
//    /**
//     *
//     * @return  false if should force activity to finish,
//     *          true if should continuing without user component
//     */
//    protected boolean ignoreOnStartUserSessionError() {
//        return false;
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
////        if (refWatcher != null && userFragmentComponent != null) {
////            refWatcher.watch(userFragmentComponent);
////        }
//        userFragmentComponent = null;
//    }
//
//    // This check has to be called. Otherwise, when user is not logged in, presenter won't be injected and this line will cause NPE
//    public boolean isUserSessionStarted() {
//        return isUserSessionStarted;
//    }
//
//    final protected void onUserComponentSetup(UserComponent userComponent) {
//        userFragmentComponent = userComponent.getUserFragmentComponent(new FragmentModule(this));
//        inject(userFragmentComponent);
//    }
//
//    public UserService getUserService() {
//        if (mUserComponent != null) {
//            return mUserComponent.getUserService();
//        } else {
//            throw new IllegalStateException("User session has not been started yet!");
//        }
//    }
//}
