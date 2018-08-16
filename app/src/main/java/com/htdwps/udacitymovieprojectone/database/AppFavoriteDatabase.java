package com.htdwps.udacitymovieprojectone.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

import com.htdwps.udacitymovieprojectone.model.MovieDetail;

/**
 * Created by HTDWPS on 8/13/18.
 */

@Database(entities = {MovieDetail.class}, version = 1, exportSchema = false)
@TypeConverters(DoubleConverter.class)
public abstract class AppFavoriteDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppFavoriteDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "favorites_movies_list";
    private static AppFavoriteDatabase sInstance;

    public static AppFavoriteDatabase getInstance(Context context) {
        if (sInstance == null) {

            synchronized (LOCK) {

                Log.d(LOG_TAG, "Creating new database instance");

                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppFavoriteDatabase.class,
                        AppFavoriteDatabase.DATABASE_NAME)
                        //Remove this during the live project
//                        .allowMainThreadQueries()
                        .build();

            }

        }

        Log.d(LOG_TAG, "Grabbing the database instance");

        return sInstance;

    }

    public abstract MovieFavoriteDao movieFavoriteDao();

}
