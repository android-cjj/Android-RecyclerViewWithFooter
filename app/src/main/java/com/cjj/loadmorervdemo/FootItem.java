package com.cjj.loadmorervdemo;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by cjj on 2016/1/30.
 */
public interface FootItem {

    View onCreateView(ViewGroup parent);

    void onBindData(View view, int state, CharSequence endStr);

}
