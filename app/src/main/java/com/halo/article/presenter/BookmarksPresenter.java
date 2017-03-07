package com.halo.article.presenter;

import android.content.Context;

import com.halo.article.base.AbstractPresenter;
import com.halo.article.bean.ZhihuDailyNews;
import com.halo.article.model.DataManager;

import java.util.List;

/**
 * Author: zx
 * Date: 2017/3/6
 * Decription:
 */

public class BookmarksPresenter extends AbstractPresenter<BookmarksContract.View> implements BookmarksContract.Presenter{
    private final DataManager dataManager;

    public BookmarksPresenter(Context context, BookmarksContract.View view) {
        super(context, view);
        view.setPresenter(this);
        dataManager = DataManager.getInstance();
    }

    @Override
    public void start() {
        loadData(false);
    }

    private void loadData(boolean refresh) {
        if (!refresh) {
            mView.showLoading();
        }

        List<ZhihuDailyNews.StoriesBean> bookmarks = dataManager.getBookmarks();
        mView.showResult(bookmarks);

        mView.stopLoading();
    }

    @Override
    public void refresh() {
        loadData(true);
    }

}
