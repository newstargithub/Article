package com.halo.article.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.halo.article.R;
import com.halo.article.bean.ZhihuDailyNews;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Author: zx
 * Date: 2017/2/9
 * Description:
 */

public class ZhihuDailyAdapter extends BaseMultiItemQuickAdapter<ZhihuDailyNews.StoriesBean, BaseViewHolder> {
    public ZhihuDailyAdapter(List<ZhihuDailyNews.StoriesBean> data) {
        super(data);
        addItemType(ZhihuDailyNews.StoriesBean.ITEM, R.layout.home_list_item_layout);
        addItemType(ZhihuDailyNews.StoriesBean.ITEM_TYPE_HEADER, R.layout.home_list_item_header);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ZhihuDailyNews.StoriesBean item) {
        switch (baseViewHolder.getItemViewType()) {
            case ZhihuDailyNews.StoriesBean.ITEM:
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
                break;
            case ZhihuDailyNews.StoriesBean.ITEM_TYPE_HEADER:
                String dateStr = item.date;
                DateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                DateFormat formatTo = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                try {
                    Date date = format.parse(item.date);
                    dateStr = formatTo.format(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                baseViewHolder.setText(R.id.textViewTitle, dateStr);
                break;
        }
    }
}
