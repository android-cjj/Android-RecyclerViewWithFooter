package com.cjj;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by cjj on 2016/2/1.
 */
public class MaterialFootItem extends FootItem {
    private CircleProgressBar mCircleProgressBar;
    private TextView mEndTextView;

    @Override
    public View onCreateView(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_material, parent, false);
        mCircleProgressBar = (CircleProgressBar) view.findViewById(R.id.progress2);
        mCircleProgressBar.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_orange_light,android.R.color.holo_red_light);
        mEndTextView = (TextView) view.findViewById(R.id.tv_end);
        return view;
    }

    @Override
    public void onBindData(View view, int state) {
        if (state == RecyclerViewWithFooter.STATE_LOADING) {
            mCircleProgressBar.setVisibility(View.VISIBLE);
            mEndTextView.setVisibility(View.GONE);
        } else if (state == RecyclerViewWithFooter.STATE_END) {

            showEnd(endText);
        }
    }

    public void showEnd(CharSequence end) {
        mEndTextView.setVisibility(View.VISIBLE);
        mCircleProgressBar.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(end)) {
            mEndTextView.setText(end);
        }
    }
}
