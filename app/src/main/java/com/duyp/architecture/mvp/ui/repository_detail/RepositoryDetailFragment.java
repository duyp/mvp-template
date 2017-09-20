package com.duyp.architecture.mvp.ui.repository_detail;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.duyp.androidutils.image.glide.loader.SimpleGlideLoader;
import com.duyp.androidutils.image.glide.loader.TransitionGlideLoader;
import com.duyp.architecture.mvp.R;
import com.duyp.architecture.mvp.base.fragment.BasePresenterFragment;
import com.duyp.architecture.mvp.app.Constants;
import com.duyp.architecture.mvp.data.model.Repository;
import com.duyp.architecture.mvp.ui.customviews.CustomIconTabLayout;
import com.duyp.architecture.mvp.ui.customviews.CustomTabTitleView;
import com.duyp.architecture.mvp.utils.NavigatorHelper;

import javax.inject.Inject;

import butterknife.BindView;

import static com.duyp.architecture.mvp.ui.repository_detail.RepoTabAdapter.ICONS;
import static com.duyp.architecture.mvp.ui.repository_detail.RepoTabAdapter.TITLES;

/**
 * Created by duypham on 9/17/17.
 *
 */

public class RepositoryDetailFragment extends BasePresenterFragment<RepositoryDetailView, RepositoryDetailPresenter>
        implements RepositoryDetailView {

    @BindView(R.id.imvBackground)
    ImageView imvBackground;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvDes)
    TextView tvDescription;
    @BindView(R.id.appBar)
    AppBarLayout appBar;
    @BindView(R.id.tab)
    CustomIconTabLayout tab;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.tvStarCount)
    TextView tvStarCount;
    @BindView(R.id.tvWatchCount)
    TextView tvWatchCount;
    @BindView(R.id.tvForkCount)
    TextView tvForkCount;

    @Inject
    RepoTabAdapter adapter;

    @Inject
    TransitionGlideLoader glideLoader;

    @Inject
    NavigatorHelper navigatorHelper;

    @Override
    protected int getLayout() {
        return R.layout.fragment_repo_detail;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentComponent().inject(this);
    }

    @Override
    protected void initialize(View view) {
        super.initialize(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imvBackground.setTransitionName(getString(R.string.transition_name_avatar));
        }
        imvBackground.setOnClickListener(v -> {
            if (getPresenter().getData() != null) {
                navigatorHelper.navigateUserProfileActivity(getPresenter().getData().getOwner().partialClone());
            }
        });

        Bundle bundle = getArguments();
        if (bundle == null) {
            bundle = getActivity().getIntent().getExtras();
        }
        if (bundle == null) {
            throw new IllegalArgumentException("Must pass repository to this fragment or container activity");
        }
        final Long repoId = bundle.getLong(Constants.EXTRA_DATA);

        adapter.setRepoId(repoId);
        viewPager.setAdapter(adapter);
        tab.setupWithViewPager(viewPager);

        getPresenter().initRepo(repoId);
        getPresenter().fetchData();
    }

    @Override
    public void populateData(Repository repository) {
        tvTitle.setText(repository.getFullName());
        tvDescription.setText(repository.getDescription());
        glideLoader.loadImage(repository.getOwner().getAvatarUrl(), imvBackground);
        tvStarCount.setText(String.valueOf(repository.getStargazersCount()));
        tvWatchCount.setText(String.valueOf(repository.getWatchersCount()));
        tvForkCount.setText(String.valueOf(repository.getForksCount()));
        tab.setTitleAt(1, getString(R.string.issues_format, repository.getOpenIssuesCount()));
    }
}
