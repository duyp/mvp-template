package com.duyp.architecture.mvp.ui.customviews;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.duyp.architecture.mvp.base.interfaces.Destroyable;
import com.duyp.architecture.mvp.dagger.InjectionHelper;
import com.duyp.architecture.mvp.dagger.component.CustomViewComponent;
import com.duyp.architecture.mvp.dagger.module.CustomViewModule;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by air on 3/27/17.
 */


public abstract class BaseRelativeLayout<T> extends RelativeLayout implements Destroyable {

    private T mData;

    Unbinder unbinder;

    CustomViewComponent customViewComponent;

    public BaseRelativeLayout(Context context) {
        super(context);
        initView(context);
    }

    public BaseRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CustomViewComponent getCustomViewComponent() {
        if (customViewComponent == null) {
            customViewComponent = InjectionHelper.getUserComponent(getContext())
                    .getCustomViewComponent(new CustomViewModule(this));
        }
        return customViewComponent;
    }

    protected void initView(Context context) {
        if (unbinder != null) {
            unbinder.unbind();
            this.removeAllViews();
        }
        View view = LayoutInflater.from(context).inflate(getLayout(), this);
        unbinder = ButterKnife.bind(this, view);
    }

    @CallSuper
    protected void bindData(T data) {
        mData = data;
    }

    @Nullable
    public T getData() {
        return mData;
    }

    protected abstract int getLayout();

    public String getString(@StringRes int res) {
        return getContext().getString(res);
    }

    @Override
    public void onDestroy() {
        customViewComponent = null;
    }
}
