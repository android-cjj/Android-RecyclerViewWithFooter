package com.cjj;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 默认的空数据视图
 *
 * @author cjj on 16/1/31.
 */
public class DefaultEmptyItem extends EmptyItem {

    private TextView mEmptyTV;

    @Override
    public View onCreateView(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_layout, parent, false);
        view.setLayoutParams(new ViewGroup.LayoutParams(parent.getMeasuredWidth(), parent.getMeasuredHeight()));
        mEmptyTV = (TextView) view.findViewById(R.id.tv_title);
        return view;
    }

    @Override
    public void onBindData(View view) {
        if (TextUtils.isEmpty(emptyText)) {
            mEmptyTV.setVisibility(View.GONE);
        } else {
            mEmptyTV.setVisibility(View.VISIBLE);
            mEmptyTV.setText(emptyText);
        }
    }
}
