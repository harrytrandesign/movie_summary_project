package com.htdwps.udacitymovieprojectone.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.htdwps.udacitymovieprojectone.DetailActivity;
import com.htdwps.udacitymovieprojectone.R;
import com.htdwps.udacitymovieprojectone.model.Result;
import com.htdwps.udacitymovieprojectone.viewholder.MovieViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by HTDWPS on 5/29/18.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private Context mContext;
    private List<Result> mMovieList;
    private LayoutInflater mInflater;

    public MoviesAdapter(Context mContext, List<Result> movieList) {
        this.mContext = mContext;
        this.mMovieList = movieList;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_movie_poster_thumbnail, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {

        final Result movie = mMovieList.get(position);

        Picasso.with(mContext)
                .load(movie.getPosterPath())
                .placeholder(R.color.colorPrimary)
                .into(holder.posterImageView);

        holder.posterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext, "Unique Id " + movie.getId()
//                        + "\n" + movie.getOriginalTitle()
//                        + "\n" + movie.getReleaseDate()
//                        + "\n" + movie.getVoteAverage()
//                        + "\n" + movie.getOverview()
//                        + "\n" + movie.getPosterPath(), Toast.LENGTH_SHORT).show();

                Intent detailIntent = new Intent(mContext, DetailActivity.class);
                Bundle detailBundle = new Bundle();
                detailBundle.putString(DetailActivity.MOVIE_POSTER_STRING_KEY, movie.getPosterPath());
                detailBundle.putString(DetailActivity.MOVIE_TITLE_STRING_KEY, movie.getOriginalTitle());
                detailBundle.putString(DetailActivity.MOVIE_RELEASE_STRING_KEY, movie.getReleaseDate());
                detailBundle.putString(DetailActivity.MOVIE_VOTE_STRING_KEY, String.valueOf(movie.getVoteAverage()));
                detailBundle.putString(DetailActivity.MOVIE_SUMMARY_STRING_KEY, movie.getOverview());
                detailIntent.putExtras(detailBundle);
                mContext.startActivity(detailIntent);

            }
        });


    }

    @Override
    public int getItemCount() {
        if (mMovieList != null) {
            return mMovieList.size();
        } else {
            return 0;
        }
    }
}
