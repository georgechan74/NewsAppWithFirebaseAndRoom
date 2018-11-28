package com.example.newsapproomorm;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsapproomorm.data.NewsItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder> {

    Context mContext;
    ArrayList<NewsItem> mArticles;
    private static final String TAG = "TheAdapter";

    public NewsRecyclerViewAdapter(Context context, ArrayList<NewsItem> articles) {
        this.mArticles = articles;
        this.mContext = context;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.news_item, parent, shouldAttachToParentImmediately);
        NewsViewHolder viewHolder = new NewsViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView description;
        ImageView img;

        public NewsViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            img = (ImageView) itemView.findViewById(R.id.img);
        }

        void bind(final int position) {
            title.setText(mArticles.get(position).getTitle());
            Log.d(TAG, "Title: " + mArticles.get(position).getTitle());
            description.setText(mArticles.get(position).getPublishedAt());
            description.append(" . ");
            description.append(mArticles.get(position).getDescription());
            Log.d(TAG, "Date: " + mArticles.get(position).getPublishedAt());
            String thumbUrl = mArticles.get(position).getThumbUrl();
            if (thumbUrl != null) {
                Picasso.get()
                        .load(thumbUrl)
                        .into(img);
            }
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String urlString = mArticles.get(getAdapterPosition()).getUrl();
            openWebPage(urlString);
        }

        private void openWebPage(String url) {
            Uri webpage = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            mContext.startActivity(intent);
        }
    }

}
