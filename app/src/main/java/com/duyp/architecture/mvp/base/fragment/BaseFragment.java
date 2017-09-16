package com.duyp.architecture.mvp.base.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.LifecycleFragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duyp.androidutils.AlertUtils;
import com.duyp.androidutils.CommonUtils;
import com.duyp.androidutils.functions.PlainAction;
import com.duyp.androidutils.functions.PlainConsumer;
import com.duyp.architecture.mvp.app.MyApplication;
import com.duyp.architecture.mvp.base.BaseView;
import com.duyp.architecture.mvp.base.activity.BaseActivity;
import com.duyp.architecture.mvp.base.interfaces.UiRefreshable;
import com.duyp.architecture.mvp.dagger.InjectionHelper;
import com.duyp.architecture.mvp.dagger.component.DaggerFragmentComponent;
import com.duyp.architecture.mvp.dagger.component.FragmentComponent;
import com.duyp.architecture.mvp.dagger.component.UserFragmentComponent;
import com.duyp.architecture.mvp.dagger.module.FragmentModule;
import com.duyp.architecture.mvp.data.Constants;
import com.duyp.architecture.mvp.data.local.user.UserManager;
import com.duyp.architecture.mvp.data.model.base.ErrorEntity;
import com.duyp.architecture.mvp.ui.listeners.AccountListener;
import com.squareup.leakcanary.RefWatcher;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by phamd on 5/25/2017.
 * Base Fragment
 */

public abstract class BaseFragment extends LifecycleFragment implements BaseView {

    @Nullable
    private FragmentComponent mFragmentComponent;

    @Nullable UserFragmentComponent mUserFragmentComponent;

    private Unbinder unbinder;
    protected RefWatcher refWatcher;
    private ProgressDialog progress_dialog;
    private boolean shouldShowProgressDialog;

