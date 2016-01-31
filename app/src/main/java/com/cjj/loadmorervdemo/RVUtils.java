package com.cjj.loadmorervdemo;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

/**
 * @author cjj
 * 设置rv manager 类型
 */
public class RVUtils {

    public static void setVerticalLinearLayout(RecyclerView rv) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(rv.getContext(), LinearLayoutManager.VERTICAL, false){
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                try {
                    super.onLayoutChildren(recycler, state);
                } catch (IndexOutOfBoundsException e) {
                    Log.e("cjj", "meet a IOOBE in RecyclerView");
                }
            }
        };
        rv.setLayoutManager(layoutManager);
    }

    public static void setGridLayout(RecyclerView rv, int spanCount) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(rv.getContext(), spanCount, GridLayoutManager.VERTICAL, false){
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                try {
                    super.onLayoutChildren(recycler, state);
                } catch (IndexOutOfBoundsException e) {
                    Log.e("cjj", "meet a IOOBE in RecyclerView");
                }
            }
        };
        rv.setLayoutManager(gridLayoutManager);
    }

    /**
     * 添加到底部的监听.
     *
     * @param rv
     * @param onLoadMoreListener
     */
    public static void setOnLastItemVisibleListener(RecyclerView rv, final OnLoadMoreListener onLoadMoreListener) {
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

                    if (layoutManager instanceof LinearLayoutManager) {
                        int lastVisiblePosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();

                        if (lastVisiblePosition >= recyclerView.getAdapter().getItemCount() - 1) {
                            onLoadMoreListener.onLoadMore();
                        }
                    } else if (layoutManager instanceof StaggeredGridLayoutManager) {

                        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                        int last[] = new int[staggeredGridLayoutManager.getSpanCount()];
                        staggeredGridLayoutManager.findLastVisibleItemPositions(last);
                        for (int i = 0; i < last.length; i++) {
                            Log.e("cjj", last[i] + "    " + recyclerView.getAdapter().getItemCount());
                            if (last[i] >= recyclerView.getAdapter().getItemCount() - 1) {
                                onLoadMoreListener.onLoadMore();
                            }
                        }
                    }
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    public static void setStaggeredGridLayoutManager(RecyclerView rv, int spanCount) {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL){
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                try {
                    super.onLayoutChildren(recycler, state);
                } catch (IndexOutOfBoundsException e) {
                    Log.e("cjj", "meet a IOOBE in RecyclerView");
                }
            }
        };
        rv.setLayoutManager(staggeredGridLayoutManager);
    }
}
