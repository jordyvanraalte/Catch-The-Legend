package com.whisperict.catchthelegend.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.whisperict.catchthelegend.R;
import com.whisperict.catchthelegend.activities.MainActivity;
import com.whisperict.catchthelegend.managers.MapManager;

public class MapFragment extends SupportMapFragment implements MapManager.OnMapReadyListener {

    private GoogleMap map;
    private static final int REQUEST_PERMISSION_ID = 1;
    private android.location.Location location;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new MapManager(this, this, getActivity());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        if (!checkPermission(Manifest.permission.ACCESS_FINE_LOCATION))
            requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_PERMISSION_ID);
        else
            map.setMyLocationEnabled(true);

        map.setBuildingsEnabled(false);
        map.setIndoorEnabled(false);
    }

    private boolean checkPermission(String permission) {
        if (ActivityCompat.checkSelfPermission(getContext(), permission)
                == PackageManager.PERMISSION_GRANTED)
            return true;
        return false;
    }

    private void requestPermission(String permission, int code) {
        requestPermissions(new String[]{permission}, code);
    }
    
}
