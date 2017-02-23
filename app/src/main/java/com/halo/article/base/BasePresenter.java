package com.halo.article.base;

/**
 * Created by zx on 2017/2/8.
 * Description: 接口
 */

public interface BasePresenter {
    // 获取数据并改变界面显示，在todo-mvp的项目中的调用时机为Fragment的OnResume()方法中
    void start();

}
