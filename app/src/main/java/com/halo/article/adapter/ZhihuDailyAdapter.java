package com.halo.article.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.halo.article.R;
import com.halo.article.bean.ZhihuDailyNews;

import java.util.List;

/**
 * Author: zx
 * Date: 2017/2/9
 * Description:
 */

public class ZhihuDailyAdapter extends BaseQuickAdapter<ZhihuDailyNews.StoriesBean, BaseViewHolder> {
    public ZhihuDailyAdapter(List data) {
        super(R.layout.home_list_item_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ZhihuDailyNews.StoriesBean item) {
        baseViewHolder.setText(R.id.textViewTitle, item.getTitle());
        ImageView imageViewCover = baseViewHolder.getView(R.id.imageViewCover);
        if (item.getImages().get(0) == null){
            imageViewCover.setImageResource(R.drawable.placeholder);
        } else {
            Glide.with(mContext)
                    .load(item.getImages().get(0))
                    .asBitmap()
                    .placeholder(R.drawable.placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .error(R.drawable.placeholder)
                    .centerCrop()
                    .into(imageViewCover);
        }
    }
}
