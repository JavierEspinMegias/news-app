package com.android.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class CardNewsAdapter extends RecyclerView.Adapter<CardNewsAdapter.ViewHolder>{

    private ArrayList<CasualNew> news;

    public CardNewsAdapter(ArrayList<CasualNew> news) {
        this.news = news;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title, description;
        private ImageView imageNew;

        public ViewHolder(final View itemView) {
            super(itemView);
            this.title = (TextView)itemView.findViewById(R.id.card_title);
            this.description = (TextView)itemView.findViewById(R.id.card_subtitle);
            this.imageNew = (ImageView)itemView.findViewById(R.id.imageView_news);
        }
    }

    @NonNull
    @Override
    public CardNewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.custom_card_news, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;

    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final CasualNew casualNew = this.news.get(position);
        holder.title.setText(casualNew.getTitle());
        holder.description.setText(casualNew.getInformation());
        Glide.with(holder.itemView.getContext())
                .load(casualNew.getPhoto())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.1f)
                .into(holder.imageNew);

    }


    @Override
    public int getItemCount() {
        return news.size();
    }
}