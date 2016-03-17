package com.cjj;


import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author cjj
 */
public class RecyclerViewWithFooter extends RecyclerView {

    public static final int STATE_END = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_EMPTY = 2;
    public static final int STATE_NONE = 3;

    private boolean mIsGetDataForNet = false;
    private
    @State
    int mState = STATE_NONE;
    /**
     * 默认的 FootItem;
     */
    private FootItem mFootItem = new DefaultFootItem();
    private EmptyItem mEmptyItem = new DefaultEmptyItem();
    private AdapterDataObserver mAdapterDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            reset();
        }


        private void reset() {
            mIsGetDataForNet = false;
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            reset();

        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            super.onItemRangeChanged(positionStart, itemCount, payload);
            reset();

        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            reset();

        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            reset();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            reset();
        }
    };

    public RecyclerViewWithFooter(Context context) {
        super(context);
        init();
    }

    public RecyclerViewWithFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RecyclerViewWithFooter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setVerticalLinearLayout();
    }

    public void setVerticalLinearLayout() {
        RecyclerViewUtils.setVerticalLinearLayout(this);
    }

    public void setGridLayout(int span) {
        RecyclerViewUtils.setGridLayout(this, span);
    }

    public void setStaggeredGridLayoutManager(int spanCount) {
        RecyclerViewUtils.setStaggeredGridLayoutManager(this, spanCount);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mState = STATE_LOADING;
        RecyclerViewUtils.setOnLastItemVisibleListener(this, new OnLoadMoreListenerWrap(onLoadMoreListener));
    }

    @Override
    public void setAdapter(Adapter adapter) {
        LoadMoreAdapter loadMoreAdapter;
        if (adapter instanceof LoadMoreAdapter) {
            loadMoreAdapter = (LoadMoreAdapter) adapter;
            loadMoreAdapter.registerAdapterDataObserver(mAdapterDataObserver);
            super.setAdapter(adapter);
        } else {
            loadMoreAdapter = new LoadMoreAdapter(adapter);
            loadMoreAdapter.registerAdapterDataObserver(mAdapterDataObserver);
            super.setAdapter(loadMoreAdapter);
        }
    }

    /**
     * 配置
     *
     * @param loadText
     * @return
     */
    public RecyclerViewWithFooter applyLoadText(CharSequence loadText) {
        mFootItem.loadText = loadText;
        return this;
    }

    public RecyclerViewWithFooter applyLoadEndText(CharSequence endText) {
        mFootItem.endText = endText;
        return this;
    }

    public RecyclerViewWithFooter applyEmptyText(CharSequence emptyText, @DrawableRes int drawableId) {

        mEmptyItem.emptyIcon = drawableId;
        mEmptyItem.emptyText = emptyText;
        return this;
    }

    public void setFootItem(FootItem footItem) {
        if (mFootItem != null) {
            if (footItem.endText == null) {
                footItem.endText = mFootItem.endText;
            }
            if (footItem.loadText == null) {
                footItem.loadText = mFootItem.loadText;
            }
        }
        mFootItem = footItem;
    }

    public void setEmptyItem(EmptyItem emptyItem) {
        if (mEmptyItem != null) {
            if (emptyItem.emptyIcon == -1) {
                emptyItem.emptyIcon = mEmptyItem.emptyIcon;
            }
            if (emptyItem.emptyText == null) {
                emptyItem.emptyText = mEmptyItem.emptyText;
            }
        }
        mEmptyItem = emptyItem;
    }

    /**
     * 表示现在是切换成 load 状态
     */
    public void setLoad() {

        if (getAdapter() != null) {
            mState = STATE_LOADING;
            mIsGetDataForNet = false;
            this.getAdapter().notifyItemChanged(this.getAdapter().getItemCount() - 1);
        }
    }

    /**
     * 表示切换成没有更多数据状态
     *
     * @param end
     */
    public void setEnd(CharSequence end) {
        if (getAdapter() != null) {

            mIsGetDataForNet = false;
            mState = STATE_END;
            mFootItem.endText = end;
            this.getAdapter().notifyItemChanged(this.getAdapter().getItemCount() - 1);
        }
    }

    /**
     * 表示切换成没有更多数据状态
     */
    public void setEnd() {

        if (getAdapter() != null) {
            mIsGetDataForNet = false;
            mState = STATE_END;
            this.getAdapter().notifyItemChanged(this.getAdapter().getItemCount() - 1);
        }
    }

    /**
     * 表示切换成 无数据 为空状态
     *
     * @param empty
     * @param resId
     */
    public void setEmpty(CharSequence empty, @DrawableRes int resId) {

        if (getAdapter() != null) {
            mState = STATE_EMPTY;
            mEmptyItem.emptyText = empty;
            mEmptyItem.emptyIcon = resId;
            if (isEmpty()) {
                this.getAdapter().notifyDataSetChanged();
            }
        }
    }

    /**
     * 表示切换成 无数据 为空状态
     */
    public void setEmpty() {
        if (getAdapter() != null) {
            mState = STATE_EMPTY;
            if (isEmpty()) {
                this.getAdapter().notifyDataSetChanged();
            }
        }
    }

    /**
     * 是否数据为空
     *
     * @return
     */
    private boolean isEmpty() {

        return (mState == STATE_NONE && getAdapter().getItemCount() == 0) || (mState != STATE_NONE && getAdapter().getItemCount() == 1);
    }

    public boolean loadMoreEnable() {
        return mState != STATE_LOADING;
    }

    @IntDef({STATE_END, STATE_LOADING, STATE_EMPTY, STATE_NONE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    public class OnLoadMoreListenerWrap implements OnLoadMoreListener {

        OnLoadMoreListener mOnLoadMoreListener;

        public OnLoadMoreListenerWrap(OnLoadMoreListener onLoadMoreListener) {
            mOnLoadMoreListener = onLoadMoreListener;
        }

        @Override
        public void onLoadMore() {

            if (!mIsGetDataForNet && !loadMoreEnable()) {
                mIsGetDataForNet = true;
                mOnLoadMoreListener.onLoadMore();
            }
        }
    }

    public class LoadMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        public static final int LOAD_MORE_VIEW_TYPE = -404;
        public static final int EMPTY_VIEW_TYPE = -403;
        public RecyclerView.Adapter mAdapter;

        public LoadMoreAdapter(Adapter adapter) {
            mAdapter = adapter;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            if (viewType == LOAD_MORE_VIEW_TYPE) {

                return new LoadMoreVH();

            } else if (viewType == EMPTY_VIEW_TYPE) {

                return new EmptyVH();
            }
            return mAdapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void registerAdapterDataObserver(AdapterDataObserver observer) {
            super.registerAdapterDataObserver(observer);
            mAdapter.registerAdapterDataObserver(observer);

        }

        @Override
        public void unregisterAdapterDataObserver(AdapterDataObserver observer) {
            super.unregisterAdapterDataObserver(observer);
            mAdapter.unregisterAdapterDataObserver(observer);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (!isFootView(position)) {

                mAdapter.onBindViewHolder(holder, position);

            } else {
                if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
                    StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) (getLayoutManager()).generateDefaultLayoutParams();
                    layoutParams.setFullSpan(true);
                    holder.itemView.setLayoutParams(layoutParams);
                }
                if (holder instanceof VH) {
                    ((VH) holder).onBindViewHolder();
                }
            }
        }


        boolean isFootView(int position) {

            return position == getItemCount() - 1 && mState != STATE_NONE;
        }

        @Override
        public int getItemViewType(int position) {
            if (!isFootView(position)) {
                return mAdapter.getItemViewType(position);
            } else {
                if (mState == STATE_EMPTY && getItemCount() == 1) {
                    return EMPTY_VIEW_TYPE;
                } else {
                    return LOAD_MORE_VIEW_TYPE;
                }
            }
        }

        @Override
        public int getItemCount() {
            if (mState == STATE_NONE) {
                return mAdapter.getItemCount();
            } else {
                return mAdapter.getItemCount() + 1;
            }
        }


        class LoadMoreVH extends VH {

            private View mItemView;

            public LoadMoreVH() {
                super(mFootItem.onCreateView(RecyclerViewWithFooter.this));
                mItemView = itemView;
            }

            @Override
            public void onBindViewHolder() {
                super.onBindViewHolder();
                if (mState == STATE_LOADING) {
                    mFootItem.onBindData(mItemView, mState);
                } else if (mState == STATE_END) {
                    mFootItem.onBindData(mItemView, mState);
                }
            }
        }

        /**
         * 数据为空时候出现
         */
        class EmptyVH extends VH {

            public EmptyVH() {
                super(mEmptyItem.onCreateView(RecyclerViewWithFooter.this));
            }

            @Override
            public void onBindViewHolder() {
                super.onBindViewHolder();
                mEmptyItem.onBindData(itemView);
            }
        }

        class VH extends RecyclerView.ViewHolder {

            public VH(View itemView) {
                super(itemView);
            }

            public void onBindViewHolder() {

            }
        }
    }
}
