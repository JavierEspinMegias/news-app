package com.android.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;

public class CasualMapView extends MapView {
    public CasualMapView(Context context) {
        super(context);
    }

    public CasualMapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CasualMapView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public CasualMapView(Context context, GoogleMapOptions googleMapOptions) {
        super(context, googleMapOptions);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        /**
         * Request all parents to relinquish the touch events
         */
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}
