package com.htdwps.udacitymovieprojectone;

import android.content.Intent;
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
import com.htdwps.udacitymovieprojectone.model.MovieDetail;
import com.htdwps.udacitymovieprojectone.model.MovieResponse;
import com.htdwps.udacitymovieprojectone.util.MovieApiService;

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

    public static final String POPULAR_CALL_TAG = "popular";
    public static final String TOPRATED_CALL_TAG = "toprated";
    public static final int FAVORITE_MOVIES_LIST = 2;

    private MoviesAdapter moviesAdapter;
    private List<MovieDetail> movieList;

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

//        setupLayout();
        movieList = new ArrayList<>();
        moviesAdapter = new MoviesAdapter(this, movieList, new MoviesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MovieDetail movie) {

                // I don't think this method is called at all when an item is clicked on the grid view
//                Toast.makeText(MainActivity.this, movie.getId().toString(), Toast.LENGTH_SHORT).show();

//                Intent detailIntent = new Intent(getBaseContext(), DetailActivity.class);
//
//                // Test using Parcelable to pass object from one activity to another
//                MovieDetail result = movie;
//
//                detailIntent.putExtra(MOVIE_OBJECT_KEY, result);
//                Bundle detailBundle = new Bundle();
//
//                detailBundle.putString(DetailActivity.MOVIE_ID_STRING_KEY, String.valueOf(movie.getId()));
//                detailBundle.putString(DetailActivity.MOVIE_POSTER_STRING_KEY, movie.getPosterPath());
//                detailBundle.putString(DetailActivity.MOVIE_TITLE_STRING_KEY, movie.getOriginalTitle());
//                detailBundle.putString(DetailActivity.MOVIE_RELEASE_STRING_KEY, movie.getReleaseDate());
//                detailBundle.putString(DetailActivity.MOVIE_VOTE_STRING_KEY, String.valueOf(movie.getVoteAverage()));
//                detailBundle.putString(DetailActivity.MOVIE_SUMMARY_STRING_KEY, movie.getOverview());
//                detailIntent.putExtras(detailBundle);

//                startActivity(detailIntent);

            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView = findViewById(R.id.rv_movie_list_recyclerview);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(moviesAdapter);

        spinnerSelection = this.getResources().getStringArray(R.array.movie_selection);
        spinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, spinnerSelection);
        spinnerMoviePicker = findViewById(R.id.spinner_toggle_select);
        spinnerMoviePicker.setOnItemSelectedListener(this);
        spinnerMoviePicker.setAdapter(spinnerAdapter);

        swapToggleRetrievedList(0);

    }

    public void setupLayout() {
//        spinnerMoviePicker = findViewById(R.id.spinner_toggle_select);
//        recyclerView = findViewById(R.id.rv_movie_list_recyclerview);


    }

    public void swapToggleRetrievedList(int selected) {

        if (selected == FAVORITE_MOVIES_LIST) {

        } else {
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
                            List<MovieDetail> movies = response.body().getMovieDetails();
                            recyclerView.setAdapter(new MoviesAdapter(getApplicationContext(), movies, new MoviesAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(MovieDetail movie) {

//                                Toast.makeText(MainActivity.this, "From swap toggle " + movie.getOriginalTitle(), Toast.LENGTH_SHORT).show();
                                    Intent detailIntent = new Intent(getBaseContext(), DetailActivity.class);

                                    // This is where the parcelable is being passed
                                    detailIntent.putExtra(MOVIE_OBJECT_KEY, movie);

//                                Bundle detailBundle = new Bundle();
//                                detailBundle.putString(MOVIE_ID_STRING_KEY, String.valueOf(movie.getId()));
//                                detailBundle.putString(MOVIE_POSTER_STRING_KEY, movie.getPosterPath());
//                                detailBundle.putString(MOVIE_TITLE_STRING_KEY, movie.getOriginalTitle());
//                                detailBundle.putString(MOVIE_RELEASE_STRING_KEY, movie.getReleaseDate());
//                                detailBundle.putString(MOVIE_VOTE_STRING_KEY, String.valueOf(movie.getVoteAverage()));
//                                detailBundle.putString(MOVIE_SUMMARY_STRING_KEY, movie.getOverview());
//                                detailIntent.putExtras(detailBundle);
                                    startActivity(detailIntent);

                                }
                            }));
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
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        switch (i) {
            case 0:

                popular = true;
                swapToggleRetrievedList(i);

                break;

            case 1:

                popular = false;
                swapToggleRetrievedList(i);

                break;
                
            case 2:

                swapToggleRetrievedList(FAVORITE_MOVIES_LIST);
                // TODO add the new favorite section into the recyclerview swaptoggle
                Toast.makeText(this, "Favorite List", Toast.LENGTH_SHORT).show();

            default:

                popular = true;
                swapToggleRetrievedList(0);

                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
