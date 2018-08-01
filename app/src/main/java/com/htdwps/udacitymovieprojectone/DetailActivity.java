package com.htdwps.udacitymovieprojectone;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.htdwps.udacitymovieprojectone.ignore.TwoThirdsImageView;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import timber.log.Timber;

public class DetailActivity extends AppCompatActivity {

    public static final String MOVIE_ID_STRING_KEY = "movie_id";
    public static final String MOVIE_POSTER_STRING_KEY = "movie_poster";
    public static final String MOVIE_TITLE_STRING_KEY = "movie_title";
    public static final String MOVIE_RELEASE_STRING_KEY = "movie_release";
    public static final String MOVIE_VOTE_STRING_KEY = "movie_rating";
    public static final String MOVIE_SUMMARY_STRING_KEY = "movie_synopsis";

    // Test a 2/3 ratio imageview
    private TwoThirdsImageView ivTwoThirdsImageView;
    private ImageView ivMoviePoster;
    private TextView tvMovieTitle;
    private TextView tvMovieReleaseDate;
    private TextView tvMovieVoteAverage;
    private TextView tvMovieSynopsis;

    String moviePosterString, movieTitleString, movieReleaseDateString, movieVoteAverageString, movieSynopsisString, movieIdKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Timber.plant();
        ButterKnife.bind(this);

//        ivTwoThirdsImageView = findViewById(R.id.iv_two_third_image_poster);
        ivMoviePoster = findViewById(R.id.iv_movie_poster);
        tvMovieTitle = findViewById(R.id.tv_movie_title);
        tvMovieReleaseDate = findViewById(R.id.tv_movie_release_date);
        tvMovieVoteAverage = findViewById(R.id.tv_movie_vote_average);
        tvMovieSynopsis = findViewById(R.id.tv_movie_synopsis);

        grabBundledExtras();

    }

    public void grabBundledExtras() {
        if (getIntent().getExtras() != null) {

            Bundle bundle = getIntent().getExtras();

            movieIdKey = bundle.getString(MOVIE_ID_STRING_KEY);
            moviePosterString = bundle.getString(MOVIE_POSTER_STRING_KEY);
            movieTitleString = bundle.getString(MOVIE_TITLE_STRING_KEY);
            movieReleaseDateString = bundle.getString(MOVIE_RELEASE_STRING_KEY);
            movieVoteAverageString = bundle.getString(MOVIE_VOTE_STRING_KEY);
            movieSynopsisString = bundle.getString(MOVIE_SUMMARY_STRING_KEY);

            populateUiFields(moviePosterString, movieTitleString, movieReleaseDateString, movieVoteAverageString, movieSynopsisString);

        }
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

        Toast.makeText(this, movieIdKey, Toast.LENGTH_LONG).show();

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
