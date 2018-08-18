package com.htdwps.udacitymovieprojectone;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.htdwps.udacitymovieprojectone.adapter.RetrofitClientManager;
import com.htdwps.udacitymovieprojectone.adapter.ReviewsAdapter;
import com.htdwps.udacitymovieprojectone.adapter.TrailersAdapter;
import com.htdwps.udacitymovieprojectone.database.AppExecutors;
import com.htdwps.udacitymovieprojectone.database.AppFavoriteDatabase;
import com.htdwps.udacitymovieprojectone.model.MovieDetail;
import com.htdwps.udacitymovieprojectone.model.ReviewList;
import com.htdwps.udacitymovieprojectone.model.Reviews;
import com.htdwps.udacitymovieprojectone.model.Trailer;
import com.htdwps.udacitymovieprojectone.model.TrailerList;
import com.htdwps.udacitymovieprojectone.util.MovieApiService;
import com.htdwps.udacitymovieprojectone.util.StringConstantsUtil;
import com.htdwps.udacitymovieprojectone.viewholder.MovieViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private ReviewsAdapter reviewsAdapter;
    private List<Reviews> reviewsList;

    // Test a 2/3 ratio imageview
//    private TwoThirdsImageView ivTwoThirdsImageView;
    private ImageView ivMoviePoster;
    private TextView tvMovieTitle;
    private TextView tvMovieReleaseDate;
    private TextView tvMovieVoteAverage;
    private TextView tvMovieSynopsis;
    private RecyclerView rvReviewsListing;
    private TextView tvReviewLabel;
    private RecyclerView rvTrailerListing;
    private TextView tvTrailerLabel;

    private AppFavoriteDatabase mDatabase;

    boolean alreadyFavorite;
    Button favoriteMovieToggleButton;
    int movieKeyInt;
    Double movieVoteAvgDbl;
    String moviePosterString, movieTitleString, movieReleaseDateString, movieVoteAverageString, movieSynopsisString, movieIdKey;

    MovieDetail exists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Timber.plant();
        ButterKnife.bind(this);

        // Create new database or load database
        mDatabase = AppFavoriteDatabase.getInstance(getApplicationContext());

        reviewsList = new ArrayList<>();
        reviewsAdapter = new ReviewsAdapter(this, reviewsList, new ReviewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Reviews reviews) {

            }
        });

//        ivTwoThirdsImageView = findViewById(R.id.iv_two_third_image_poster);
        favoriteMovieToggleButton = findViewById(R.id.favorite_unfavorite_movie_button);
        ivMoviePoster = findViewById(R.id.iv_movie_poster);
        tvMovieTitle = findViewById(R.id.tv_movie_title);
        tvMovieReleaseDate = findViewById(R.id.tv_movie_release_date);
        tvMovieVoteAverage = findViewById(R.id.tv_movie_vote_average);
        tvMovieSynopsis = findViewById(R.id.tv_movie_synopsis);

        tvTrailerLabel = findViewById(R.id.tv_trailer_label);
        tvReviewLabel = findViewById(R.id.tv_reviews_label);

        rvTrailerListing = findViewById(R.id.rv_movie_trailer_list);
//        rvTrailerListing.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvTrailerListing.setLayoutManager(new GridLayoutManager(this, 2));
        rvTrailerListing.addItemDecoration(new DividerItemDecoration(rvTrailerListing.getContext(), DividerItemDecoration.VERTICAL));

        rvReviewsListing = findViewById(R.id.rv_movie_reviews_list);
        rvReviewsListing.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        rvReviewsListing.setAdapter(reviewsAdapter);
        rvReviewsListing.addItemDecoration(new DividerItemDecoration(rvReviewsListing.getContext(), DividerItemDecoration.VERTICAL));

        grabBundledExtras();

        queryDatabaseIfAlreadyFavorite(movieKeyInt);

        favoriteMovieToggleButton.setOnClickListener(this);

    }

    public void queryDatabaseIfAlreadyFavorite(final int id) {

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                exists = mDatabase.movieFavoriteDao().loadMovieAlreadyFavorite(id);

                if (exists != null) {
                    alreadyFavorite = true;
                    tvMovieTitle.setText(exists.getTitle());
                } else {
                    alreadyFavorite = false;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        toggleFavoriteButton(alreadyFavorite);

                    }
                });
            }
        });

    }

    public void toggleFavoriteButton(boolean favorited) {

        String label;

        if (favorited) {
            label = getResources().getString(R.string.unfavorite_button_label);
        } else {
            label = getResources().getString(R.string.favorite_button_label);
        }

        favoriteMovieToggleButton.setText(label);

    }

    public void saveToFavorites() {
        final int movie_id = movieKeyInt;
        final String title = movieTitleString;
        String image = moviePosterString;
        String summary = movieSynopsisString;
        String release = movieReleaseDateString;
        Double voteAvg = movieVoteAvgDbl;

        final MovieDetail movieDetail = new MovieDetail(movie_id, voteAvg, title, moviePosterString, summary, release);

//        Log.i("image", moviePosterString);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                mDatabase.movieFavoriteDao().insertFavoriteMovie(movieDetail);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        queryDatabaseIfAlreadyFavorite(movie_id);
                        Toast.makeText(getApplicationContext(), getString(R.string.favorite_added_string), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });


