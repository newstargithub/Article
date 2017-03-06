package com.halo.article.model;

import com.halo.article.api.ZhihuApi;
import com.halo.article.bean.ZhihuDailyNews;
import com.halo.article.bean.ZhihuNewsDetail;
import com.halo.article.util.LoggerInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhouxin on 2016/11/7.
 * Description:
 * Data Layer持有DataManager和一系列的Helper classe
 * PreferencesHelper：从SharedPreferences读取和存储数据。
 * DatabaseHelper：处理操作SQLite数据库。
 * Retrofit services：执行访问REST API，我们现在使用Retrofit来代替Volley，因为它天生支持RxJava。而且也更好用。
 */
public class DataManager {
    private static final String BASE_URL_ZHIHU = " http://news-at.zhihu.com/";

    private static volatile DataManager sInstance;
    private DatabaseHelper mDatabaseHelper;
    private ZhihuApi mZhihuService;

    public static DataManager getInstance() {
        synchronized (DataManager.class) {
            if (sInstance == null) {
                synchronized (DataManager.class) {
                    sInstance = new DataManager();
                }
            }
            return sInstance;
        }
    }

    private DataManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new LoggerInterceptor(null, true));
        OkHttpClient httpClient = builder.build();
        Retrofit.Builder builderRetrofit = new Retrofit.Builder()
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        mZhihuService = builderRetrofit.baseUrl(BASE_URL_ZHIHU).build().create(ZhihuApi.class);
        mDatabaseHelper = DatabaseHelper.getInstance();
    }

    public void getDailyNews(String date, Callback<ZhihuDailyNews> callback) {
        Call<ZhihuDailyNews> call = mZhihuService.getDailyNews(date);
        call.enqueue(callback);
    }

    public void getNewsDetail(String id, Callback<ZhihuNewsDetail> callback) {
        Call<ZhihuNewsDetail> call = mZhihuService.getNewsDetail(id);
        call.enqueue(callback);
    }

    public String getCacheNewsDetail(int id) {
        return mDatabaseHelper.getZhihuDaily(id);
    }

    public void saveNews(ZhihuDailyNews.StoriesBean item) {
        mDatabaseHelper.saveNews(item);
    }

    public void saveNewsDetail(ZhihuDailyNews.StoriesBean item, ZhihuNewsDetail detail) {
        mDatabaseHelper.saveNewsDetail(item, detail);
    }

    public boolean queryIfIsBookmarked(int id) {
        return mDatabaseHelper.queryIfIsBookmarked(id);
    }

    public void addToOrDeleteFromBookmarks(int id) {
        mDatabaseHelper.addToOrDeleteFromBookmarks(id);
    }
}
