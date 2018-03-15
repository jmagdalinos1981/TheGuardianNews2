package com.johnmagdalinos.android.newsworld.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.johnmagdalinos.android.newsworld.R;
import com.johnmagdalinos.android.newsworld.model.Section;

import java.util.ArrayList;

/**
 * Created by Gianni on 08/02/2018.
 */

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.DrawerViewholder> {
    /** Member variables */
    private ArrayList<Section> mSections;
    private int mSelectedItem;
    private Context mContext;
    private TextView mTextView;
    private OnClickCallback mCallback;

    public interface OnClickCallback {
        void onItemClick(int position);
    }

    public DrawerAdapter(Context context, OnClickCallback callback, ArrayList<Section> sections, int
            selectedItem) {
        mContext = context;
        mCallback = callback;
        mSections = sections;
        mSelectedItem = selectedItem;
    }

    @Override
    public DrawerViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewRoot = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .list_item_drawer, parent, false);
        return new DrawerViewholder(viewRoot);
    }

    @Override
    public void onBindViewHolder(DrawerViewholder holder, int position) {
        mTextView.setText(mSections.get(position).getTitle());

        // Set the background color of the selected view
        if (position == mSelectedItem) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.drawerHighlight));
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color
                    .transparent));
        }
    }

    @Override
    public int getItemCount() {
        if (mSections == null) {
            return 0;
        } else {
            return mSections.size();
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

    public class DrawerViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public DrawerViewholder(View itemView) {
            super(itemView);

            mTextView = itemView.findViewById(R.id.tv_drawer_list_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mSelectedItem = getAdapterPosition();
            notifyDataSetChanged();
            mCallback.onItemClick(mSelectedItem);
        }
    }

    public void swapList(ArrayList<Section> newList) {
        mSections = newList;
        notifyDataSetChanged();
    }
}
