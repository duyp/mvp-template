package com.duyp.architecture.mvp.ui.repositories;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.duyp.architecture.mvp.R;
import com.duyp.architecture.mvp.base.fragment.BaseSwipeRecyclerViewFragment;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by phamd on 9/14/2017.
 *
 */

public class RepositoriesFragment extends BaseSwipeRecyclerViewFragment<RepositoryAdapter, RepositoryView, RepositoryPresenter>
        implements RepositoryView, AdapterView.OnItemSelectedListener {

    @BindView(R.id.spinnerFilter)
    AppCompatSpinner spinnerFilter;
    @BindView(R.id.tvSearch)
    EditText edtSearch;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_repositories;
    }

    @Override
    protected void initialize(View view) {
        super.initialize(view);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.repo_filter, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilter.setAdapter(adapter);
        spinnerFilter.setOnItemSelectedListener(this);
        RxTextView.textChanges(edtSearch).debounce(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(charSequence -> {
                    getPresenter().getOptions().setValue(new Pair<>(spinnerFilter.getSelectedItemPosition(), charSequence.toString()));
                });
    }

    @NonNull
    @Override
    protected RepositoryAdapter createAdapter() {
        return getPresenter().getAdapter();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        edtSearch.setHint(getString(R.string.search_by_format, adapterView.getSelectedItem().toString()));
        String text = edtSearch.getText().toString();
        if (!text.isEmpty()) {
            getPresenter().getOptions().setValue(new Pair<>(position, text));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
