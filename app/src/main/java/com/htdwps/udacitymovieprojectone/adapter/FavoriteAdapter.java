package com.htdwps.udacitymovieprojectone.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.htdwps.udacitymovieprojectone.R;
import com.htdwps.udacitymovieprojectone.model.FavoriteMovie;
import com.htdwps.udacitymovieprojectone.viewholder.MovieViewHolder;

import java.util.List;

/**
 * Created by HTDWPS on 8/14/18.
 */
public class FavoriteAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private Context context;
    private List<FavoriteMovie> favoriteList;
    private LayoutInflater mInflater;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(FavoriteMovie movie);
    }

    public FavoriteAdapter(Context context, List<FavoriteMovie> favoriteList, OnItemClickListener listener) {
        this.context = context;
        this.favoriteList = favoriteList;
        this.mInflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_movie_poster_thumbnail, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        MovieViewHolder movieViewHolder = (MovieViewHolder) holder;

    }

    @Override
    public int getItemCount() {
        if (favoriteList != null) {
            return favoriteList.size();
        } else {
            return 0;
        }
    }
}
