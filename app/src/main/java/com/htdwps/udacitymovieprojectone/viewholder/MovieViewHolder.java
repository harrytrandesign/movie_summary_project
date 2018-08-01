package com.htdwps.udacitymovieprojectone.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.htdwps.udacitymovieprojectone.R;
import com.htdwps.udacitymovieprojectone.adapter.MoviesAdapter;
import com.htdwps.udacitymovieprojectone.model.MovieDetail;
import com.squareup.picasso.Picasso;

/**
 * Created by HTDWPS on 5/29/18.
 */
public class MovieViewHolder extends RecyclerView.ViewHolder {

    private ImageView posterImageView;

    public MovieViewHolder(View itemView) {
        super(itemView);

        posterImageView = itemView.findViewById(R.id.iv_thumbnail_movie_poster);

//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int position = getAdapterPosition();
//                if (position != RecyclerView.NO_POSITION) {
////                    MovieDetail clickedItem = movieList
//                }
//            }
//        });
    }

    public void bind(final Context context, final MovieDetail movie, final MoviesAdapter.OnItemClickListener listener) {

        Picasso.with(context)
                .load(movie.getPosterPath())
                .placeholder(R.color.colorPrimary)
                .into(posterImageView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(movie);
//                Toast.makeText(context, "Only see this if it works as it should work", Toast.LENGTH_SHORT).show();
            }
        });
        //        holder.posterImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Toast.makeText(mContext, "Unique Id " + movie.getId()
////                        + "\n" + movie.getOriginalTitle()
////                        + "\n" + movie.getReleaseDate()
////                        + "\n" + movie.getVoteAverage()
////                        + "\n" + movie.getOverview()
////                        + "\n" + movie.getPosterPath(), Toast.LENGTH_SHORT).show();
//
//                Intent detailIntent = new Intent(mContext, DetailActivity.class);
//                Bundle detailBundle = new Bundle();
//                detailBundle.putString(DetailActivity.MOVIE_POSTER_STRING_KEY, movie.getPosterPath());
//                detailBundle.putString(DetailActivity.MOVIE_TITLE_STRING_KEY, movie.getOriginalTitle());
//                detailBundle.putString(DetailActivity.MOVIE_RELEASE_STRING_KEY, movie.getReleaseDate());
//                detailBundle.putString(DetailActivity.MOVIE_VOTE_STRING_KEY, String.valueOf(movie.getVoteAverage()));
//                detailBundle.putString(DetailActivity.MOVIE_SUMMARY_STRING_KEY, movie.getOverview());
//                detailIntent.putExtras(detailBundle);
//                mContext.startActivity(detailIntent);

    }

}
