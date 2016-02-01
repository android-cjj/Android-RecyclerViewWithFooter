package com.cjj;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by cjj on 16/1/31.
 */
public class DefaultEmptyItem extends EmptyItem {

    private ImageView mIconIm;
    private TextView mEmptyTV;


    @Override
    public View onCreateView(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_layout, parent, false);
        view.setLayoutParams(new ViewGroup.LayoutParams(parent.getMeasuredWidth(),parent.getMeasuredHeight()));
        mIconIm = (ImageView)view. findViewById(R.id.iv_image);
        mEmptyTV = (TextView) view.findViewById(R.id.tv_title);
        return view;
    }

    @Override
    public void onBindData(View view) {

        if(TextUtils.isEmpty(emptyText)){
            mEmptyTV.setVisibility(View.GONE);
        }else {
            mEmptyTV.setVisibility(View.VISIBLE);
            mEmptyTV.setText(emptyText);
        }

        if(emptyIcon <=0) {
            mIconIm.setVisibility(View.GONE);
        }else{
            mIconIm.setVisibility(View.VISIBLE);
            mIconIm.setImageResource(emptyIcon);
        }
    }
}
