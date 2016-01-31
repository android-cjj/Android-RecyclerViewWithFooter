package com.cjj.loadmorervdemo;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by sam on 15/6/2.
 */
public class EmptyView extends FrameLayout {

    private ImageView mIconIm;
    private TextView mEmptyTV;


    public EmptyView(Context context, ViewGroup parent) {
        super(context);
        setLayoutParams(new ViewGroup.LayoutParams(parent.getMeasuredWidth(),parent.getMeasuredHeight()));
        init();

    }

    private void init() {
        addView(LayoutInflater.from(getContext()).inflate(R.layout.empty_layout, this,false));
        mIconIm = (ImageView) findViewById(R.id.iv_image);
        mEmptyTV = (TextView) findViewById(R.id.tv_title);

    }

    public void setEmptyInfo(CharSequence emptyStr, int iconRes) {

        if(TextUtils.isEmpty(emptyStr)){
            mEmptyTV.setVisibility(GONE);
        }else {
            mEmptyTV.setVisibility(VISIBLE);
            mEmptyTV.setText(emptyStr);
        }

        if(iconRes <=0) {
            mIconIm.setVisibility(GONE);
        }else{
            mIconIm.setVisibility(VISIBLE);
            mIconIm.setImageResource(iconRes);
        }
    }
}
