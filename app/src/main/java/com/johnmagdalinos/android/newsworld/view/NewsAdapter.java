package com.johnmagdalinos.android.newsworld.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.johnmagdalinos.android.newsworld.R;
import com.johnmagdalinos.android.newsworld.model.articlesdb.NewsArticle;
import com.johnmagdalinos.android.newsworld.utilities.DataUtilities;

/**
 * RecyclerView Adapter used to populate the RecyclerView with NewsArticles
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    /** Member variables */
    private NewsAdapterCallback mCallback;
    private NewsArticle[] mArticles;
    private TextView mTitleTextView, mSectionTextView, mDateTextView, mTimeTextView;

    /** Interface that handles click events */
    public interface NewsAdapterCallback {
        void NewsClicked(String url);
    }

    /** Class constructor */
    public NewsAdapter(NewsAdapterCallback callback, NewsArticle[] articles) {
        mCallback = callback;
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
        String dateSource = mArticles[position].getWebPublicationDate();
        mTitleTextView.setText(mArticles[position].getWebTitle());
        mSectionTextView.setText(mArticles[position].getSectionId());
        mDateTextView.setText(DataUtilities.convertAPIDate(dateSource));
        mTimeTextView.setText(DataUtilities.convertAPITime(dateSource));
    }

    @Override
    public int getItemCount() {
        if (mArticles == null) {
            return 0;
        } else {
            return mArticles.length;
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

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public NewsViewHolder(View itemView) {
            super(itemView);

            mTitleTextView = itemView.findViewById(R.id.tv_list_item_title);
            mSectionTextView = itemView.findViewById(R.id.tv_list_item_section);
            mDateTextView = itemView.findViewById(R.id.tv_list_item_date);
            mTimeTextView = itemView.findViewById(R.id.tv_list_item_time);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String url = mArticles[getAdapterPosition()].getWebUrl();
            mCallback.NewsClicked(url);
        }
    }

    public void swapList(NewsArticle[] newArticles) {
        mArticles = newArticles;
        notifyDataSetChanged();
    }
}

