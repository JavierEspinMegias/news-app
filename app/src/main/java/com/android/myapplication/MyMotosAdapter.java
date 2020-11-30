package com.android.myapplication;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyMotosAdapter extends RecyclerView.Adapter<MyMotosAdapter.ViewHolder>{


    private String userId;
    private ArrayList<CasualMoto> motos;


    public MyMotosAdapter(ArrayList<CasualMoto> motos, String userId) {

        this.motos = motos;
        this.userId = userId;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public MaterialTextView type, cv, model, name;
        public LinearLayout item_moto_background, linear_moto_add;
        public ImageView imageMoto;

        public ViewHolder(final View itemView) {
            super(itemView);

            this.type = (MaterialTextView) itemView.findViewById(R.id.text_moto_type_item);
            this.cv = (MaterialTextView) itemView.findViewById(R.id.text_cv_item);
            this.model = (MaterialTextView) itemView.findViewById(R.id.text_model_item);
            this.name = (MaterialTextView) itemView.findViewById(R.id.text_name_moto);
            this.item_moto_background = (LinearLayout)itemView.findViewById(R.id.adapter_linear_layout_item_moto);
            this.linear_moto_add = (LinearLayout)itemView.findViewById(R.id.linear_add_moto);
            this.imageMoto = (ImageView) itemView.findViewById(R.id.image_view_adapter_moto);
        }
    }

    @NonNull
    @Override
    public MyMotosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.adapter_motos, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;

    }


    @Override
    public void onBindViewHolder(final MyMotosAdapter.ViewHolder holder, final int position) {
        final CasualMoto moto = this.motos.get(position);
        if (position != motos.size()-1){
            holder.type.setText(moto.getType());
            holder.cv.setText(moto.getCv());
            holder.model.setText(moto.getModel());
            holder.name.setText(moto.getName());
                if (moto.getType().equals(holder.itemView.getContext().getString(R.string.casual_ride))){
                    holder.imageMoto.setImageResource(R.drawable.ic_casual);
                    holder.imageMoto.setColorFilter(holder.itemView.getResources().getColor(R.color.colorCasual, holder.itemView.getContext().getTheme()));

                }else if(moto.getType().equals(holder.itemView.getContext().getString(R.string.speed_ride))){
                    holder.imageMoto.setImageResource(R.drawable.ic_speed);
                    holder.imageMoto.setColorFilter(holder.itemView.getResources().getColor(R.color.colorSpeed, holder.itemView.getContext().getTheme()));

                }else if(moto.getType().equals(holder.itemView.getContext().getString(R.string.enduro))){
                    holder.imageMoto.setImageResource(R.drawable.ic_enduro);
                    holder.imageMoto.setColorFilter(holder.itemView.getResources().getColor(R.color.colorEnduro, holder.itemView.getContext().getTheme()));

                }else if(moto.getType().equals(holder.itemView.getContext().getString(R.string.travel))){
                    holder.imageMoto.setImageResource(R.drawable.ic_travel);
                    holder.imageMoto.setColorFilter(holder.itemView.getResources().getColor(R.color.colorTravel, holder.itemView.getContext().getTheme()));

                }

            holder.item_moto_background.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }else{
            holder.linear_moto_add.setVisibility(View.GONE);
            holder.item_moto_background.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragNewMoto frag_settings = FragNewMoto.newInstance(userId);
                    loadFragment(frag_settings, holder);
                }
            });
        }

    }


    public void loadFragment(Fragment frag, MyMotosAdapter.ViewHolder holder){
        ((FragmentActivity)holder.itemView.getContext()).getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                .replace(R.id.frame_fragments, frag)
                .commit();
    }


    @Override
    public int getItemCount() {
        return motos.size();
    }
}