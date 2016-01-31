package com.cjj.loadmorervdemo;


import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 加载更多的RecycleView
 * Created by CJJ made a slight change
 */
public class RecyclerViewWithFooter extends RecyclerView {

    Context mContext;
    private LoadMoreAdapter mLoadMoreAdapter;
    private boolean mIsGetDataForNet = false;

    public static final int STATE_END = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_EMPTY = 2;
    public static final int STATE_NONE = 3;


    @IntDef({STATE_END, STATE_LOADING, STATE_EMPTY, STATE_NONE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    private
    @State
    int mState = STATE_NONE;

    /**
     * 默认的 FootItem;
     */
    private FootItem mFootItem = new DefaultFootItem();

    private EmptyItem mEmptyItem = new DefaultEmptyItem(getContext());


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
        mContext = getContext();
        setVerticalLinearLayout();
    }

    public void setVerticalLinearLayout() {
        RVUtils.setVerticalLinearLayout(this);
    }

    public void setGridLayout(int span) {
        RVUtils.setGridLayout(this, span);
    }

    public void setStaggeredGridLayoutManager(int spanCount) {
        RVUtils.setStaggeredGridLayoutManager(this, spanCount);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mState = STATE_LOADING;
        RVUtils.setOnLastItemVisibleListener(this, new OnLoadMoreListenerWrap(onLoadMoreListener));
    }


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


    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter instanceof LoadMoreAdapter) {
            mLoadMoreAdapter = (LoadMoreAdapter) adapter;
            mLoadMoreAdapter.registerAdapterDataObserver(mAdapterDataObserver);
            super.setAdapter(adapter);
        } else {
            mLoadMoreAdapter = new LoadMoreAdapter(adapter);
            mLoadMoreAdapter.registerAdapterDataObserver(mAdapterDataObserver);
            super.setAdapter(mLoadMoreAdapter);
        }
    }


    /**
     * 加载到
     */
    public void setLoad() {
        mState = STATE_LOADING;
        mIsGetDataForNet = false;
        this.getAdapter().notifyItemChanged(this.getAdapter().getItemCount() - 1);

    }

    /**
     * 表示加载到最后了
     */
    public void setEnd(CharSequence charSequence) {
        mIsGetDataForNet = false;
        mState = STATE_END;
        mLoadMoreAdapter.setEndText(charSequence);
        this.getAdapter().notifyItemChanged(this.getAdapter().getItemCount() - 1);
    }

    public void setLoadText(CharSequence loadText) {
        mLoadMoreAdapter.setLoadText(loadText);
        this.getAdapter().notifyItemChanged(this.getAdapter().getItemCount() - 1);
    }

    /**
     * 设置 为空
     */
    public void setEmpty(CharSequence empty, int resId) {
        mState = STATE_EMPTY;
        mLoadMoreAdapter.setEmptyInfo(empty, resId);
        if (this.getAdapter() != null && isEmpty()) {

            this.getAdapter().notifyDataSetChanged();
        }
    }


    /**
     * 是否数据为空
     * @return
     */
    public boolean isEmpty() {

        return (mState == STATE_NONE && getAdapter().getItemCount() == 0) || (mState != STATE_NONE && getAdapter().getItemCount() == 1);
    }

    public boolean loadMoreEnable() {
        return mState != STATE_LOADING;
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

    public void setFootItem(FootItem footItem) {
        mFootItem = footItem;
    }

    public void setEmptyItem(EmptyItem emptyItem) {
        mEmptyItem = emptyItem;
    }

    public class LoadMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        public RecyclerView.Adapter mAdapter;
        private CharSequence mEmptyText;
        private int mEmptyIcon;
        private CharSequence mEndText;

        public static final int LOAD_MORE_VIEW_TYPE = -404;
        public static final int EMPTY_VIEW_TYPE = -403;
        private CharSequence mLoadText;

        public LoadMoreAdapter(Adapter adapter) {
            mAdapter = adapter;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            if (viewType == LOAD_MORE_VIEW_TYPE) {

                return new LoadMoreVH(mFootItem.onCreateView(RecyclerViewWithFooter.this));

            } else if (viewType == EMPTY_VIEW_TYPE) {

                return new EmptyVH(mEmptyItem.onCreateView(RecyclerViewWithFooter.this));
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

        public void setEmptyInfo(CharSequence string, int resId) {
            mEmptyText = string;
            mEmptyIcon = resId;
        }

        public void setEndText(CharSequence end) {
            mEndText = end;
        }

        public void setLoadText(CharSequence loadText) {

            mLoadText = loadText;

        }


        class LoadMoreVH extends VH {

            private View mItemView;

            public LoadMoreVH(View itemView) {
                super(itemView);
                mItemView = itemView;
            }

            @Override
            public void onBindViewHolder() {
                super.onBindViewHolder();
                if (mState == STATE_LOADING) {
                    mFootItem.onBindData(mItemView, mState, mLoadText);
                } else if (mState == STATE_END) {
                    mFootItem.onBindData(mItemView, mState, mEndText);
                }
            }
        }

        /**
         * 数据为空时候出现
         */
        class EmptyVH extends VH {

            public EmptyVH(View itemView) {
                super(itemView);
            }

            @Override
            public void onBindViewHolder() {
                super.onBindViewHolder();
                mEmptyItem.onBindData(itemView, mEmptyText, mEmptyIcon);
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
