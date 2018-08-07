package com.htdwps.udacitymovieprojectone.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.htdwps.udacitymovieprojectone.R;

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
}
