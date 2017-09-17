package com.duyp.architecture.mvp.ui.repository_detail;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.duyp.androidutils.adapter.BaseFragmentStatePagerAdapter;
import com.duyp.androidutils.image.glide.loader.SimpleGlideLoader;
import com.duyp.architecture.mvp.R;
import com.duyp.architecture.mvp.base.fragment.BaseFragment;
import com.duyp.architecture.mvp.base.fragment.BasePresenterFragment;
import com.duyp.architecture.mvp.data.Constants;
import com.duyp.architecture.mvp.data.model.Repository;
import com.duyp.architecture.mvp.ui.repositories.RepositoryPresenter;
import com.duyp.architecture.mvp.ui.repositories.RepositoryView;
import com.duyp.architecture.mvp.utils.NavigatorHelper;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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
    @BindView(R.id.btnStar)
    LinearLayout btnStar;
    @BindView(R.id.btnWatch)
    LinearLayout btnWatch;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.appBar)
    AppBarLayout appBar;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

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
        Bundle bundle = getArguments();
        if (bundle == null) {
            bundle = getActivity().getIntent().getExtras();
        }
        if (bundle == null) {
            throw new IllegalArgumentException("Must pass repository to this fragment or container activity");
        }

        Repository repository = Parcels.unwrap(bundle.getParcelable(Constants.EXTRA_DATA));
        adapter.setRepository(repository);
        viewPager.setAdapter(adapter);
        tab.setupWithViewPager(viewPager);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imvBackground.setTransitionName(getString(R.string.transition_name_avatar));
        }

        populateData(repository);
    }

    @Override
    public void populateData(Repository repository) {
        tvTitle.setText(repository.getFullName());
        glideLoader.loadImage(repository.getOwner().getAvatarUrl(), imvBackground);
    }
}
