package com.cjj.loadmorervdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.loadmore.FootItem;
import com.cjj.loadmore.RecyclerViewWithFooter;

/**
 * Created by cjj on 2016/2/1.
 */
public class CustomFootItem extends FootItem {

    @Override
    public View onCreateView(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.foot_view, parent, false);
    }

    @Override
    public void onBindData(View view, int state) {
        /**
         * state 有 RecyclerViewWithFooter 定义的 STATE_END, STATE_LOADING, STATE_EMPTY, STATE_NONE
         */
        if (state == RecyclerViewWithFooter.STATE_LOADING) {

        } else if (state == RecyclerViewWithFooter.STATE_END) {

        }
    }
}
