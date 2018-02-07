package com.johnmagdalinos.android.theguardiannews.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.johnmagdalinos.android.theguardiannews.R;
import com.johnmagdalinos.android.theguardiannews.model.NewsArticle;

import java.util.ArrayList;

/**
 * RecyclerView Adapter used to populate the RecyclerView with NewsArticles
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    /** Member variables */
    private ArrayList<NewsArticle> mArticles;
    private TextView mTitleTextView, mSectionTextView, mDateTextView, mTimeTextView;

    /** Class constructor */
    public NewsAdapter(ArrayList<NewsArticle> articles) {
        mArticles = articles;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,
                parent, false);

        return new NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        mTitleTextView.setText(mArticles.get(position).getWebTitle());
        mSectionTextView.setText(mArticles.get(position).getSectionId());
        mDateTextView.setText(mArticles.get(position).getPublicationDate());
        mTimeTextView.setText(mArticles.get(position).getPublicationDate());
    }

    @Override
    public int getItemCount() {
        if (mArticles == null) {
            return 0;
        } else {
            return mArticles.size();
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        public NewsViewHolder(View itemView) {
            super(itemView);

            mTitleTextView = itemView.findViewById(R.id.tv_list_item_title);
            mSectionTextView = itemView.findViewById(R.id.tv_list_item_section);
            mDateTextView = itemView.findViewById(R.id.tv_list_item_date);
            mTimeTextView = itemView.findViewById(R.id.tv_list_item_time);
        }
    }

    public void swapList(ArrayList<NewsArticle> newArticles) {
        mArticles = newArticles;
        notifyDataSetChanged();
    }
}

