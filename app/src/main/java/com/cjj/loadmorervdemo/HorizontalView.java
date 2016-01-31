package com.cjj.loadmorervdemo;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by cjj on 2016/1/30.
 */
public class HorizontalView  implements FootItem {

    private ProgressBar mProgressBar;
    private TextView mEndTextView;
    private Context mContext;



    @Override
    public View onCreateView(View parent) {

        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.horizontal_view,null);

        return view;
    }

    @Override
    public void onBindData(View view, int state) {
        if (state == RecyclerViewWithFooter.STATE_LOADING) {
//            showProgressBar();
        } else if (state == RecyclerViewWithFooter.STATE_END) {
//            showEnd("ccccccccccccccjjjjjjjjjjjjjjjjjjjj");
        }
    }


    public void showProgressBar() {
        mEndTextView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void showEnd(String end) {
        mEndTextView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(end)) {
            mEndTextView.setText(end);
        }
    }



}
