package com.tripaction.nytimessearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tripaction.nytimessearch.R;
import com.tripaction.nytimessearch.activity.DetailActivity;
import com.tripaction.nytimessearch.models.Response;
import com.tripaction.nytimessearch.utils.ImageUtil;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private static final String TAG = RecyclerAdapter.class.getSimpleName();

    private List<Response.Doc> mArticles;
    public static final String ARTICLE_EXTRA = "article_extra";
    private Context mContext;

    public RecyclerAdapter(Context context, List<Response.Doc> articles) {
        mArticles = articles;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: loading position: " + mArticles.get(position).getWebUrl());
        ImageUtil.displayNewsImage(mContext, holder.newsItemImage, mArticles.get(position));
        holder.newsItemTitle.setText(mArticles.get(position).getHeadline().getMain());
        holder.parentLayout.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, DetailActivity.class);
            intent.putExtra(ARTICLE_EXTRA, mArticles.get(position));
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView newsItemImage;
        private TextView newsItemTitle;
        private RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newsItemImage = itemView.findViewById(R.id.news_item_image);
            newsItemTitle = itemView.findViewById(R.id.news_item_title);
            parentLayout = itemView.findViewById(R.id.news_item_container);
        }
    }
}
