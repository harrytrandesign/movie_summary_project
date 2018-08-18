package com.htdwps.udacitymovieprojectone.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.htdwps.udacitymovieprojectone.model.MovieDetail;

import java.util.List;

/**
 * Created by HTDWPS on 8/17/18.
 */
public class MainViewModel extends AndroidViewModel {

    private LiveData<List<MovieDetail>> movies;

    public LiveData<List<MovieDetail>> getMovies() {
        return movies;
    }

    public MainViewModel(@NonNull Application application) {
        super(application);

        AppFavoriteDatabase appFavoriteDatabase = AppFavoriteDatabase.getInstance(this.getApplication());
        Log.i("database", "Actively retrieving the task from database");
        movies = appFavoriteDatabase.movieFavoriteDao().loadFavorites();

    }
}
