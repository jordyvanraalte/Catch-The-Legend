package com.whisperict.catchthelegend.controllers.managers.map;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewTreeObserver;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MapManager implements ViewTreeObserver.OnGlobalLayoutListener, OnMapReadyCallback {

    private static final String TAG = "Permission";
    private SupportMapFragment mapFragment;
    private OnMapReadyListener listener;

    private GoogleMap googleMap;
    private boolean isMapReady = false;
    private boolean isViewReady = false;

    private MapManager(SupportMapFragment fragment, OnMapReadyListener listener) {
        this.mapFragment = fragment;
        this.listener = listener;
    }

    public static void loadMap(Fragment fragment, @IdRes int id, OnMapReadyListener listener){
        new MapManager((SupportMapFragment)fragment.getChildFragmentManager().findFragmentById(id), listener).registerListeners();
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
