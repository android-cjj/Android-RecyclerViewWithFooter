package com.cjj.loadmorervdemo;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FootView implements FootItem {

    private ProgressBar mProgressBar;
    private TextView mLoadingText;
    private TextView mEndTextView;


    @Override
    public View onCreateView(View parent) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_footer_loading, null);
        mProgressBar = (ProgressBar) view.findViewById(R.id.load_progress);
        mEndTextView = (TextView) view.findViewById(R.id.tv_end);
        mLoadingText = (TextView) view.findViewById(R.id.tv_load);
        return view;
    }

    @Override
    public void onBindData(View view, int state) {
        if (state == LoadMoreRecycleView.STATE_LOADING) {
            showProgressBar(view.getContext().getResources().getString(R.string.loading));
        } else if (state == LoadMoreRecycleView.STATE_END) {
            showEnd("");
        }
    }

    public void showProgressBar(String load) {
        mEndTextView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(load)) {
            mLoadingText.setVisibility(View.VISIBLE);
            mLoadingText.setText(load);
        } else {
            mLoadingText.setVisibility(View.GONE);
        }
    }

    public void showEnd(String end) {
        mEndTextView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mLoadingText.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(end)) {
            mEndTextView.setText(end);
        }
    }
}
