package com.htdwps.udacitymovieprojectone.adapter;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HTDWPS on 5/30/18.
 */
public class RetrofitClientManager {

    public static final String MOVIEDB_BASE_URL = "http://api.themoviedb.org/3/";

    public static Retrofit getClient() {

        return new Retrofit.Builder()
                .baseUrl(MOVIEDB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
