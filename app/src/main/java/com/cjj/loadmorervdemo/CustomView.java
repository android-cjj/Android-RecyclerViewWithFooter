package com.cjj.loadmorervdemo;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by cjj on 2016/1/30.
 */
public class CustomView extends RelativeLayout implements FootItem {

    private ProgressBar mProgressBar;
    private TextView mEndTextView;
    private Context mContext;

    public CustomView(Context context) {
        super(context);
    }

    @Override
    public View onCreateView(View parent) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_footer_loading, this);
        mProgressBar = (ProgressBar) this.findViewById(R.id.load_progress);
        mEndTextView = (TextView) this.findViewById(R.id.tv_end);
        return view;
    }

    @Override
    public void onBindData(View view, int state) {
        if (state == LoadMoreRecycleView.STATE_LOADING) {
            showProgressBar();
        } else if (state == LoadMoreRecycleView.STATE_END) {
            showEnd("ccccccccccccccjjjjjjjjjjjjjjjjjjjj");
        }
    }


    public void showProgressBar() {
        mEndTextView.setVisibility(GONE);
        mProgressBar.setVisibility(VISIBLE);
    }

    public void showEnd(String end) {
        mEndTextView.setVisibility(VISIBLE);
        mProgressBar.setVisibility(GONE);
        if (!TextUtils.isEmpty(end)) {
            mEndTextView.setText(end);
        }
    }



}
