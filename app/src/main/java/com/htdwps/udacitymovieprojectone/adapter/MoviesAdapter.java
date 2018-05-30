package com.htdwps.udacitymovieprojectone.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
                Toast.makeText(mContext, position + " \nUnique Id " + movie.getId()
                        + " \n" + movie.getOriginalTitle()
                        + " \n" + movie.getOverview()
                        + " \n" + movie.getTitle(), Toast.LENGTH_SHORT).show();
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
