package com.htdwps.udacitymovieprojectone.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.htdwps.udacitymovieprojectone.R;

/**
 * Created by HTDWPS on 5/29/18.
 */
public class MovieViewHolder extends RecyclerView.ViewHolder {

    public ImageView posterImageView;

    public MovieViewHolder(View itemView) {
        super(itemView);

        posterImageView = itemView.findViewById(R.id.iv_thumbnail_movie_poster);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
//                    Result clickedItem = movieList
                }
            }
        });
    }
}