    @Nullable
    private AccountListener mAccountListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AccountListener) {
            mAccountListener = (AccountListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mAccountListener = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        refWatcher = MyApplication.get(this).getRefWatcher();
    }

    protected abstract int getLayout();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
    }

    protected abstract void initialize(View view);

    public FragmentComponent fragmentComponent() {
        if (mFragmentComponent == null) {
            mFragmentComponent = DaggerFragmentComponent.builder()
                    .fragmentModule(new FragmentModule(this))
                    .appComponent(InjectionHelper.getAppComponent(this))
                    .build();
        }
        return mFragmentComponent;
    }

    //////// USER MANAGER ////////////////
    protected void ensureInUserScope(@NonNull PlainConsumer<UserFragmentComponent> componentConsumer) {
        ensureInUserScope(true, componentConsumer, null);
    }

    protected void ensureInUserScope(boolean forceLogin, @NonNull PlainConsumer<UserFragmentComponent> componentConsumer) {
        ensureInUserScope(forceLogin, componentConsumer, null);
    }

    protected void ensureInUserScope(@NonNull PlainConsumer<UserFragmentComponent> componentConsumer, @Nullable PlainAction onError) {
        ensureInUserScope(false, componentConsumer, onError);
    }

    /**
     * Ensure we are in user scope (has saved user)
     * @param forceLogin true if should force user to login in case of saved user is not exist
     * @param componentConsumer consume {@link UserFragmentComponent} for fragment injecting
     * @param onError will run if no user found
     */
    protected void ensureInUserScope(boolean forceLogin,
                                     @NonNull PlainConsumer<UserFragmentComponent> componentConsumer,
                                     @Nullable PlainAction onError) {
        UserManager userManager = InjectionHelper.getAppComponent(this).userManager();
        if (userManager.checkForSavedUserAndStartSessionIfHas()) {
            // noinspection ConstantConditions
            mUserFragmentComponent = userManager.getUserComponent().getUserFragmentComponent(new FragmentModule(this));
            componentConsumer.accept(mUserFragmentComponent);
        } else {
            if (forceLogin && mAccountListener != null) {
                mAccountListener.forceLogin();
            }
            if (onError != null) {
                onError.run();
            }
        }
    }
    ////// END USER MANAGER ///////////////////

    @Override
    @CallSuper
    public void onDestroy() {
        super.onDestroy();
        if (refWatcher != null) {
            if (mFragmentComponent != null) {
                refWatcher.watch(mFragmentComponent);
            }
            if (mUserFragmentComponent != null) {
                refWatcher.watch(mUserFragmentComponent);
            }
            refWatcher.watch(this);
        }
        unbinder.unbind();
        mFragmentComponent = null;
    }

    public void showProgressDialog(int delay, String message) {
        if (progress_dialog == null) {
            progress_dialog = new ProgressDialog(getActivity());
        }
        shouldShowProgressDialog = true;
        new android.os.Handler().postDelayed(() -> {
            if (shouldShowProgressDialog && !progress_dialog.isShowing()) {
                try {
                    progress_dialog.setMessage(message == null ? "Loading" : message);
                    progress_dialog.setCancelable(false);
                    progress_dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                    // Handle error for this bug: Unable to add window -- token android.os.BinderProxy@d25223d is not valid; is your activity running?
                    // happen when activity finish before dialog shown.
                }
            }
        }, delay);
    }

    public void hideProgressDialog() {
        shouldShowProgressDialog = false;
        if (progress_dialog != null && progress_dialog.isShowing()) {
            progress_dialog.dismiss();
        }
    }

    @Override
    @CallSuper
    public void onError(ErrorEntity errorEntity) {
        showToastLongMessage(errorEntity.getMessage());
    }

    @Override
    public void showProgress() {
        showProgress(null);
    }

    @Override
    public void showProgress(String message) {
        showProgressDialog(Constants.PROGRESS_DIALOG_DELAY, message);
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
        if (this instanceof UiRefreshable) {
            ((UiRefreshable)this).doneRefresh();
        }
    }

    @Override
    public void showMessage(String message) {
        showToastLongMessage(message);
    }

    @Override
    public void setProgress(boolean show) {
        if (show) {
            showProgress();
        } else {
            hideProgress();
        }
    }

    public void showToastLongMessage(String message) {
        AlertUtils.showToastLongMessage(getContext(), message);
    }

    public void showToastShortMessage(String message){
        AlertUtils.showToastShortMessage(getContext(), message);
    }

    public void requestPermission(@NonNull PlainConsumer<Boolean> onNext, String... permissions) {
        Activity activity = getActivity();
        if (activity instanceof BaseActivity) {
            ((BaseActivity) getActivity()).requestPermission(onNext, permissions);
        } else {
            throw new IllegalStateException("This fragment must be attached in BaseActivity");
        }
    }

    /**
     * Do nothing on fail to force request permission
     */
    public void requestPermission(@NonNull PlainAction onGranted, String... permissions) {
        requestPermission(onGranted, false, null, permissions);
    }

    /**
     * Request a list of permissions at runtime
     * If user denied and selected "don't ask again", user will be asked
     * for navigating to app setting to enable needed permissions
     *
     * @param onGranted action on permissions successfully granted
     *
     * @param isMandatory * TRUE:  the request will be shown again if user click deny
     *                    * FALSE: just show a toast that this feature can not be perform
     *                              without needed permissions
     *
     * @param onFailToForce action run after user deny and select "don't ask again", then click cancel on alert dialog
     *                      - if null, do nothing
     *
     * @param permissions permissions to request
     */
    public void requestPermission(@NonNull PlainAction onGranted, boolean isMandatory,
                                  @Nullable PlainAction onFailToForce, String... permissions) {
        ((BaseActivity)getActivity()).requestPermission(onGranted, isMandatory, onFailToForce, permissions);
    }

    public void finishFragment() {
        CommonUtils.hideSoftKeyboard(getActivity());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().finishAfterTransition();
        } else {
            getActivity().finish();
        }
    }

    /**
     * @return true if fragment should handle back press, false if not (activity will handle back press event)
     */
    public boolean onBackPressed() {
        return false;
    }

}
