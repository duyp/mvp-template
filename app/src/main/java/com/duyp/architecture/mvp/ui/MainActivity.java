package com.duyp.architecture.mvp.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.duyp.androidutils.AlertUtils;
import com.duyp.androidutils.CommonUtils;
import com.duyp.androidutils.StringUtils;
import com.duyp.androidutils.image.glide.GlideUtils;
import com.duyp.androidutils.image.glide.loader.SimpleGlideLoader;
import com.duyp.architecture.mvp.R;
import com.duyp.architecture.mvp.base.BaseActivity;
import com.duyp.architecture.mvp.data.local.user.UserManager;
import com.duyp.architecture.mvp.data.local.user.UserRepo;
import com.duyp.architecture.mvp.data.model.User;
import com.duyp.architecture.mvp.data.remote.GithubService;
import com.duyp.architecture.mvp.utils.api.ApiUtils;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    public static final int MAX_INDEX = 3;
    public static final String[] NAMES = new String[] {"Duy Pham", "Thanh Tran", "Dieu Anh", "Duy Khang"};
    public static final String[] AVATARS = new String[] {
            "https://camo.mybb.com/e01de90be6012adc1b1701dba899491a9348ae79/687474703a2f2f7777772e6a71756572797363726970742e6e65742f696d616765732f53696d706c6573742d526573706f6e736976652d6a51756572792d496d6167652d4c69676874626f782d506c7567696e2d73696d706c652d6c69676874626f782e6a7067",
            "https://www.codeproject.com/KB/GDI-plus/ImageProcessing2/flip.jpg",
            "http://www.diena.lt/sites/default/files/sites/default/files/test/1__85320a.jpg",
            "http://www.multyshades.com/wp-content/uploads/2012/06/baby-animal33.jpg"
    };

    @BindView(R.id.edtUserName)
    EditText edtUserName;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.pb)
    ProgressBar progressBar;
    @BindView(R.id.imvAvatar)
    ImageView imvAvatar;
    @BindView(R.id.tvName)
    TextView tvName;

    @Inject
    UserManager userManager;

    @Inject
    UserRepo userRepo;

    @Inject
    GithubService githubService;

    @Inject
    SimpleGlideLoader glideLoader;

    LiveData<User> mUserLiveData;

    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        glideLoader.setUseFixedSizeThumbnail(false);

        FloatingActionButton fab =  findViewById(R.id.fab);
        FloatingActionButton fabLogout =  findViewById(R.id.fabLogout);
        fab.setOnClickListener(view -> {
            if (mUserLiveData != null && mUserLiveData.getValue() != null) {
                User user = userRepo.cloneUser(mUserLiveData.getValue());
                user.setName(NAMES[index]);
                user.setAvatarUrl(AVATARS[index]);
                index ++;
                if (index > MAX_INDEX) index = 0;
                userRepo.updateUserIfEquals(user, null);
            }
        });

        fabLogout.setOnClickListener(view -> {
            userManager.logout();
        });


        imvAvatar.setOnClickListener(view -> {
            CommonUtils.hideSoftKeyboard(this);
            String userName = edtUserName.getText().toString();
            String pass = edtPassword.getText().toString();
            String basic = StringUtils.getBasicAuth(userName, pass);
            Log.d("auth", "basic auth: " + basic);

            ApiUtils.makeRequest(githubService.login(basic), true, disposable -> {
                progressBar.setVisibility(View.VISIBLE);
            }, () -> {
                progressBar.setVisibility(View.GONE);
            }, user -> {
                userManager.doAfterLogin(user, basic, userLiveData -> {
                    mUserLiveData = userLiveData;
                    populateUserData();
                });
            }, error -> {
                AlertUtils.showToastLongMessage(MainActivity.this, error.getMessage());
            });
        });
        init();
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    private void init() {
        if (!userManager.checkForSavedUserAndStartSessionIfHas(userLiveData -> {
            mUserLiveData = userLiveData;
            populateUserData();
        })) {
            AlertUtils.showToastLongMessage(this, "Please login!");
        }
    }

    private void populateUserData() {
        mUserLiveData.observe(this, user -> {
            if (user != null) {
                glideLoader.loadImage(user.getAvatarUrl(), imvAvatar);
                tvName.setText(user.getName());
            } else {
                GlideUtils.loadImageDrawableResource(this, R.drawable.ic_avatar_default, imvAvatar);
                tvName.setText("Please login");
            }
        });
    }
}
