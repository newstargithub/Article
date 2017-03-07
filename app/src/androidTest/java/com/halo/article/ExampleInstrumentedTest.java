package com.halo.article;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.apkfuns.logutils.LogUtils;
import com.halo.article.bean.ZhihuDailyNews;
import com.halo.article.model.DataManager;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.halo.article", appContext.getPackageName());
    }

    @Test
    public void testCache() {
        DataManager instance = DataManager.getInstance();
        List<ZhihuDailyNews.StoriesBean> bookmarks = instance.getBookmarks();
        LogUtils.d(bookmarks);
    }
}
