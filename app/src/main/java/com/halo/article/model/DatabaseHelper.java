package com.halo.article.model;

/**
 * Created by zhouxin on 2016/11/9.
 * Description:
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
}
