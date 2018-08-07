package com.htdwps.udacitymovieprojectone;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.htdwps.udacitymovieprojectone.adapter.RetrofitClientManager;
import com.htdwps.udacitymovieprojectone.adapter.ReviewsAdapter;
import com.htdwps.udacitymovieprojectone.ignore.TwoThirdsImageView;
import com.htdwps.udacitymovieprojectone.model.MovieDetail;
import com.htdwps.udacitymovieprojectone.model.ReviewList;
import com.htdwps.udacitymovieprojectone.model.Reviews;
import com.htdwps.udacitymovieprojectone.model.Trailer;
import com.htdwps.udacitymovieprojectone.model.TrailerList;
import com.htdwps.udacitymovieprojectone.util.MovieApiService;
import com.htdwps.udacitymovieprojectone.util.StringConstantsUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class DetailActivity extends AppCompatActivity {

    private ReviewsAdapter reviewsAdapter;
    private List<Reviews> reviewsList;

    // Test a 2/3 ratio imageview
    private TwoThirdsImageView ivTwoThirdsImageView;
    private ImageView ivMoviePoster;
    private TextView tvMovieTitle;
    private TextView tvMovieReleaseDate;
    private TextView tvMovieVoteAverage;
    private TextView tvMovieSynopsis;
    private RecyclerView rvReviewsListing;

    String moviePosterString, movieTitleString, movieReleaseDateString, movieVoteAverageString, movieSynopsisString, movieIdKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Timber.plant();
        ButterKnife.bind(this);

        reviewsList = new ArrayList<>();
        reviewsAdapter = new ReviewsAdapter(this, reviewsList, new ReviewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Reviews reviews) {

            }
        });

//        ivTwoThirdsImageView = findViewById(R.id.iv_two_third_image_poster);
        ivMoviePoster = findViewById(R.id.iv_movie_poster);
        tvMovieTitle = findViewById(R.id.tv_movie_title);
        tvMovieReleaseDate = findViewById(R.id.tv_movie_release_date);
        tvMovieVoteAverage = findViewById(R.id.tv_movie_vote_average);
        tvMovieSynopsis = findViewById(R.id.tv_movie_synopsis);
        rvReviewsListing = findViewById(R.id.rv_movie_reviews_list);
        rvReviewsListing.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvReviewsListing.setAdapter(reviewsAdapter);

        grabBundledExtras();

    }

    public void grabBundledExtras() {
        if (getIntent().getExtras() != null) {

//            Bundle bundle = getIntent().getExtras();
//
//            movieIdKey = bundle.getString(MOVIE_ID_STRING_KEY);
//            moviePosterString = bundle.getString(MOVIE_POSTER_STRING_KEY);
//            movieTitleString = bundle.getString(MOVIE_TITLE_STRING_KEY);
//            movieReleaseDateString = bundle.getString(MOVIE_RELEASE_STRING_KEY);
//            movieVoteAverageString = bundle.getString(MOVIE_VOTE_STRING_KEY);
//            movieSynopsisString = bundle.getString(MOVIE_SUMMARY_STRING_KEY);

            MovieDetail movieMovieDetail = getIntent().getParcelableExtra(StringConstantsUtil.MOVIE_OBJECT_KEY);

            movieIdKey = String.valueOf(movieMovieDetail.getId());
            moviePosterString = movieMovieDetail.getPosterPath();
            movieTitleString = movieMovieDetail.getOriginalTitle();
            movieReleaseDateString = movieMovieDetail.getReleaseDate();
            movieVoteAverageString = String.valueOf(movieMovieDetail.getVoteAverage());
            movieSynopsisString = movieMovieDetail.getOverview();

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
                    rvReviewsListing.setAdapter(new ReviewsAdapter(getApplicationContext(), reviews, new ReviewsAdapter.OnItemClickListener() {

                        @Override
                        public void onItemClick(Reviews reviews) {
//                            Toast.makeText(DetailActivity.this, reviews.getId(), Toast.LENGTH_SHORT).show();
                        }
                    }));

//                    for (Reviews reviews1 : reviews) {
//
//                        Toast.makeText(DetailActivity.this, reviews1.getContent(), Toast.LENGTH_SHORT).show();
//                        Timber.d(reviews1.getAuthor() + " " + reviews1.getContent());
//
//                    }

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

                    for (Trailer trailer1 : trailer) {

//                        Toast.makeText(DetailActivity.this, trailer1.getKey() + " " + trailer1.getSite(), Toast.LENGTH_SHORT).show();
                        Timber.d(trailer1.getKey() + " " + trailer1.getSite());

                    }

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
                .load(poster)
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

        // This toast pops up when detail activity opens up
//        Toast.makeText(this, movieSynopsisString, Toast.LENGTH_LONG).show();

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

}
