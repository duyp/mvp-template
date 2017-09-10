package com.duyp.architecture.mvp.base.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duyp.androidutils.AlertUtils;
import com.duyp.androidutils.CommonUtils;
import com.duyp.androidutils.functions.PlainAction;
import com.duyp.androidutils.functions.PlainConsumer;
import com.duyp.architecture.mvp.app.MyApplication;
import com.duyp.architecture.mvp.base.BaseActivity;
import com.duyp.architecture.mvp.base.BaseView;
import com.duyp.architecture.mvp.base.interfaces.UiRefreshable;
import com.duyp.architecture.mvp.dagger.InjectionHelper;
import com.duyp.architecture.mvp.dagger.component.DaggerFragmentComponent;
import com.duyp.architecture.mvp.dagger.component.FragmentComponent;
import com.duyp.architecture.mvp.dagger.module.FragmentModule;
import com.duyp.architecture.mvp.data.Constants;
import com.squareup.leakcanary.RefWatcher;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by phamd on 5/25/2017.
 * Base Fragment
 */

public abstract class BaseFragment extends Fragment implements BaseView {

    @Getter
    @Setter
    public String TAG = "";

    private Unbinder unbinder;

    private FragmentComponent mFragmentComponent;

    protected RefWatcher refWatcher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        refWatcher = MyApplication.get(this).getRefWatcher();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @NonNull
    protected abstract Integer getLayout();

    public FragmentComponent fragmentComponent() {
        if (mFragmentComponent == null) {
            mFragmentComponent = DaggerFragmentComponent.builder()
                    .fragmentModule(new FragmentModule(this))
                    .appComponent(InjectionHelper.getAppComponent(this))
                    .build();
        }
        return mFragmentComponent;
    }

    @Override
    @CallSuper
    public void onDestroy() {
        super.onDestroy();
        if (refWatcher != null) {
            if (mFragmentComponent != null) {
                refWatcher.watch(mFragmentComponent);
            }
            refWatcher.watch(this);
        }
        unbinder.unbind();
        mFragmentComponent = null;
    }

    private ProgressDialog progress_dialog;
    private boolean shouldShowProgressDialog;

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

    public boolean isProgressShowing() {
        if (progress_dialog != null) {
            return progress_dialog.isShowing();
        }
        return false;
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

    protected void handleError(String message) {
        AlertUtils.showSnackBarLongMessage(getView(), message);
    }

    @Override
    @CallSuper
    public void onError(int code, String message) {
        handleError(message);
        hideProgress();
    }

    @Override
    @CallSuper
    public void showProgress() {
        showProgressDialog(Constants.PROGRESS_DIALOG_DELAY, null);
    }

    @Override
    public void showProgress(String message) {
        showProgressDialog(Constants.PROGRESS_DIALOG_DELAY, message);
    }

    @Override
    @CallSuper
    public void hideProgress() {
        hideProgressDialog();
        if (this instanceof UiRefreshable) {
            ((UiRefreshable)this).doneRefresh();
        }
    }

    void showDialog(DialogFragment dialog) {
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // show the dialog.
        dialog.show(ft, "dialog");
    }

    public void showToastLongMessage(String message) {
        AlertUtils.showToastLongMessage(getContext(), message);
    }

    public void showToastShortMessage(String message){
        AlertUtils.showToastShortMessage(getContext(), message);
    }

    @Override
    public void onStart() {
        super.onStart();
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
}
