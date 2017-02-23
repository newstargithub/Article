package com.halo.article.api;

import com.halo.article.bean.ZhihuDailyNews;
import com.halo.article.bean.ZhihuNewsDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by zx on 2016/11/10.
 * Description: 知乎接口
 */
public interface ZhihuApi {

    /*
     public interface GitHubService {
        @GET("users/{user}/repos")
        Call<List<Repo>> listRepos(@Path("user") String user);
    }
     */

    /**
     * http://news-at.zhihu.com/api/4/news/before/20170122
     * 获取知乎日报2017年1月22日的消息列表
     * */
    @GET("api/4/news/before/{date}")
    Call<ZhihuDailyNews> getDailyNews(@Path("date") String date);


    /**
     * http://news-at.zhihu.com/api/4/news/9165434
     * 获取详细的内容，例如，我们获取id为9165434的内容，只需要将id拼接到http://news-at.zhihu.com/api/4/news/之后
     */
    @GET("api/4/news/{id}")
    Call<ZhihuNewsDetail> getNewsDetail(@Path("id") String id);

}
