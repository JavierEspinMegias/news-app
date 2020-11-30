package com.android.myapplication;

import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;


public class FragSimpleMeet extends Fragment {

    private static final String CITY_MEET = "city_meet";
    private static final String NEW_MEET = "new_meet";

    private String cityMeet;
    private ImageView imageGroup;

    private CasualMeet newMeet;
    private MaterialTextView day, city, description, duration, hour, distance, type;


    public FragSimpleMeet() {

    }

    public static FragSimpleMeet newInstance(String cityMeet, CasualMeet newMeet) {
        FragSimpleMeet fragment = new FragSimpleMeet();
        Bundle args = new Bundle();
        args.putString(CITY_MEET, cityMeet);
        args.putSerializable(NEW_MEET, (Serializable)newMeet);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cityMeet = getArguments().getString(CITY_MEET);
            newMeet = (CasualMeet) getArguments().getSerializable(NEW_MEET);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_simple_meet, container, false);
        imageGroup = (ImageView)v.findViewById(R.id.image_view_single_type);
        day = (MaterialTextView)v.findViewById(R.id.text_single_day);
        city = (MaterialTextView)v.findViewById(R.id.text_single_city);
        description = (MaterialTextView)v.findViewById(R.id.text_single_description);
        duration = (MaterialTextView)v.findViewById(R.id.text_single_duration_);
        hour = (MaterialTextView)v.findViewById(R.id.text_single_hour);
        distance = (MaterialTextView)v.findViewById(R.id.text_single_distance);
        type = (MaterialTextView)v.findViewById(R.id.text_single_type);

        switch (newMeet.getRide_type()){
            case "Speed":
                imageGroup.setImageResource(R.drawable.ic_speed);
                imageGroup.setColorFilter(getResources().getColor(R.color.colorSpeed, getContext().getTheme()));
                break;
            case "Casual Ride":
                imageGroup.setImageResource(R.drawable.ic_casual);
                imageGroup.setColorFilter(getResources().getColor(R.color.colorCasual, getContext().getTheme()));
                break;
            case "Travel":
                imageGroup.setImageResource(R.drawable.ic_travel);
                imageGroup.setColorFilter(getResources().getColor(R.color.colorTravel, getContext().getTheme()));
                break;
            case "Enduro":
                imageGroup.setImageResource(R.drawable.ic_enduro);
                imageGroup.setColorFilter(getResources().getColor(R.color.colorEnduro, getContext().getTheme()));
                break;
        }

        day.setText(newMeet.getDay());
        city.setText(cityMeet);
        description.setText(newMeet.getDescription());
        duration.setText(newMeet.getDuration()+"");
        hour.setText(newMeet.getHour_start()+"");
        distance.setText(newMeet.getDistance()+" km");
        type.setText(newMeet.getRide_type());

        return v;
    }
}
