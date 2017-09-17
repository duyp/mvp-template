package com.duyp.architecture.mvp.ui.repository_detail.issues;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.duyp.architecture.mvp.R;
import com.duyp.architecture.mvp.dagger.qualifier.ActivityContext;
import com.duyp.architecture.mvp.data.model.Issue;
import com.duyp.architecture.mvp.utils.BaseRecyclerViewAdapter;
import com.duyp.architecture.mvp.utils.BaseViewHolder;

import javax.inject.Inject;

/**
 * Created by duypham on 9/17/17.
 */

public class IssuesAdapter extends BaseRecyclerViewAdapter<Issue> {

    @Inject
    public IssuesAdapter(@ActivityContext @NonNull Context context) {
        super(context);
    }

    @Override
    protected void bindHolder(RecyclerView.ViewHolder viewHolder, @NonNull Issue item) {

    }

    @Override
    protected RecyclerView.ViewHolder createHolder(ViewGroup viewGroup, int i) {
        return new IssueViewHolder(mInflater.inflate(R.layout.view_item_repository, viewGroup, false));
    }

    class IssueViewHolder extends BaseViewHolder {

        public IssueViewHolder(View itemView) {
            super(itemView);
        }
    }
}
