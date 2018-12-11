package com.whisperict.catchthelegend.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.whisperict.catchthelegend.R;
import com.whisperict.catchthelegend.managers.MapManager;

public class MapFragment extends SupportMapFragment implements MapManager.OnMapReadyListener {

    private GoogleMap map;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new MapManager(this, this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        LatLngBounds breda = new LatLngBounds.Builder()
                .include(new LatLng(51.615751, 4.722507))
                .include(new LatLng(51.606789, 4.807834))
                .include(new LatLng(51.589740, 4.823363))
                .include(new LatLng(51.521217, 4.771453))
                .include(new LatLng(51.521168, 4.746086))
                .include(new LatLng(51.574364, 4.691582))
                .build();

        map.setMinZoomPreference(14);
        map.setMaxZoomPreference(20);

        map.setBuildingsEnabled(false);
        map.setIndoorEnabled(false);

        map.setLatLngBoundsForCameraTarget(breda);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(breda.getCenter(), 16));
    }
}
