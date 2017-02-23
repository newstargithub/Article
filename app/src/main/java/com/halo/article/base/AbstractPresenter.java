package com.halo.article.base;

import android.content.Context;

/**
 * Created by zx on 2017/2/8.
 * Description:
 * Presenter经常需要执行一些耗时的操作，例如请求网络数据，
 * Presenter持有了Activity的强引用，如果在请求结束前Activity被销毁了
 * 那么由于网络请求还没返回，导致Presenter一直持有Activity对象，
 * 使得Activity对象无法被回收，此时发生内存泄漏
 * 建立Presenter抽象使用Activity，Fragment的生命周期来解决这个问题
 */
public abstract class AbstractPresenter<V> implements BasePresenter{

    protected Context mContext;

    public AbstractPresenter(Context context, V v) {
        this.mContext = context;
        attachView(v);
    }

    //View接口类型
    private V mView;

    /**
     * 与View建立关联
     * @param view  实现了某个特定接口的Activity或者Fragment类型
     */
    public void attachView(V view) {
        mView = view;
    }

    /**
     * 获取View
     * @return
     */
    public V getView() {
        return mView;
    }

    /**
     * 判断是否与View建立了关联
     * @return boolean
     */
    public boolean isViewAttached() {
        return mView != null;
    }

    /**
     * 与View解除关联
     */
    public void detachView() {
        mView = null;
        mContext = null;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}
