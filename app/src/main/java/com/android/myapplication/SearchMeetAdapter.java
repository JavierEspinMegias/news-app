package com.android.myapplication;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.ChangeBounds;
import androidx.transition.ChangeImageTransform;
import androidx.transition.ChangeTransform;
import androidx.transition.Fade;
import androidx.transition.TransitionSet;

import com.google.android.material.textview.MaterialTextView;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchMeetAdapter extends RecyclerView.Adapter<SearchMeetAdapter.ViewHolder>{


    private String userId;
    private ArrayList<CasualMeet> meets;


    public SearchMeetAdapter(ArrayList<CasualMeet> meets, String userId) {

        this.meets = meets;
        this.userId = userId;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public MaterialTextView type, day, description, city;
        public ImageView imageGroup;
        public LinearLayout background;

        public ViewHolder(final View itemView) {
            super(itemView);

            this.type = (MaterialTextView) itemView.findViewById(R.id.text_type_item);
            this.day = (MaterialTextView) itemView.findViewById(R.id.text_day_item);
            this.city = (MaterialTextView) itemView.findViewById(R.id.text_city_item);
            this.description = (MaterialTextView) itemView.findViewById(R.id.text_description_item);
            this.imageGroup = (ImageView)itemView.findViewById(R.id.image_view_adapter_item);
            this.background = (LinearLayout)itemView.findViewById(R.id.adapter_linear_layout_item);

        }
    }

    @NonNull
    @Override
    public SearchMeetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.adapter_meets, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;

    }


    @Override
    public void onBindViewHolder(final SearchMeetAdapter.ViewHolder holder, final int position) {
        final CasualMeet meet = this.meets.get(position);

        if (meet.getRide_type().equals("Enduro")){
            holder.type.setText(R.string.enduro);
            holder.imageGroup.setImageResource(R.drawable.ic_enduro);
            holder.imageGroup.setColorFilter(holder.itemView.getResources().getColor(R.color.colorEnduro, holder.itemView.getContext().getTheme()));
        }else if(meet.getRide_type().equals("Travel")){
            holder.type.setText(R.string.travel);
            holder.imageGroup.setImageResource(R.drawable.ic_travel);
            holder.imageGroup.setColorFilter(holder.itemView.getResources().getColor(R.color.colorTravel, holder.itemView.getContext().getTheme()));
        }else if(meet.getRide_type().equals("Speed")){
            holder.type.setText(R.string.speed_ride);
            holder.imageGroup.setImageResource(R.drawable.ic_speed);
            holder.imageGroup.setColorFilter(holder.itemView.getResources().getColor(R.color.colorSpeed, holder.itemView.getContext().getTheme()));
        }

        holder.day.setText(meet.getDay());
        holder.description.setText(meet.getDescription());
        try {
            holder.city.setText(getAddressByLatLong(Double.parseDouble(
                    meet.getLatitude_start()),
                    Double.parseDouble(meet.getLongitude_start()),
                    holder.itemView.getContext()).get(0).getLocality());

        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragSimpleMeet watchMeet = FragSimpleMeet.newInstance(holder.city.getText().toString(),meet);

                ((FragmentActivity)holder.itemView.getContext())
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_fragments, watchMeet)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }



    public List<Address> getAddressByLatLong(Double lati, Double longi, Context context) throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());

        addresses = geocoder.getFromLocation(lati, longi, 1);
        return addresses;
    }



    @Override
    public int getItemCount() {
        return meets.size();
    }
}