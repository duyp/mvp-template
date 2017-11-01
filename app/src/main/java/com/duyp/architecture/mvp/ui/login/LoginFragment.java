package com.duyp.architecture.mvp.ui.login;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Fade;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.duyp.androidutils.CommonUtils;
import com.duyp.androidutils.StringUtils;
import com.duyp.androidutils.navigator.FragmentNavigator;
import com.duyp.architecture.mvp.R;
import com.duyp.architecture.mvp.base.fragment.BasePresenterFragment;
import com.duyp.architecture.mvp.data.model.User;
import com.duyp.architecture.mvp.ui.main.MainView;
import com.duyp.architecture.mvp.ui.profile.ProfileFragment;
import com.duyp.architecture.mvp.utils.AvatarLoader;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by duypham on 9/12/17.
 *
 */

public class LoginFragment extends BasePresenterFragment<LoginView, LoginPresenter> implements LoginView {

    @BindView(R.id.edtUserName)
    EditText edtUserName;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.imvAvatar)
    ImageView imvAvatar;
    @BindView(R.id.pb)
    ProgressBar pb;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    @Inject
    AvatarLoader avatarLoader;

    @Inject
    FragmentNavigator fragmentNavigator;

    private boolean isPreparingForTransition = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentComponent().inject(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.setExitTransition(new Fade());
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initialize(View view) {
        super.initialize(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imvAvatar.setTransitionName(getString(R.string.transition_name_avatar));
        }
    }

    @Override
    public void showProgress() {
        btnLogin.setVisibility(View.GONE);
        pb.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        if (!isPreparingForTransition) {
            btnLogin.setVisibility(View.VISIBLE);
            pb.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btnLogin)
    public void onViewClicked() {
        String userName = edtUserName.getText().toString();
        String passWord = edtPassword.getText().toString();
        if (StringUtils.isNotEmpty(userName, passWord)) {
            CommonUtils.hideSoftKeyboard(getActivity());
            getPresenter().loginUser(userName, passWord);
        } else {
            showToastLongMessage("Please enter username and password");
        }
    }

    @Override
    public void onLoginSuccess(User user) {
        isPreparingForTransition = true;
        avatarLoader.loadImage(user.getAvatarUrl(), imvAvatar);
        new android.os.Handler().postDelayed(() -> {
            fragmentNavigator.replaceFragment(R.id.container,  ProfileFragment.newInstance(null), imvAvatar);
            if (getActivity() instanceof MainView) {
                ((MainView) getActivity()).setTitle("Profile");
            }
        }, 800);
    }
}
