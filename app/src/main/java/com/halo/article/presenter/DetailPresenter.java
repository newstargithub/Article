package com.halo.article.presenter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.text.Html;
import android.text.TextUtils;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.halo.article.R;
import com.halo.article.base.AbstractPresenter;
import com.halo.article.bean.ZhihuDailyNews;
import com.halo.article.bean.ZhihuNewsDetail;
import com.halo.article.model.DataManager;
import com.halo.article.util.NetworkUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.id;
import static android.R.attr.type;
import static android.content.Context.CLIPBOARD_SERVICE;


/**
 * Author: zx
 * Date: 2017/3/3
 * Decription:
 */

public class DetailPresenter extends AbstractPresenter<DetailContract.View> implements DetailContract.Presenter{

    private final DataManager dataManager;
    private ZhihuDailyNews.StoriesBean mItem;
    private ZhihuNewsDetail zhihuDailyStory;

    public DetailPresenter(Context context, DetailContract.View view) {
        super(context, view);
        view.setPresenter(this);
        dataManager = DataManager.getInstance();
    }

    @Override
    public void start() {
        mView.setTitle(mItem.getTitle());
        mView.showCover(mItem.getImages().get(0));
    }

    @Override
    public void setStoriesBean(ZhihuDailyNews.StoriesBean item) {
        mItem = item;
    }

    @Override
    public void openUrl(WebView view, String url) {
        try{
            mContext.startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)));
        } catch (android.content.ActivityNotFoundException ex){
            mView.showBrowserNotFoundError();
        }
    }

    @Override
    public void requestData() {
        if (mItem == null || mItem.getId() == 0) {
            mView.showLoadingError();
            return;
        }
        mView.showLoading();
        // set the web view whether to show the image
        mView.setImageMode(true);
        int id = mItem.getId();
        if (NetworkUtils.isConnected()) {
            dataManager.getNewsDetail(String.valueOf(id), new Callback<ZhihuNewsDetail>() {
                @Override
                public void onResponse(Call<ZhihuNewsDetail> call, Response<ZhihuNewsDetail> response) {
                    mView.stopLoading();
                    zhihuDailyStory = response.body();
                    dataManager.saveNewsDetail(mItem, zhihuDailyStory);
                    if(isViewAttached()) {
                        if(zhihuDailyStory.getBody() == null) {
                            mView.showResultWithoutBody(zhihuDailyStory.getShare_url());
                        } else {

                              mView.showResult(convertZhihuContent(zhihuDailyStory.getBody()));
                        }
                    }
                }

                @Override
                public void onFailure(Call<ZhihuNewsDetail> call, Throwable t) {
                    mView.stopLoading();
                    mView.showLoadingError();
                }
            });
        } else {
            String content = dataManager.getCacheNewsDetail(id);
            if(!TextUtils.isEmpty(content)) {
                try {
                    zhihuDailyStory = new Gson().fromJson(content, ZhihuNewsDetail.class);
                    mView.showResult(convertZhihuContent(zhihuDailyStory.getBody()));
                } catch (JsonSyntaxException e) {
                    mView.showResult(content);
                }
            }
            mView.stopLoading();
        }
    }

    @Override
    public boolean queryIfIsBookmarked() {
        return dataManager.queryIfIsBookmarked(mItem.getId());
    }

    @Override
    public void addToOrDeleteFromBookmarks() {
        if(queryIfIsBookmarked()) {
            dataManager.addToOrDeleteFromBookmarks(mItem.getId());
            mView.showDeletedFromBookmarks();
        } else {
            dataManager.addToOrDeleteFromBookmarks(mItem.getId());
            mView.showAddedToBookmarks();
        }
    }

    @Override
    public void copyLink() {
        if (checkNull()) {
            mView.showCopyTextError();
            return;
        }
        ClipboardManager manager = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text", Html.fromHtml(zhihuDailyStory.getShare_url()).toString());
        manager.setPrimaryClip(clipData);
        mView.showTextCopied();
    }

    private boolean checkNull() {
        return zhihuDailyStory == null;
    }

    @Override
    public void openInBrowser() {
        if (checkNull()) {
            mView.showLoadingError();
            return;
        }

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(zhihuDailyStory.getShare_url()));
            mContext.startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex){
            mView.showBrowserNotFoundError();
        }
    }

    @Override
    public void copyText() {
        if (checkNull()) {
            mView.showCopyTextError();
            return;
        }

        ClipboardManager manager = (ClipboardManager) mContext.getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text", Html.fromHtml(mItem.getTitle() + "\n" + zhihuDailyStory.getBody()).toString());
        manager.setPrimaryClip(clipData);
        mView.showTextCopied();
    }

    @Override
    public void shareAsText() {
        if (checkNull()) {
            mView.showSharingError();
            return;
        }

        try {
            Intent shareIntent = new Intent().setAction(Intent.ACTION_SEND).setType("text/plain");
            String shareText = "" + mItem.getTitle() + " ";

            shareText += zhihuDailyStory.getShare_url();

            shareText = shareText + "\t\t\t" + mContext.getString(R.string.share_extra);

            shareIntent.putExtra(Intent.EXTRA_TEXT,shareText);
            mContext.startActivity(Intent.createChooser(shareIntent, mContext.getString(R.string.share_to)));
        } catch (android.content.ActivityNotFoundException ex){
            mView.showLoadingError();
        }
    }

    private String convertZhihuContent(String preResult) {

        preResult = preResult.replace("<div class=\"img-place-holder\">", "");
        preResult = preResult.replace("<div class=\"headline\">", "");

        // 在api中，css的地址是以一个数组的形式给出，这里需要设置
        // in fact,in api,css addresses are given as an array
        // api中还有js的部分，这里不再解析js
        // javascript is included,but here I don't use it
        // 不再选择加载网络css，而是加载本地assets文件夹中的css
        // use the css file from local assets folder,not from network
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/zhihu_daily.css\" type=\"text/css\">";


        // 根据主题的不同确定不同的加载内容
        // load content judging by different theme
        String theme = "<body className=\"\" onload=\"onLoaded()\">";
        if ((mContext.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                == Configuration.UI_MODE_NIGHT_YES){
            theme = "<body className=\"\" onload=\"onLoaded()\" class=\"night\">";
        }

        return new StringBuilder()
                .append("<!DOCTYPE html>\n")
                .append("<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n")
                .append("<head>\n")
                .append("\t<meta charset=\"utf-8\" />")
                .append(css)
                .append("\n</head>\n")
                .append(theme)
                .append(preResult)
                .append("</body></html>").toString();
    }
}
