package com.htdwps.udacitymovieprojectone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.htdwps.udacitymovieprojectone.adapter.MoviesAdapter;
import com.htdwps.udacitymovieprojectone.model.MovieResponse;
import com.htdwps.udacitymovieprojectone.model.Result;
import com.htdwps.udacitymovieprojectone.util.MovieApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    public final static String MOVIEDB_BASE_URL = "http://api.themoviedb.org/3/";

    private RecyclerView recyclerView;
    private MoviesAdapter moviesAdapter;
    private List<Result> movieList;

    // Get JSON
    public Retrofit retrofit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Timber.plant();

        setupLayout();

        loadJsonData();

    }


    public Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(MOVIEDB_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public void loadJsonData() {
        try {
            if (BuildConfig.MOVIE_DB_API_KEY_TOKEN.isEmpty()) {
                Toast.makeText(this, "Your API Key is empty.", Toast.LENGTH_SHORT).show();
                return;
            }

            MovieApiService movieApiService = getClient().create(MovieApiService.class);

            Call<MovieResponse> call = movieApiService.getPopularMovies(BuildConfig.MOVIE_DB_API_KEY_TOKEN);

            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                    Timber.i("Response successful");
                    Timber.i(response.body().getPage().toString());

//                    MovieResponse movieResponse = response.body();
                    List<Result> movies = response.body().getResults();
                    recyclerView.setAdapter(new MoviesAdapter(getApplicationContext(), movies));
//                    moviesAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Failed to retrieve data.", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            Timber.i(e.getMessage());
        }
    }


    public void setupLayout() {
        recyclerView = findViewById(R.id.rv_movie_list_recyclerview);
        movieList = new ArrayList<>();
        moviesAdapter = new MoviesAdapter(this, movieList);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(moviesAdapter);
        moviesAdapter.notifyDataSetChanged();

    }
}
