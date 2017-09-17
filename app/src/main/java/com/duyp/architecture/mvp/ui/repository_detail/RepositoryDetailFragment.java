package com.duyp.architecture.mvp.ui.repository_detail;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.duyp.androidutils.image.glide.loader.SimpleGlideLoader;
import com.duyp.architecture.mvp.R;
import com.duyp.architecture.mvp.base.fragment.BasePresenterFragment;
import com.duyp.architecture.mvp.data.Constants;
import com.duyp.architecture.mvp.data.model.Repository;
import com.duyp.architecture.mvp.utils.NavigatorHelper;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.duyp.architecture.mvp.ui.repository_detail.RepoTabAdapter.ICONS;
import static com.duyp.architecture.mvp.ui.repository_detail.RepoTabAdapter.TITLES;

/**
 * Created by duypham on 9/17/17.
 *
 */

public class RepositoryDetailFragment extends BasePresenterFragment<RepositoryDetailView, RepositoryDetailPresenter>
        implements RepositoryDetailView {

    public static RepositoryDetailFragment newInstance(@NonNull Repository repository) {
        return NavigatorHelper.createFragmentWithArguments(new RepositoryDetailFragment(), bundle -> {
            bundle.putParcelable(Constants.EXTRA_DATA, Parcels.wrap(repository));
        });
    }

    @BindView(R.id.imvBackground)
    ImageView imvBackground;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvDes)
    TextView tvDescription;
    @BindView(R.id.appBar)
    AppBarLayout appBar;
    @BindView(R.id.tab)
    TabLayout tab;
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
    SimpleGlideLoader glideLoader;

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

        Bundle bundle = getArguments();
        if (bundle == null) {
            bundle = getActivity().getIntent().getExtras();
        }
        if (bundle == null) {
            throw new IllegalArgumentException("Must pass repository to this fragment or container activity");
        }
        final Repository repository = Parcels.unwrap(bundle.getParcelable(Constants.EXTRA_DATA));

        populateData(repository);
        new android.os.Handler().postDelayed(() -> {
            adapter.setRepository(repository);
            initTabs();
            getPresenter().initRepo(repository);
        }, 1000);
    }

    private void initTabs() {
        viewPager.setAdapter(adapter);
        tab.setupWithViewPager(viewPager);
        for (int i = 0; i < tab.getTabCount(); i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.view_tab_item, null);
            ImageView imv = view.findViewById(R.id.imv);
            TextView tv = view.findViewById(R.id.tvTitle);
            imv.setImageResource(ICONS[i]);
            tv.setText(TITLES[i]);
            tab.getTabAt(i).setCustomView(view);
        }
    }

    @Override
    public void populateData(Repository repository) {
        tvTitle.setText(repository.getFullName());
        tvDescription.setText(repository.getDescription());
        glideLoader.loadImage(repository.getOwner().getAvatarUrl(), imvBackground);
        tvStarCount.setText(String.valueOf(repository.getStargazersCount()));
        tvWatchCount.setText(String.valueOf(repository.getWatchersCount()));
        tvForkCount.setText(String.valueOf(repository.getForksCount()));
    }
}
