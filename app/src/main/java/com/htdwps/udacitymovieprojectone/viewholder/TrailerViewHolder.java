package com.htdwps.udacitymovieprojectone.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.htdwps.udacitymovieprojectone.R;
import com.htdwps.udacitymovieprojectone.adapter.TrailersAdapter;
import com.htdwps.udacitymovieprojectone.model.Trailer;

/**
 * Created by HTDWPS on 8/6/18.
 */
public class TrailerViewHolder extends RecyclerView.ViewHolder {

    private TextView titleTextView;
    private TextView sourceTextView;
    private TextView videoTypeTextView; // Trailer, short, clip, etc
    private Button viewTrailerButton;

    public TrailerViewHolder(View itemView) {
        super(itemView);

        titleTextView = itemView.findViewById(R.id.tv_trailer_name);
        sourceTextView = itemView.findViewById(R.id.tv_trailer_source);
        videoTypeTextView = itemView.findViewById(R.id.tv_trailer_type);
        viewTrailerButton = itemView.findViewById(R.id.btn_view_trailer);

    }

    public void bind(final Context context, final Trailer trailer, final TrailersAdapter.OnItemClickListener listener) {

        titleTextView.setText(trailer.getName());
        sourceTextView.setText(trailer.getSite());
        videoTypeTextView.setText(trailer.getType());

        viewTrailerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(trailer);
            }
        });

    }

}
