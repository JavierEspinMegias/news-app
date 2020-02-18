package com.android.myapplication;

import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.view.ViewGroup.getChildMeasureSpec;
import static androidx.constraintlayout.widget.Constraints.TAG;


public class FragAddMeet extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private GoogleMap myMap;
    private MapView mMapView;
    private Marker mPositionMarker;
    private OnFragmentInterfaceCom mListener;
    private EditText addressMap;
    private boolean isMapExpanded = false;
    private ImageView expandMap;


    public FragAddMeet() {
        // Required empty public constructor
    }

    public static FragAddMeet newInstance(String param1) {
        FragAddMeet fragment = new FragAddMeet();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
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
        expandMap = (ImageView) v.findViewById(R.id.image_expand);
        final LinearLayout linearMap = (LinearLayout) v.findViewById(R.id.linear_map);
        addressMap = (EditText) v.findViewById(R.id.address_map);


        final ChipGroup choiceChipGroup = v.findViewById(R.id.chip_group);
        choiceChipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup chipGroup, int i) {
                if (i == R.id.travel){
                    seekBarKm.setMax(9999);
                }else{
                    seekBarKm.setMax(999);
                }
            }
        });

        seekBarKm.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textViewKm.setText(i + " km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        try {
            MapsInitializer.initialize(this.getActivity());
            mMapView = (MapView) v.findViewById(R.id.map);
            mMapView.onCreate(savedInstanceState);
            mMapView.getMapAsync(this);
        } catch (InflateException e) {
            Log.e(TAG, "Inflate exception");
        }

        expandMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isMapExpanded){
                    rotateView(AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_anime_up));
                    expandCollapse(mMapView, 1000, 300);
                    isMapExpanded = false;
                }else{
                    rotateView(AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_anim));
                    expandCollapse(mMapView, 1000, linearMap.getMeasuredHeight());
                    isMapExpanded = true;
                }
            }
        });

        return v;
    }

    public void expandCollapse(final View v, int duration, int targetHeight) {
        int prevHeight  = v.getHeight();

        v.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight-120);
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


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String info, Object data) {
        if (mListener != null) {
            mListener.onFragmentMessage(info, data);
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInterfaceCom) {
            mListener = (OnFragmentInterfaceCom) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }





    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;
        myMap.getUiSettings().setMyLocationButtonEnabled(true);
        myMap.setMyLocationEnabled(true);
        myMap.getUiSettings().setMapToolbarEnabled(true);
        myMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
            try {
                addressMap.setText(getAddressByLatLong(latLng.latitude, latLng.longitude).get(0).getAddressLine(0));
                onLocationChanged(latLng);
            } catch (IOException e) {
                e.printStackTrace();
            }
            }
        });

    }

    public void onLocationChanged(LatLng latLng) {


        if (mPositionMarker == null) {
            mPositionMarker = myMap.addMarker(new MarkerOptions()
                    .flat(true)
                    .anchor(0.5f, 0.5f)
                    .position(new LatLng(latLng.latitude, latLng.latitude)));
        }

        myMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

    }

    public List<Address> getAddressByLatLong(Double lati, Double longi) throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        addresses = geocoder.getFromLocation(lati, longi, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        return addresses;


    }





    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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