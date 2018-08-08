package com.htdwps.udacitymovieprojectone.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.htdwps.udacitymovieprojectone.R;
import com.htdwps.udacitymovieprojectone.adapter.ReviewsAdapter;
import com.htdwps.udacitymovieprojectone.model.Reviews;

/**
 * Created by HTDWPS on 8/6/18.
 */
public class ReviewViewHolder extends RecyclerView.ViewHolder {

    private TextView authorTextView;
    private TextView contentTextView;

    public ReviewViewHolder(View itemView) {
        super(itemView);

        authorTextView = itemView.findViewById(R.id.tv_review_author_name);
        contentTextView = itemView.findViewById(R.id.tv_review_content);

    }

    public void bind(final Context context, final Reviews reviews, final ReviewsAdapter.OnItemClickListener listener) {

        authorTextView.setText(reviews.getAuthor());
        contentTextView.setText(reviews.getContent());

    }

}
