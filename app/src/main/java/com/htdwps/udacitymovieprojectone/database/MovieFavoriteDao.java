package com.htdwps.udacitymovieprojectone.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.htdwps.udacitymovieprojectone.model.MovieDetail;

import java.util.List;

/**
 * Created by HTDWPS on 8/13/18.
 */
@Dao
public interface MovieFavoriteDao {

    @Query("SELECT * FROM favorite_movie ORDER BY title")
    List<MovieDetail> loadFavoriteMovies();

    @Insert
    void insertFavoriteMovie(MovieDetail movieDetail);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(MovieDetail movieDetail);

    @Delete
    void deleteFavoriteMovie(MovieDetail movieDetail);

}
