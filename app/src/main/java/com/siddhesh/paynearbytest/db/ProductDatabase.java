package com.siddhesh.paynearbytest.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


@Database(entities = {ProductDetails.class,Favorite.class}, version = 1, exportSchema = false)
public abstract class ProductDatabase extends RoomDatabase {

    private static final String TAG = "LocationDatabase";

    public abstract ProductDao productDao();


    private static ProductDatabase productDatabase;

    // synchronized is use to avoid concurrent access in multithred environment
    public static synchronized ProductDatabase getInstance(Context context) {
        if (null == productDatabase) {
            productDatabase = buildDatabaseInstance(context);
        }
        return productDatabase;
    }

    private static ProductDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context
                , ProductDatabase.class
                , "productDatabase").allowMainThreadQueries().build();//TODO Remove allowMainThreadQueries() and all database operations in service or async
    }

    public void cleanUp() {
        productDatabase = null;
    }
}