//        toggleFavoriteButton(true);

    }

    private void removeFavorite() {

        final int id = exists.getId();
        final String title = exists.getTitle();

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                mDatabase.movieFavoriteDao().deleteFavoriteMovie(exists);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        queryDatabaseIfAlreadyFavorite(id);
                        Toast.makeText(getApplicationContext(), getString(R.string.favorite_removed_string), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

//        toggleFavoriteButton(false);

    }

    public void watchYoutubeVideoTrailer(String video_id_key) {
        Intent openAppIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + video_id_key));
        Intent openWebIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + video_id_key));

        openAppIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        openAppIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        openAppIntent.addFlags(Intent.FLAG_FROM_BACKGROUND);

        openWebIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        openWebIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        openWebIntent.addFlags(Intent.FLAG_FROM_BACKGROUND);

        try {
            this.startActivity(openAppIntent);
        } catch (ActivityNotFoundException error) {
            this.startActivity(openWebIntent);
        }
    }

    public void grabBundledExtras() {
        if (getIntent().getExtras() != null) {

            MovieDetail movieMovieDetail = getIntent().getParcelableExtra(StringConstantsUtil.MOVIE_OBJECT_KEY);

            movieKeyInt = movieMovieDetail.getId();
            movieVoteAvgDbl = movieMovieDetail.getVoteAverage();

            movieIdKey = String.valueOf(movieKeyInt);
            moviePosterString = movieMovieDetail.getPosterPath();
            movieTitleString = movieMovieDetail.getOriginalTitle();
            movieReleaseDateString = movieMovieDetail.getReleaseDate();
            movieVoteAverageString = String.valueOf(movieVoteAvgDbl);
            movieSynopsisString = movieMovieDetail.getOverview();

            Log.i("image", "Poster Path is " + moviePosterString);

            populateUiFields(moviePosterString, movieTitleString, movieReleaseDateString, movieVoteAverageString, movieSynopsisString);

            grabTrailerListForMovie(movieIdKey);

            grabReviewsListForMovie(movieIdKey);

        }
    }

    private void grabReviewsListForMovie(String movieIdKey) {

        MovieApiService reviewApiService = RetrofitClientManager.getClient().create(MovieApiService.class);

        Call<ReviewList> reviewListCall;

        reviewListCall = reviewApiService.getMovieReviewsForMovie(movieIdKey, BuildConfig.MOVIE_DB_API_KEY_TOKEN);

        reviewListCall.enqueue(new Callback<ReviewList>() {
            @Override
            public void onResponse(Call<ReviewList> call, Response<ReviewList> response) {

                try {

                    List<Reviews> reviews = response.body().getResults();

                    if (reviews.size() < 1) {
                        tvReviewLabel.setVisibility(View.GONE);
                    }

                    rvReviewsListing.setAdapter(new ReviewsAdapter(getApplicationContext(), reviews, new ReviewsAdapter.OnItemClickListener() {

                        @Override
                        public void onItemClick(Reviews reviews) {
//                            Toast.makeText(DetailActivity.this, reviews.getId(), Toast.LENGTH_SHORT).show();
                        }
                    }));

                } catch (NullPointerException e) {


                }
            }

            @Override
            public void onFailure(Call<ReviewList> call, Throwable t) {

            }
        });

    }

    private void grabTrailerListForMovie(String movieIdKey) {

        MovieApiService trailerApiService = RetrofitClientManager.getClient().create(MovieApiService.class);

        Call<TrailerList> trailerListCall;

        trailerListCall = trailerApiService.getMovieTrailersForMovie(movieIdKey, BuildConfig.MOVIE_DB_API_KEY_TOKEN);

        trailerListCall.enqueue(new Callback<TrailerList>() {
            @Override
            public void onResponse(Call<TrailerList> call, Response<TrailerList> response) {

                try {

                    List<Trailer> trailer = response.body().getResults();

                    if (trailer.size() < 1) {
                        tvTrailerLabel.setVisibility(View.GONE);
                    }

                    rvTrailerListing.setAdapter(new TrailersAdapter(getApplicationContext(), trailer, new TrailersAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Trailer trailer) {

//                            Toast.makeText(DetailActivity.this, trailer.getKey(), Toast.LENGTH_SHORT).show();
                            watchYoutubeVideoTrailer(trailer.getKey());

                        }
                    }));

                } catch (NullPointerException e) {


                }

            }

            @Override
            public void onFailure(Call<TrailerList> call, Throwable t) {

            }
        });


    }

    public void populateUiFields(String poster, String title, String release, String vote, String summary) {

        // 2/3 ratio poster test
//        Picasso.with(this)
//                .load(poster)
//                .placeholder(R.drawable.allblackhorizontal)
//                .error(R.drawable.allblackhorizontal)
//                .into(ivTwoThirdsImageView);

        // Used placeholder and error methods per suggestion from review
        Picasso.with(this)
                .load(MovieViewHolder.IMAGE_PATH_PREFIX + poster)
                .placeholder(R.drawable.allblackhorizontal)
                .error(R.drawable.allblackhorizontal)
                .into(ivMoviePoster);

        if (title == null) {
            tvMovieTitle.setVisibility(View.GONE);
        } else {
            tvMovieTitle.setText(title);
            setTitle(title);
        }

        if (release == null) {
            tvMovieReleaseDate.setVisibility(View.GONE);
        } else {
            tvMovieReleaseDate.setText(release);
        }

        if (vote == null) {
            tvMovieVoteAverage.setVisibility(View.GONE);
        } else {
            tvMovieVoteAverage.setText(vote);
        }

        if (summary == null) {
            tvMovieSynopsis.setVisibility(View.GONE);
        } else {
            tvMovieSynopsis.setText(summary);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id) {

            case R.id.favorite_unfavorite_movie_button:

                if (!alreadyFavorite) {
                    saveToFavorites();
                } else {
                    removeFavorite();
                }

        }
    }

}
