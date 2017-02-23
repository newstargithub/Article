package com.halo.article.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

/**
 * Author: zx
 * Date: 2017/2/9
 * Description:
 */

public class ResourceUtils {
    private static Context mContext = Utils.getContext();

    public static Drawable getDrawable(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return mContext.getResources().getDrawable(id, mContext.getTheme());
        } else {
            return mContext.getDrawable(id);
        }
    }


}
