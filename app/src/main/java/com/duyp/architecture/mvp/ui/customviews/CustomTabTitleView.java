package com.duyp.architecture.mvp.ui.customviews;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;
import android.widget.TextView;

import com.duyp.architecture.mvp.R;

import butterknife.BindView;
import lombok.Getter;

/**
 * Created by duypham on 9/20/17.
 *
 */

public class CustomTabTitleView extends BaseRelativeLayout {

    @BindView(R.id.imv)
    ImageView imv;
    @Getter
    @BindView(R.id.tvTitle)
    TextView tvTitle;

    public CustomTabTitleView(Context context) {
        super(context);
    }

    @Override
    protected int getLayout() {
        return R.layout.view_tab_item;
    }

    public void setImageResource(@DrawableRes int res) {
        imv.setImageResource(res);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }
}
