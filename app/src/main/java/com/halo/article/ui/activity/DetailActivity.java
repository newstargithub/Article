package com.halo.article.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.halo.article.R;
import com.halo.article.base.BaseActivity;
import com.halo.article.bean.ZhihuDailyNews;
import com.halo.article.presenter.DetailPresenter;
import com.halo.article.ui.fragment.DetailFragment;
import com.halo.article.util.ActivityUtils;
import com.halo.article.util.Constants;

public class DetailActivity extends BaseActivity {

    private ZhihuDailyNews.StoriesBean item;

    private DetailPresenter mDetailPresenter;

    public static Intent newIntent(Context context, ZhihuDailyNews.StoriesBean item) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(Constants.PARAM, item);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame);
        DetailFragment detailFragment =
                (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        if (detailFragment == null) {
            // Create the fragment
            detailFragment = DetailFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), detailFragment, R.id.container);
        }

        // Create the presenter
        mDetailPresenter = new DetailPresenter(this, detailFragment);
        mDetailPresenter.setStoriesBean(item);
    }

    @Override
    protected void initArgument(Intent intent) {
        item = (ZhihuDailyNews.StoriesBean) intent.getSerializableExtra(Constants.PARAM);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
