package com.duyp.architecture.mvp.ui.main;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.duyp.architecture.mvp.data.model.User;
import com.mindorks.placeholderview.PlaceHolderView;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by duypham on 9/12/17.
 *
 */

@Getter
public class CustomDrawerView extends PlaceHolderView {

    @Setter
    private int selectedItem = 0;
    private User user;

    public CustomDrawerView(Context context) {
        super(context);
    }

    public CustomDrawerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomDrawerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void updateUser(@Nullable User user) {
        this.user = user;
        refresh();
    }
}
