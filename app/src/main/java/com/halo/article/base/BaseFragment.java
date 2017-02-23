package com.halo.article.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zx on 2017/2/8.
 * Description: Fragment基类
 */

public abstract class BaseFragment<V extends BaseView, P extends AbstractPresenter<V>> extends Fragment {

    protected P mPresenter;
    protected boolean firstLoad = true;
    protected boolean prepared = false;
    protected boolean visible = false;
    protected Context mContext;

    /**
     * 创建Presenter
     * @return  Presenter
     */
    protected abstract P createPresenter();

    /**
     * 初始化View和事件
     * @param view  View
     */
    protected abstract void initViews(View view);

    /**
     * @return  是否注册事件
     */
    protected abstract boolean isRegisterEvent();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        initArguments();
        if(isRegisterEvent()) {
            registerEvent();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        prepared = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        visible = isVisibleToUser;
        lazyLoad();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(isRegisterEvent()) {
            unregisterEvent();
        }
        mPresenter.detachView();
    }

    protected void initArguments() {

    }

    /**
     * 当第一次可见时加载数据
     */
    private void lazyLoad() {
        if (visible && firstLoad && prepared) {
            firstLoad = false;
            initData(false);
        }
    }

    /**
     * 加载数据
     * @param pullToRefresh true 下拉刷新触发
     */
    protected abstract void initData(boolean pullToRefresh);

    /**
     * 注册事件
     */
    private void registerEvent() {
        EventBus.getDefault().register(this);
    }

    /**
     * 解注册事件
     */
    private void unregisterEvent() {
        EventBus.getDefault().unregister(this);
    }


}
