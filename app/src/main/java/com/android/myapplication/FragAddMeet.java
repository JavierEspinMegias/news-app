package com.android.myapplication;

import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ErrorDialogFragment;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import static android.view.ViewGroup.getChildMeasureSpec;
import static androidx.constraintlayout.widget.Constraints.TAG;


public class FragAddMeet extends Fragment implements OnMapReadyCallback {

    private static final String USER_ID = "user_id";
    private String userId;

    // TODO: Rename and change types of parameters
    private GoogleMap myMap;
    private CasualMapView mMapView;
    private TextInputEditText meetDescription, addressMap;
    private boolean isMapExpanded = false;
    private ImageView expandMap;
    private MaterialButton sendMeet;
    private GregorianCalendar daySelected = new GregorianCalendar(1, 1, 1);
    private LatLng lastSelectedLocation;

    public FragAddMeet() {


    }

    public static FragAddMeet newInstance(String userId) {
        FragAddMeet fragment = new FragAddMeet();
        Bundle args = new Bundle();
        args.putString(USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getString(USER_ID);
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        } else {
            // Permission has already been granted
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_add_meet, container, false);

        final SeekBar seekBarKm = (SeekBar) v.findViewById(R.id.seekBar_distance);
        final TextView textViewKm = (TextView) v.findViewById(R.id.text_distance);

        final SeekBar seekBarHours = (SeekBar) v.findViewById(R.id.seekBar_hours);
        final TextView textViewHours = (TextView) v.findViewById(R.id.num_hours);

        final LinearLayout linearMap = (LinearLayout) v.findViewById(R.id.linear_map);
        final LinearLayout linearHour = (LinearLayout) v.findViewById(R.id.linear_hour);
        final LinearLayout linearCalendar = (LinearLayout) v.findViewById(R.id.linear_calendar);
        final LinearLayout linearDistance = (LinearLayout) v.findViewById(R.id.linear_distance);
        final LinearLayout linearDuration = (LinearLayout) v.findViewById(R.id.linear_duration);

        final TabLayout tabLayout = (TabLayout)v.findViewById(R.id.meet_option_tab);

        expandMap = (ImageView) v.findViewById(R.id.image_expand);
        addressMap = (TextInputEditText) v.findViewById(R.id.map_address);

        final ChipGroup choiceChipGroup = v.findViewById(R.id.chip_group);

        meetDescription = (TextInputEditText) v.findViewById(R.id.tiet_descript);

        final CalendarView calendatMeet = (CalendarView)v.findViewById(R.id.calendar_meet);

        sendMeet = (MaterialButton)v.findViewById(R.id.send_meet);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        tab.getIcon().setTint(getContext().getResources().getColor(R.color.colorPrimary, getContext().getTheme()));
                        linearMap.setVisibility(View.VISIBLE);
                        linearCalendar.setVisibility(View.GONE);
                        linearHour.setVisibility(View.GONE);
                        linearDistance.setVisibility(View.GONE);
                        linearDuration.setVisibility(View.GONE);
                        break;
                    case 1:
                        tab.getIcon().setTint(getContext().getResources().getColor(R.color.colorPrimary, getContext().getTheme()));
                        linearCalendar.setVisibility(View.VISIBLE);
                        linearHour.setVisibility(View.GONE);
                        linearMap.setVisibility(View.GONE);
                        linearDistance.setVisibility(View.GONE);
                        linearDuration.setVisibility(View.GONE);
                        break;
                    case 2:
                        tab.getIcon().setTint(getContext().getResources().getColor(R.color.colorPrimary, getContext().getTheme()));
                        linearHour.setVisibility(View.VISIBLE);
                        linearMap.setVisibility(View.GONE);
                        linearCalendar.setVisibility(View.GONE);
                        linearDistance.setVisibility(View.GONE);
                        linearDuration.setVisibility(View.GONE);
                        break;
                    case 3:
                        tab.getIcon().setTint(getContext().getResources().getColor(R.color.colorPrimary, getContext().getTheme()));
                        linearHour.setVisibility(View.GONE);
                        linearMap.setVisibility(View.GONE);
                        linearCalendar.setVisibility(View.GONE);
                        linearDistance.setVisibility(View.VISIBLE);
                        linearDuration.setVisibility(View.GONE);
                        break;
                    case 4:
                        tab.getIcon().setTint(getContext().getResources().getColor(R.color.colorPrimary, getContext().getTheme()));
                        linearHour.setVisibility(View.GONE);
                        linearMap.setVisibility(View.GONE);
                        linearCalendar.setVisibility(View.GONE);
                        linearDistance.setVisibility(View.GONE);
                        linearDuration.setVisibility(View.VISIBLE);
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        try {
            MapsInitializer.initialize(this.getActivity());
            mMapView = (CasualMapView) v.findViewById(R.id.map);
            mMapView.onCreate(savedInstanceState);
            mMapView.getMapAsync(this);
        } catch (InflateException e) {
            Log.e(TAG, "Inflate exception");
        }


        choiceChipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup chipGroup, int i) {
                Chip chipSelec = v.findViewById(chipGroup.getCheckedChipId());
                if (i == R.id.travel){
                    seekBarKm.setMax(9999);
                }else{
                    seekBarKm.setMax(999);
                }

            }
        });

        seekBarHours.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textViewHours.setText(i+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarKm.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textViewKm.setText(i+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        expandMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (isMapExpanded){
                rotateView(AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_anime_up));
                expandCollapse(mMapView, 1000, 222);
                isMapExpanded = false;
            }else{
                rotateView(AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_anim));
                expandCollapse(mMapView, 1000,800);
                isMapExpanded = true;
            }
            }
        });


        sendMeet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference();
            DatabaseReference newRef = database.getReference();

            if (choiceChipGroup.getCheckedChipId() != -1){
                Chip chosen = v.findViewById(choiceChipGroup.getCheckedChipId());
                String finalDescript = meetDescription.getText().toString();
                if (finalDescript.length()>50){
                    finalDescript = finalDescript.substring(0, 50);
                }

                CasualMeet newMeet = new CasualMeet(
                        "",
                        userId,
                        daySelected.get(Calendar.DAY_OF_MONTH)+"/"+(daySelected.get(Calendar.MONTH)+1)+"/"+daySelected.get(Calendar.YEAR),
                        "",
                        "",
                        chosen.getText()+"",
                        lastSelectedLocation.latitude+"",
                        lastSelectedLocation.longitude+"",
                        "",
                        "",
                        finalDescript,
                        Integer.parseInt(textViewHours.getText().toString()),
                        Integer.parseInt(textViewKm.getText().toString()));

                newRef = reference.child("meets").push();
                String meetId = newRef.getKey();
                newMeet.setId(meetId);
                reference.child("meets").child(meetId).setValue(newMeet);
            }

            }
        });

        calendatMeet.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                daySelected = new GregorianCalendar(year, month, dayOfMonth);
            }
        });

        return v;
    }

    public boolean checkInputs(ChipGroup chipGroup){
        if (chipGroup.getCheckedChipId() != -1){
            if (meetDescription.getText().toString().length()>5){
                if (true){

                    return true;
                }
            }else{
                Toast.makeText(getContext(), R.string.need_desciption, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getContext(), R.string.need_type, Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void expandCollapse(final View v, int duration, int targetHeight) {

        v.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(v.getMeasuredHeight(), targetHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
            v.getLayoutParams().height = (int) animation.getAnimatedValue();
            v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    public void rotateView(Animation an){
        expandMap.setAnimation(an);
        an.setDuration(500);
        an.setRepeatCount(0);
        an.setRepeatMode(Animation.REVERSE);
        an.setFillAfter(true);
        expandMap.setAnimation(an);

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;
        myMap.getUiSettings().setMyLocationButtonEnabled(true);
        myMap.setMyLocationEnabled(true);
        myMap.getUiSettings().setMapToolbarEnabled(true);

        lastSelectedLocation = new LatLng(0f,0f);

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                try {
                    addressMap.setText(getAddressByLatLong(latLng.latitude, latLng.longitude).get(0).getAddressLine(0));

                    myMap.clear();
                    myMap.addMarker(new MarkerOptions()
                            .flat(true)
                            .anchor(0.5f, 1f)
                            .position(latLng));

                    myMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    public List<Address> getAddressByLatLong(Double lati, Double longi) throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        addresses = geocoder.getFromLocation(lati, longi, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        lastSelectedLocation = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
        return addresses;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }
    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }



}