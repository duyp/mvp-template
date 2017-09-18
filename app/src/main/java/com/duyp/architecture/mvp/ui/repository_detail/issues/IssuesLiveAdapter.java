package com.duyp.architecture.mvp.ui.repository_detail.issues;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.duyp.architecture.mvp.R;
import com.duyp.architecture.mvp.base.adapter.BaseRealmLiveDataAdapter;
import com.duyp.architecture.mvp.dagger.qualifier.ActivityContext;
import com.duyp.architecture.mvp.data.model.Issue;
import com.duyp.architecture.mvp.data.model.Label;
import com.duyp.architecture.mvp.utils.BaseViewHolder;
import com.duyp.architecture.mvp.utils.RelativeTimeTextView;

import javax.inject.Inject;

import butterknife.BindDimen;
import butterknife.BindView;

/**
 * Created by duypham on 9/19/17.
 * Adapter for issues fragment
 */
class IssuesLiveAdapter extends BaseRealmLiveDataAdapter<Issue, IssuesLiveAdapter.IssueViewHolder>{

    private static final String TAG = "IssuesAdapter";

    @Inject
    public IssuesLiveAdapter(@ActivityContext Context context, @NonNull LifecycleOwner owner) {
        super(context, owner);
    }

    @Override
    public IssueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new IssueViewHolder(mInflater.inflate(R.layout.view_item_issue, parent, false));
    }

    @Override
    protected void bindHolder(IssueViewHolder viewHolder, @NonNull Issue item) {
        viewHolder.bindData(item);
    }

    class IssueViewHolder extends BaseViewHolder {

        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.labelContainer)
        LinearLayout labelContainer;
        @BindView(R.id.tvDes)
        TextView tvDes;
        @BindView(R.id.tvTime)
        RelativeTimeTextView tvTime;
        @BindView(R.id.tvCommentCount)
        TextView tvCommentCount;

        @BindDimen(R.dimen.base5)
        int defaultMargin;

        IssueViewHolder(View itemView) {
            super(itemView);
        }

        void bindData(@NonNull Issue issue) {
            tvTitle.setText(issue.getTitle());
            tvDes.setText(getContext().getString(R.string.issue_description_format, issue.getNumber(),
                    issue.getUser().getLogin()));
            tvTime.setReferenceTime(issue.getCreatedAt());
            tvCommentCount.setText(getContext().getString(R.string.issue_comments_format, issue.getComments()));

            labelContainer.removeAllViews();
            Log.d(TAG, "bindData: label count: " + issue.getLabels().size());
            labelContainer.setVisibility(issue.getLabels().isEmpty() ? View.GONE : View.VISIBLE);
            for (Label label : issue.getLabels()) {
                TextView textView = new TextView(getContext());
                textView.setText(label.getName());
                textView.setTextColor(Color.WHITE);
                textView.setBackgroundColor(Color.parseColor("#" + label.getColor()));
                textView.setPadding(defaultMargin, defaultMargin, defaultMargin, defaultMargin);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                );

                labelContainer.addView(textView, params);
            }
        }
    }
}
