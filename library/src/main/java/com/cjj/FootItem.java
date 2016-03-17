package com.cjj;

import android.view.View;
import android.view.ViewGroup;

/**
 * Footer item.
 *
 * @author cjj on 2016/1/30.
 */
public abstract class FootItem {

    public CharSequence loadText;
    public CharSequence endText;

    public abstract View onCreateView(ViewGroup parent);

    public abstract void onBindData(View view, int state);

}
