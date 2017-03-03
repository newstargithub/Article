package com.halo.article.presenter;

import android.content.Context;

import com.halo.article.base.AbstractPresenter;
import com.halo.article.bean.ZhihuDailyNews;
import com.halo.article.model.DataManager;
import com.halo.article.util.DateFormatUtils;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author: zx
 * Date: 2017/2/9
 * Description:
 */

public class ZhihuDailyPresenter extends AbstractPresenter<ZhihuDailyContract.View> implements ZhihuDailyContract.Presenter {

    private final DataManager mDataManager;
    private ArrayList<ZhihuDailyNews.StoriesBean> mList = new ArrayList<ZhihuDailyNews.StoriesBean>();

    public ZhihuDailyPresenter(Context context, ZhihuDailyContract.View v) {
        super(context, v);
        v.setPresenter(this);
        mDataManager = DataManager.getInstance();
    }

    @Override
    public void loadPosts(long date, final boolean clear) {
        mDataManager.getDailyNews(DateFormatUtils.getZhihuDailyDateFormat(date), new Callback<ZhihuDailyNews>(){

            @Override
            public void onResponse(Call<ZhihuDailyNews> call, Response<ZhihuDailyNews> response) {
                ZhihuDailyNews dailyNews = response.body();
                if(isViewAttached()) {
                    getView().stopLoading();
                    if(clear) {
                        mList.clear();
                    }
                    mList.addAll(dailyNews.getStories());
                    getView().showResults(mList);
                }
            }

            @Override
            public void onFailure(Call<ZhihuDailyNews> call, Throwable t) {
                if(isViewAttached()) {
                    getView().stopLoading();
                    getView().showError();
                }
            }
        });
    }

    @Override
    public void refresh() {
        loadPosts(Calendar.getInstance().getTimeInMillis(), true);
    }

    @Override
    public void loadMore(long date) {
        loadPosts(date, false);
    }

    @Override
    public void startReading(int position) {

    }

    @Override
    public void feelLucky() {

    }

    @Override
    public void start() {
        loadPosts(Calendar.getInstance().getTimeInMillis(), true);
    }
}
