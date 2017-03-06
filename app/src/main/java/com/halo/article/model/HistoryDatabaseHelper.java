package com.halo.article.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Author: zx
 * Date: 2017/3/3
 * Decription: 缓存历史数据库帮助类
 */

public class HistoryDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "History.db";
    private static final int VERSION = 1;

    public static final String TABLE_ZHIHU = "zhihu";
    private static final String CREATE_ZHIHU = "create table if not exists " + TABLE_ZHIHU + " ( " +
            ZhihuColumns._ID + " integer primary key autoincrement," +
            ZhihuColumns.ZHIHU_ID + " integer not null," +
            ZhihuColumns.NEWS + " text," +
            ZhihuColumns.TIME + " real," +
            ZhihuColumns.CONTENT + " text," +
            ZhihuColumns.BOOKMARK + " integer default 0 " + ")";

    public HistoryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ZHIHU);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    interface ZhihuColumns extends BaseColumns {
        String ZHIHU_ID = "zhihu_id";
        String NEWS = "news";
        String TIME = "time";
        String CONTENT = "content";
        String BOOKMARK = "bookmark";
    }


}
