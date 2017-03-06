package com.halo.article.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.apkfuns.logutils.LogUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zx on 2017/2/8.
 * Description: Fragment基类
 */

public abstract class BaseFragment extends Fragment {

    private BasePresenter mPresenter;
    private boolean firstLoad = true;
    private boolean prepared = false;
    private boolean visible = false;
    protected Context mContext;

    protected void setBasePresenter(BasePresenter presenter) {
        mPresenter = presenter;
        LogUtils.e("setBasePresenter");
    }

    /**
     * 初始化View和事件
     * @param view  View
     */
    protected abstract void initViews(View view);

    /**
     * 加载数据
     * @param pullToRefresh true 下拉刷新触发
     */
    protected abstract void initData(boolean pullToRefresh);

    /**
     * @return  是否注册事件
     */
    protected abstract boolean isRegisterEvent();

    /**
     * @return  是否懒加载
     */
    protected abstract boolean isLazyLoad();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initArguments();
        if(isRegisterEvent()) {
            registerEvent();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        prepared = true;
        if(isLazyLoad()) {
            lazyLoad();
        } else {
            firstLoad = false;
            LogUtils.e("initData");
            initData(false);
        }
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
        if(mPresenter != null) {
            mPresenter.detachView();
        }
    }

    protected void initArguments() {

    }

    /**
     * 当第一次可见时加载数据
     */
    private void lazyLoad() {
        if (visible && firstLoad && prepared) {
            firstLoad = false;
            LogUtils.e("initData");
            initData(false);
        }
    }

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
