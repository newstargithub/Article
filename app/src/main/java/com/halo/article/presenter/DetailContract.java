package com.halo.article.presenter;

import android.webkit.WebView;

import com.halo.article.base.BasePresenter;
import com.halo.article.base.BaseView;
import com.halo.article.bean.ZhihuDailyNews;

/**
 * Author: zx
 * Date: 2017/3/3
 * Decription:
 */

public interface DetailContract {
    interface View extends BaseView<Presenter> {

        void setTitle(String title);

        void showCover(String url);

        void showLoadingError();

        void showLoading();

        void setImageMode(boolean showImage);

        void stopLoading();

        void showResultWithoutBody(String share_url);

        void showResult(String result);

        void showCopyTextError();

        void showTextCopied();

        void showBrowserNotFoundError();

        void showSharingError();

        void showDeletedFromBookmarks();

        void showAddedToBookmarks();
    }

    interface Presenter extends BasePresenter<View> {

        void setStoriesBean(ZhihuDailyNews.StoriesBean item);

        void openUrl(WebView view, String url);

        void requestData();

        boolean queryIfIsBookmarked();

        void addToOrDeleteFromBookmarks();

        void copyLink();

        void openInBrowser();

        void copyText();

        void shareAsText();
    }
}
