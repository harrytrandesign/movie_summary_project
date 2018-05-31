package com.htdwps.udacitymovieprojectone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.htdwps.udacitymovieprojectone.adapter.MoviesAdapter;
import com.htdwps.udacitymovieprojectone.adapter.RetrofitClientManager;
import com.htdwps.udacitymovieprojectone.model.MovieResponse;
import com.htdwps.udacitymovieprojectone.model.Result;
import com.htdwps.udacitymovieprojectone.util.MovieApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String POPULAR_CALL_TAG = "popular";
    public static final String TOPRATED_CALL_TAG = "toprated";

    private RecyclerView recyclerView;
    private MoviesAdapter moviesAdapter;
    private List<Result> movieList;

    // Menu for swapping between popular and top rated
    private ArrayAdapter<String> spinnerAdapter;
    private String[] spinnerSelection;
    private Spinner spinnerMoviePicker;

    // Get JSON
    public Retrofit retrofit = null;

    // For Testing Purposes
    public boolean popular = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Timber.plant();

        setupLayout();

        spinnerSelection = this.getResources().getStringArray(R.array.movie_selection);
        spinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, spinnerSelection);
        spinnerMoviePicker.setAdapter(spinnerAdapter);

        swapToggleRetrievedList();

    }

    public void loadInitialJsonData() {
        try {
            MovieApiService movieApiService = RetrofitClientManager.getClient().create(MovieApiService.class);

            // Movie DB Api key stored in the gradle.properties file
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
            Timber.i(e);
        }
    }


    public void setupLayout() {
        spinnerMoviePicker = findViewById(R.id.spinner_toggle_select);
        spinnerMoviePicker.setOnItemSelectedListener(this);
        recyclerView = findViewById(R.id.rv_movie_list_recyclerview);
        movieList = new ArrayList<>();
        moviesAdapter = new MoviesAdapter(this, movieList);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(moviesAdapter);

    }

    public void swapToggleRetrievedList() {
        try {

            MovieApiService movieApiService = RetrofitClientManager.getClient().create(MovieApiService.class);

            Call<MovieResponse> call;
            if (popular) {
                call = movieApiService.getPopularMovies(BuildConfig.MOVIE_DB_API_KEY_TOKEN);
            } else {
                call = movieApiService.getTopRatedMovies(BuildConfig.MOVIE_DB_API_KEY_TOKEN);
            }

            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

//                    Timber.i("Response successful");
//                    Timber.i(response.body().getPage().toString());

//                    MovieResponse movieResponse = response.body();
                    try {
                        List<Result> movies = response.body().getResults();
                        recyclerView.setAdapter(new MoviesAdapter(getApplicationContext(), movies));
//                    moviesAdapter.notifyDataSetChanged();
                    } catch (NullPointerException e) {
                        Timber.i(e);
                    }
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Failed to retrieve data.", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            Timber.i(e);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        switch (i) {
            case 0:

                popular = true;
                swapToggleRetrievedList();

                break;

            case 1:

                popular = false;
                swapToggleRetrievedList();

                break;

            default:

                popular = true;
                swapToggleRetrievedList();

                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
