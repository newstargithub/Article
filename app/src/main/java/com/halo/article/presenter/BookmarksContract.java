package com.halo.article.presenter;

import com.halo.article.base.BasePresenter;
import com.halo.article.base.BaseView;
import com.halo.article.bean.ZhihuDailyNews;

import java.util.List;

/**
 * Author: zx
 * Date: 2017/3/6
 * Decription:
 */

public interface BookmarksContract {
    interface View extends BaseView<Presenter> {

        void showResult(List<ZhihuDailyNews.StoriesBean> bookmarks);

        void showLoading();

        void stopLoading();
    }

    interface Presenter extends BasePresenter<View> {

        void refresh();

    }
}
