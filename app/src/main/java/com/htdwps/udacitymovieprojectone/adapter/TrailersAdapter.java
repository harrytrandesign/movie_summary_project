package com.htdwps.udacitymovieprojectone.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.htdwps.udacitymovieprojectone.R;
import com.htdwps.udacitymovieprojectone.model.Trailer;
import com.htdwps.udacitymovieprojectone.viewholder.TrailerViewHolder;

import java.util.List;

/**
 * Created by HTDWPS on 8/7/18.
 */
public class TrailersAdapter extends RecyclerView.Adapter<TrailerViewHolder> {

    private Context mContext;
    private List<Trailer> mTrailerList;
    private LayoutInflater mInflater;
    private final OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(Trailer trailer);
    }

    public TrailersAdapter(Context mContext, List<Trailer> mTrailerList, OnItemClickListener mListener) {
        this.mContext = mContext;
        this.mTrailerList = mTrailerList;
        this.mInflater = LayoutInflater.from(mContext);
        this.mListener = mListener;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_trailer_layout, parent, false);

        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {

        TrailerViewHolder trailerViewHolder = (TrailerViewHolder) holder;
        trailerViewHolder.bind(mContext, mTrailerList.get(position), mListener);

    }

    @Override
    public int getItemCount() {
        if (mTrailerList != null) {
            return mTrailerList.size();
        } else {
            return 0;
        }
    }
}
