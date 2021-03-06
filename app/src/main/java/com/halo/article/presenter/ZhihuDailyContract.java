package com.halo.article.presenter;

import com.halo.article.base.BasePresenter;
import com.halo.article.base.BaseView;
import com.halo.article.bean.ZhihuDailyNews;

import java.util.List;

/**
 * Created by zx on 2017/2/8.
 * Description:
 */

public interface ZhihuDailyContract {
    interface View extends BaseView<Presenter> {
        // 显示加载或其他类型的错误
        void showError();
        // 显示正在加载
        void showLoading();
        // 停止显示正在加载
        void stopLoading();
        // 成功获取到数据后，在界面中显示
        void showResults(List<ZhihuDailyNews.StoriesBean> list);
        // 显示用于加载指定日期的date picker dialog
        void showPickDialog();
    }

    interface Presenter extends BasePresenter<View> {
        // 请求数据
        void loadPosts(long date, boolean clearing);
        // 刷新数据
        void refresh();
        // 加载更多文章
        void loadMore(long date);
        // 显示详情
        void startReading(int position);
        // 随便看看
        void feelLucky();

        void saveNews(ZhihuDailyNews.StoriesBean item);
    }
}
