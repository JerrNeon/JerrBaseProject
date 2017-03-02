package com.cw.jerrbase.pulltorefresh;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;

import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (上拉刷新下拉加载的RecyerView)
 * @create by: chenwei
 * @date 2017/1/6 14:18
 */
public class PullToRefreshRecyerView extends PullToRefreshBase<RecyclerView> {

    /**
     * RecyclerView
     */
    private RecyclerView mRecyclerView;
    /**
     * 用于滑到底部自动加载的Footer
     */
    private LoadingLayout mLoadMoreFooterLayout;
    /**
     * 滚动的监听器
     */
    private RecyclerView.OnScrollListener mScrollListener;


    public PullToRefreshRecyerView(Context context) {
        this(context, null);
    }

    public PullToRefreshRecyerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshRecyerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setPullLoadEnabled(false);
    }

    @Override
    protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
        RecyclerView recyclerView = new RecyclerView(context);
        mRecyclerView = recyclerView;
        recyclerView.addOnScrollListener(new RecyerViewOnScrollListener());
        return recyclerView;
    }

    @Override
    protected boolean isReadyForPullDown() {
        return isFirstItemVisible();
    }

    @Override
    protected boolean isReadyForPullUp() {
        return isLastItemVisible();
    }

    @Override
    protected void startLoading() {
        super.startLoading();
        if (null != mLoadMoreFooterLayout) {
            mLoadMoreFooterLayout.setState(ILoadingLayout.State.REFRESHING);
        }
    }

    @Override
    public void onPullUpRefreshComplete() {
        super.onPullUpRefreshComplete();
        if (null != mLoadMoreFooterLayout) {
            mLoadMoreFooterLayout.setState(ILoadingLayout.State.RESET);
        }
    }

    @Override
    public void setScrollLoadEnabled(boolean scrollLoadEnabled) {
        super.setScrollLoadEnabled(scrollLoadEnabled);
        if (scrollLoadEnabled) {
            // 设置Footer
            if (null == mLoadMoreFooterLayout) {
                mLoadMoreFooterLayout = new FooterLoadingLayout(getContext());
            }
            if (null == mLoadMoreFooterLayout.getParent()) {
                if (mRecyclerView.getAdapter() != null) {
                    HeaderAndFooterWrapper headerAndFooterWrapper = new HeaderAndFooterWrapper(mRecyclerView.getAdapter());
                    headerAndFooterWrapper.addFootView(mLoadMoreFooterLayout);
                }
            }
            mLoadMoreFooterLayout.show(true);
        } else {
            if (null != mLoadMoreFooterLayout) {
                mLoadMoreFooterLayout.show(false);
            }
        }
    }

    @Override
    public LoadingLayout getFooterLoadingLayout() {
        if (isScrollLoadEnabled())
            return mLoadMoreFooterLayout;
        return super.getFooterLoadingLayout();
    }

    @Override
    public LoadingLayout getHeaderLoadingLayout() {
        return super.getHeaderLoadingLayout();
    }

    @Override
    protected LoadingLayout createHeaderLoadingLayout(Context context, AttributeSet attrs) {
        return new HeaderLoadingLayout(context);
    }

    /**
     * 设置适配器
     *
     * @param adapter
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter != null)
            mRecyclerView.setAdapter(adapter);
    }

    /**
     * 设置是否有更多数据的标志
     *
     * @param hasMoreData true表示还有更多的数据，false表示没有更多数据了
     */
    public void setHasMoreData(boolean hasMoreData) {
        if (!hasMoreData) {
            if (null != mLoadMoreFooterLayout) {
                mLoadMoreFooterLayout.setState(ILoadingLayout.State.NO_MORE_DATA);
            }
            LoadingLayout footerLoadingLayout = getFooterLoadingLayout();
            if (null != footerLoadingLayout) {
                footerLoadingLayout.setState(ILoadingLayout.State.NO_MORE_DATA);
            }
        }
    }

    /**
     * 设置滑动的监听器
     *
     * @param scrollListener 监听器
     */
    public void addOnScrollListener(RecyclerView.OnScrollListener scrollListener) {
        mScrollListener = scrollListener;
    }

    /**
     * 表示是否还有更多数据
     *
     * @return true表示还有更多数据
     */
    private boolean hasMoreData() {
        if ((null != mLoadMoreFooterLayout) && (mLoadMoreFooterLayout.getState() == ILoadingLayout.State.NO_MORE_DATA)) {
            return false;
        }

        return true;
    }

    /**
     * 判断第一个child是否完全显示出来
     *
     * @return true完全显示出来，否则false
     */
    private boolean isFirstItemVisible() {
        final RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
        if (null == adapter) {
            return true;
        }

        int mostTop = (mRecyclerView.getChildCount() > 0) ? mRecyclerView.getChildAt(0).getTop() : 0;
        if (mostTop >= 0) {
            return true;
        }

        return false;
    }

    /**
     * 判断最后一个child是否完全显示出来
     *
     * @return true完全显示出来，否则false
     */
    private boolean isLastItemVisible() {
        final RecyclerView.Adapter adapter = mRecyclerView.getAdapter();

        if (null == adapter) {
            return true;
        }
        int firstVisiblePosition = 0;
        int lastVisiblePosition = 0;
        RecyclerView.LayoutManager layoutManager =  mRecyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager){
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            firstVisiblePosition = linearLayoutManager.findFirstVisibleItemPosition();
            lastVisiblePosition = linearLayoutManager.findLastVisibleItemPosition();
        }else  if (layoutManager instanceof GridLayoutManager){
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            firstVisiblePosition = gridLayoutManager.findFirstVisibleItemPosition();
            lastVisiblePosition = gridLayoutManager.findLastVisibleItemPosition();
        }else  if (layoutManager instanceof StaggeredGridLayoutManager){
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
//            firstVisiblePosition = staggeredGridLayoutManager.findFirstVisibleItemPositions();
//            lastVisiblePosition = staggeredGridLayoutManager.findLastVisibleItemPosition();
        }
        final int lastItemPosition = adapter.getItemCount() - 1;

        /**
         * This check should really just be: lastVisiblePosition == lastItemPosition, but ListView
         * internally uses a FooterView which messes the positions up. For me we'll just subtract
         * one to account for it and rely on the inner condition which checks getBottom().
         */
        if (lastVisiblePosition >= lastItemPosition - 1) {
            final int childIndex = lastVisiblePosition - firstVisiblePosition;
            final int childCount = adapter.getItemCount();
            final int index = Math.min(childIndex, childCount - 1);
            final View lastVisibleChild = mRecyclerView.getChildAt(index);
            if (lastVisibleChild != null) {
                return lastVisibleChild.getBottom() <= mRecyclerView.getBottom();
            }
        }

        return false;
    }

    private class RecyerViewOnScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (isScrollLoadEnabled() && hasMoreData()) {
                if (newState == recyclerView.getScrollState()) {
                    if (isReadyForPullUp()) {
                        startLoading();
                    }
                }
            }

            if (null != mScrollListener) {
                mScrollListener.onScrollStateChanged(recyclerView, newState);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (null != mScrollListener) {
                mScrollListener.onScrolled(recyclerView, dx, dy);
            }
        }
    }
}
