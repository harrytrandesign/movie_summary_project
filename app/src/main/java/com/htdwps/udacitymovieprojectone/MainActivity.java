package com.htdwps.udacitymovieprojectone;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.htdwps.udacitymovieprojectone.adapter.FavoriteAdapter;
import com.htdwps.udacitymovieprojectone.adapter.MoviesAdapter;
import com.htdwps.udacitymovieprojectone.adapter.RetrofitClientManager;
import com.htdwps.udacitymovieprojectone.database.AppFavoriteDatabase;
import com.htdwps.udacitymovieprojectone.model.MovieDetail;
import com.htdwps.udacitymovieprojectone.model.MovieResponse;
import com.htdwps.udacitymovieprojectone.util.MovieApiService;
import com.htdwps.udacitymovieprojectone.util.ThumbnailResizer;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import timber.log.Timber;

import static com.htdwps.udacitymovieprojectone.util.StringConstantsUtil.MOVIE_OBJECT_KEY;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private RecyclerView recyclerView;
    private Spinner spinnerMoviePicker;

    public static final int POPULAR_CALL_TAG = 0;
    public static final int TOP_RATED_CALL_TAG = 1;
    public static final int FAVORITE_MOVIES_LIST = 2;

    private AppFavoriteDatabase mDatabase;

    private MoviesAdapter moviesAdapter;
    private List<MovieDetail> movieList;
    private FavoriteAdapter favoriteAdapter;

    // Menu for swapping between popular and top rated
    private ArrayAdapter<String> spinnerAdapter;
    private String[] spinnerSelection;

    // Get JSON
    public Retrofit retrofit = null;

    // For Testing Purposes
    public boolean popular = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Timber.plant();
        ButterKnife.bind(this);

        movieList = new ArrayList<>();
        moviesAdapter = new MoviesAdapter(this, movieList, new MoviesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MovieDetail movie) {

            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, ThumbnailResizer.calculateNoOfColumns(this));
        recyclerView = findViewById(R.id.rv_movie_list_recyclerview);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(moviesAdapter);

        spinnerSelection = this.getResources().getStringArray(R.array.movie_selection);
        spinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, spinnerSelection);
        spinnerMoviePicker = findViewById(R.id.spinner_toggle_select);
        spinnerMoviePicker.setOnItemSelectedListener(this);
        spinnerMoviePicker.setAdapter(spinnerAdapter);

        mDatabase = AppFavoriteDatabase.getInstance(getApplicationContext());

    }

    public void swapToggleRetrievedList(int selected) {

        if (selected == FAVORITE_MOVIES_LIST) {

            LiveData<List<MovieDetail>> favorites = mDatabase.movieFavoriteDao().loadFavorites();
            favorites.observe(this, new Observer<List<MovieDetail>>() {
                @Override
                public void onChanged(@Nullable List<MovieDetail> movieDetails) {

                    Log.i("image", "Receiving database update from LiveData.");
                    recyclerView.setAdapter(new MoviesAdapter(getApplicationContext(), movieDetails, new MoviesAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(MovieDetail movie) {

                            Intent detailIntent = new Intent(getBaseContext(), DetailActivity.class);
                            detailIntent.putExtra(MOVIE_OBJECT_KEY, movie);
                            startActivity(detailIntent);

                        }
                    }));

                }
            });

        } else {

            try {

                MovieApiService movieApiService = RetrofitClientManager.getClient().create(MovieApiService.class);

                Call<MovieResponse> call;
                if (selected == POPULAR_CALL_TAG) {
                    call = movieApiService.getPopularMovies(BuildConfig.MOVIE_DB_API_KEY_TOKEN);
                } else {
                    call = movieApiService.getTopRatedMovies(BuildConfig.MOVIE_DB_API_KEY_TOKEN);
                }

                call.enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                        try {
                            List<MovieDetail> movies = response.body().getMovieDetails();
                            recyclerView.setAdapter(new MoviesAdapter(getApplicationContext(), movies, new MoviesAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(MovieDetail movie) {

//                                Toast.makeText(MainActivity.this, "From swap toggle " + movie.getOriginalTitle(), Toast.LENGTH_SHORT).show();
                                    Intent detailIntent = new Intent(getBaseContext(), DetailActivity.class);

                                    // This is where the parcelable is being passed
                                    detailIntent.putExtra(MOVIE_OBJECT_KEY, movie);

                                    startActivity(detailIntent);

                                }
                            }));

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
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        int number;

        number = spinnerMoviePicker.getSelectedItemPosition();

//        Toast.makeText(this, "" + number, Toast.LENGTH_SHORT).show();

        swapToggleRetrievedList(number);

//        switch (i) {
//            case POPULAR_CALL_TAG:
//
//                popular = true;
//                swapToggleRetrievedList(i);
//
//                break;
//
//            case TOP_RATED_CALL_TAG:
//
//                popular = false;
//                swapToggleRetrievedList(i);
//
//                break;
//
//            case FAVORITE_MOVIES_LIST:
//
//                swapToggleRetrievedList(i);
//                Toast.makeText(this, "Favorite List", Toast.LENGTH_SHORT).show();
//
//                break;
//
//            default:
//
//                popular = true;
//                swapToggleRetrievedList(POPULAR_CALL_TAG);
//
//                break;
//
//        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void onResume() {
        super.onResume();

        swapToggleRetrievedList(spinnerMoviePicker.getSelectedItemPosition());

    }
}
