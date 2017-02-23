package com.halo.article.app;

import android.app.Application;
import android.content.Context;

import com.halo.article.util.Utils;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by zhouxin on 2017/2/8.
 * Description:
 */

public class App extends Application {

    private static Context sContext;

    @Override public void onCreate() {
        super.onCreate();
        //内存监控
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...
        sContext = getApplicationContext();
        Utils.init(sContext);
    }

    public static Context getContext() {
        return sContext;
    }

}
