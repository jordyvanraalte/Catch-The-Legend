package com.whisperict.catchthelegend.managers;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MapManager implements ViewTreeObserver.OnGlobalLayoutListener, OnMapReadyCallback {

    private static final String TAG = "Permission";
    private SupportMapFragment mapFragment;
    private OnMapReadyListener listener;
    private Context context;

    private GoogleMap googleMap;
    private boolean isMapReady = false;
    private boolean isViewReady = false;

    public MapManager(SupportMapFragment fragment, OnMapReadyListener listener, Context context) {
        this.mapFragment = fragment;
        this.listener = listener;
        this.context = context;
        registerListeners();
    }

    private void registerListeners() {
        // View layout.
        View mapView = mapFragment.getView();

        if ((mapView.getWidth() != 0) && (mapView.getHeight() != 0)) {
            isViewReady = true;
        } else {
            mapView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        }

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onGlobalLayout() {
        mapFragment.getView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
        isViewReady = true;
        fireCallbackIfReady();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        isMapReady = true;
        fireCallbackIfReady();
    }

    private void fireCallbackIfReady() {
        if (isViewReady && isMapReady) {
            listener.onMapReady(googleMap);
        }
    }

    public interface OnMapReadyListener {
        void onMapReady(GoogleMap map);
    }
}
