package com.android.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{

    private ArrayList<CasualRace> news;


    public NewsAdapter(ArrayList<CasualRace> news) {
        this.news = news;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public MaterialTextView league, circuit, date, country, city;
        public ImageView image;
        public ViewHolder(final View itemView) {
            super(itemView);

            this.league = (MaterialTextView) itemView.findViewById(R.id.news_league);
            this.circuit = (MaterialTextView) itemView.findViewById(R.id.news_circuit);
            this.date = (MaterialTextView) itemView.findViewById(R.id.news_date);
            this.country = (MaterialTextView) itemView.findViewById(R.id.news_country);
            this.city = (MaterialTextView) itemView.findViewById(R.id.news_city);
            this.image = (ImageView)itemView.findViewById(R.id.news_image_league);
        }
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.adapter_news, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;

    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final CasualRace casualNew = this.news.get(position);

        holder.circuit.setText(casualNew.getCircuit());
        holder.league.setText(casualNew.getLeague());
        holder.country.setText(casualNew.getCountry());
        holder.city.setText(casualNew.getCity());
        holder.date.setText(casualNew.getDate());
        switch (casualNew.getLeague()){
            case "MotoGP":
                holder.image.setImageResource(R.mipmap.ic_motogp_c);
                break;
            case "Moto2":
                holder.image.setImageResource(R.mipmap.ic_moto2_c);
                break;
            case "Moto3":
                holder.image.setImageResource(R.mipmap.ic_moto3_c);
                break;
            case "MotoE World Cup":
                holder.image.setImageResource(R.mipmap.ic_motoe_c);
                break;
            case "SBK":
                holder.image.setImageResource(R.mipmap.ic_sbk_c);
                break;
            case "MXGP":
                holder.image.setImageResource(R.mipmap.ic_mxgp_c);
                break;
            case "MXGP European league":
                holder.image.setImageResource(R.mipmap.ic_mxgp_c);
                break;
            case "Pro Motocross":
                holder.image.setImageResource(R.mipmap.ic_pro_c);
                break;
            case "AMA Supercross":
                holder.image.setImageResource(R.mipmap.ic_ama_c);
                break;
            case "Isle of Man TT":
                holder.image.setImageResource(R.mipmap.ic_isle_c);
                break;
        }

    }


    @Override
    public int getItemCount() {
        return news.size();
    }
}