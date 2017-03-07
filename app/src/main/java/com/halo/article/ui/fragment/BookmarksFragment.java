package com.halo.article.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.halo.article.R;
import com.halo.article.adapter.ZhihuDailyAdapter;
import com.halo.article.base.BaseFragment;
import com.halo.article.bean.ZhihuDailyNews;
import com.halo.article.presenter.BookmarksContract;
import com.halo.article.ui.activity.DetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: zx
 * Date: 2017/3/6
 * Decription:
 */

public class BookmarksFragment extends BaseFragment implements BookmarksContract.View {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    private BookmarksContract.Presenter presenter;
    private ZhihuDailyAdapter mAdapter;

    @Override
    protected void initViews(View view) {
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.shape_drive_line));
        recyclerView.addItemDecoration(itemDecoration);
        mAdapter = new ZhihuDailyAdapter(null);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ZhihuDailyNews.StoriesBean item = mAdapter.getItem(i);
                Intent intent = DetailActivity.newIntent(mContext, item);
                startActivity(intent);
            }
        });
        mAdapter.setEnableLoadMore(false);
    }

    @Override
    protected void initData(boolean pullToRefresh) {
        presenter.start();
    }

    @Override
    protected boolean isRegisterEvent() {
        return false;
    }

    @Override
    protected boolean isLazyLoad() {
        return false;
    }

    @Override
    public void setPresenter(BookmarksContract.Presenter presenter) {
        setBasePresenter(presenter);
        this.presenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void showResult(List<ZhihuDailyNews.StoriesBean> bookmarks) {
        mAdapter.setNewData(bookmarks);
    }

    @Override
    public void showLoading() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void stopLoading() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        });
    }

    public static BookmarksFragment newInstance() {
        return new BookmarksFragment();
    }

    public void notifyDataChanged() {
        presenter.refresh();
    }
}
