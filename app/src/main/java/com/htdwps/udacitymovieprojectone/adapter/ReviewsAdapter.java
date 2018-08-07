package com.htdwps.udacitymovieprojectone.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.htdwps.udacitymovieprojectone.R;
import com.htdwps.udacitymovieprojectone.model.Reviews;
import com.htdwps.udacitymovieprojectone.viewholder.ReviewViewHolder;

import java.util.List;

/**
 * Created by HTDWPS on 8/7/18.
 */
public class ReviewsAdapter extends RecyclerView.Adapter<ReviewViewHolder> {

    private Context mContext;
    private List<Reviews> mReviewList;
    private LayoutInflater mInflater;
    private final OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(Reviews reviews);
    }

    public ReviewsAdapter(Context mContext, List<Reviews> mReviewList, OnItemClickListener mListener) {
        this.mContext = mContext;
        this.mReviewList = mReviewList;
        this.mInflater = LayoutInflater.from(mContext);
        this.mListener = mListener;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_review_layout, parent, false);

        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {

        ReviewViewHolder reviewViewHolder = (ReviewViewHolder) holder;
        reviewViewHolder.bind(mContext, mReviewList.get(position), mListener);
    }

    @Override
    public int getItemCount() {
        if (mReviewList != null) {
            return mReviewList.size();
        } else {
            return 0;
        }
    }
}
