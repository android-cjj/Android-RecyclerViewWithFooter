package com.cjj.loadmorervdemo;

import android.view.View;

/**
 * Created by cjj on 2016/1/30.
 */
public interface FootItem {

    public View onCreateView(View parent);

    public void onBindData(View view,int state);

}
