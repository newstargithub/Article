package com.halo.article.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.halo.article.app.App;
import com.halo.article.bean.ZhihuDailyNews;
import com.halo.article.bean.ZhihuNewsDetail;
import com.halo.article.model.HistoryDatabaseHelper.ZhihuColumns;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Created by zhouxin on 2016/11/9.
 * Description: 处理操作SQLite数据库
 */
public class DatabaseHelper {

    private static DatabaseHelper sInstance;

    private DatabaseHelper() {

    }

    public static DatabaseHelper getInstance() {
        synchronized (DatabaseHelper.class) {
            if(sInstance == null) {
                synchronized (DatabaseHelper.class) {
                    sInstance = new DatabaseHelper();
                }
            }
            return sInstance;
        }
    }

   /* public Observable saveCarriers(List list) {
        return null;
    }

    public Observable<Jztk> saveJztks(final List<Jztk> jztks) {
        return Observable.create(new Observable.OnSubscribe<Jztk>() {
            @Override
            public void call(Subscriber<? super Jztk> subscriber) {
                if (subscriber.isUnsubscribed()) return;
                SQLiteDatabase db = App.sDb.getWritableDatabase();
                db.beginTransaction();
                try {
                    for (Jztk item : jztks) {
                        long result = App.sDb.insert(item, ConflictAlgorithm.Replace);
                        if (result >= 0) {
                            subscriber.onNext(item);
                        }
                    }
                    db.setTransactionSuccessful();
                    subscriber.onCompleted();
                } finally {
                    db.endTransaction();
                }
            }
        }).sample(1000, TimeUnit.MINUTES);
    }

    public Observable<List<Jztk>> getJztks() {
        return Observable.create(new Observable.OnSubscribe<List<Jztk>>() {
            @Override
            public void call(Subscriber<? super List<Jztk>> subscriber) {
                ArrayList<Jztk> list = App.sDb.query(Jztk.class);
                subscriber.onNext(list);
                subscriber.onCompleted();
            }
        });
    }*/

    public String getZhihuDaily(int id) {
        String content = null;
        Cursor cursor = getReadableDatabase()
                .query(HistoryDatabaseHelper.TABLE_ZHIHU, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getInt(cursor.getColumnIndex(ZhihuColumns.ZHIHU_ID)) == id) {
                    content = cursor.getString(cursor.getColumnIndex(ZhihuColumns.CONTENT));
                    break;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return content;
    }

    public void saveNews(ZhihuDailyNews.StoriesBean item) {
        Gson gson = new Gson();
        SQLiteDatabase db = getWritableDatabase();
        int id = item.getId();
        boolean ifIDExists = queryIfIDExists(id);
        if(!ifIDExists) {
            ContentValues values = new ContentValues();
            values.put(ZhihuColumns.NEWS, gson.toJson(item));
            db.update(HistoryDatabaseHelper.TABLE_ZHIHU, values, ZhihuColumns.ZHIHU_ID + " = ?", new String[] {String.valueOf(id)});
            values.clear();
        } else {
            db.beginTransaction();
            try {
                DateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                Date date = format.parse(item.date);
                ContentValues values = new ContentValues();
                values.put(ZhihuColumns.ZHIHU_ID, id);
                values.put(ZhihuColumns.NEWS, gson.toJson(item));
                values.put(ZhihuColumns.TIME, date.getTime() / 1000);
                db.insert(HistoryDatabaseHelper.TABLE_ZHIHU, null, values);
                values.clear();
                db.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.endTransaction();
            }
        }
    }

    public void saveNewsDetail(ZhihuDailyNews.StoriesBean item, ZhihuNewsDetail detail) {
        Gson gson = new Gson();
        SQLiteDatabase db = getWritableDatabase();
        int id = item.getId();
        boolean ifIDExists = queryIfIDExists(id);
        if(ifIDExists) {
            ContentValues values = new ContentValues();
            values.put(ZhihuColumns.CONTENT, gson.toJson(detail));
            db.update(HistoryDatabaseHelper.TABLE_ZHIHU, values, ZhihuColumns.ZHIHU_ID + " = ?", new String[] {String.valueOf(id)});
            values.clear();
        } else {
            db.beginTransaction();
            try {
                DateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                Date date = format.parse(item.date);
                ContentValues values = new ContentValues();
                values.put(ZhihuColumns.ZHIHU_ID, id);
                values.put(ZhihuColumns.NEWS, gson.toJson(item));
                values.put(ZhihuColumns.CONTENT, gson.toJson(detail));
                values.put(ZhihuColumns.TIME, date.getTime() / 1000);
                db.insert(HistoryDatabaseHelper.TABLE_ZHIHU, null, values);
                values.clear();
                db.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.endTransaction();
            }
        }
    }

    private boolean queryIfIDExists(int id){
        boolean isExist = false;
        SQLiteDatabase db = getReadableDatabase();
        String selection = ZhihuColumns.ZHIHU_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(HistoryDatabaseHelper.TABLE_ZHIHU, null, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()){
            isExist = true;
        }
        cursor.close();
        return isExist;
    }

    public boolean queryIfIsBookmarked(int id) {
        boolean isBookmarked = false;
        SQLiteDatabase db = getReadableDatabase();
        String selection = ZhihuColumns.ZHIHU_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(HistoryDatabaseHelper.TABLE_ZHIHU, null, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            int bookMark = cursor.getInt(cursor.getColumnIndex(ZhihuColumns.BOOKMARK));
            isBookmarked = bookMark != 0;
        }
        cursor.close();
        return isBookmarked;
    }

    private SQLiteDatabase getWritableDatabase() {
        HistoryDatabaseHelper dbHelper = new HistoryDatabaseHelper(App.getContext());
         return dbHelper.getWritableDatabase();
    }

    private SQLiteDatabase getReadableDatabase() {
        HistoryDatabaseHelper dbHelper = new HistoryDatabaseHelper(App.getContext());
        return dbHelper.getReadableDatabase();
    }

    public void addToOrDeleteFromBookmarks(int id) {
        boolean isBookmarked = queryIfIsBookmarked(id);
        SQLiteDatabase db = getWritableDatabase();
        String selection = ZhihuColumns.ZHIHU_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        ContentValues values = new ContentValues();
        values.put(ZhihuColumns.BOOKMARK, isBookmarked ? 0 : 1);
        db.update(HistoryDatabaseHelper.TABLE_ZHIHU, values, selection, selectionArgs);
        values.clear();
    }

    public List<ZhihuDailyNews.StoriesBean> getBookmarks() {
        List<ZhihuDailyNews.StoriesBean> list = new ArrayList<>();
        String news;
        ZhihuDailyNews.StoriesBean storiesBean;
        SQLiteDatabase db = getReadableDatabase();
        String selection = ZhihuColumns.BOOKMARK + " = ?";
        String[] selectionArgs = {"1"};
        Cursor cursor = db.query(HistoryDatabaseHelper.TABLE_ZHIHU, null, selection, selectionArgs, null, null, null);
        if(cursor.moveToFirst()) {
            do {
                try {
                    news = cursor.getString(cursor.getColumnIndex(ZhihuColumns.NEWS));
                    storiesBean = new Gson().fromJson(news, ZhihuDailyNews.StoriesBean.class);
                    list.add(storiesBean);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    //ignore
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
