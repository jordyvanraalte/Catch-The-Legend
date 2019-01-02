package com.whisperict.catchthelegend.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.whisperict.catchthelegend.R;
import com.whisperict.catchthelegend.activities.CatchActivity;
import com.whisperict.catchthelegend.activities.DetailedLegendActivity;
import com.whisperict.catchthelegend.entities.Legend;
import com.whisperict.catchthelegend.managers.map.MapManager;
import com.whisperict.catchthelegend.managers.map.PermissionManager;
import com.whisperict.catchthelegend.managers.game.GameManager;
import com.whisperict.catchthelegend.managers.game.GameResponseListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MapFragment extends Fragment implements MapManager.OnMapReadyListener, GameResponseListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap map;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location lastLocation;
    private GameManager gameManager;

    private static final int REQUEST_PERMISSION_ID = 1;
    private static final String TAG = "map fragment";

    private ArrayList<Legend> legends = new ArrayList<>();

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getContext()));
        gameManager = GameManager.getInstance(getContext(), this);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (fusedLocationProviderClient != null) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert getFragmentManager() != null;
        MapManager.loadMap(this, R.id.map, this);
    }

    @SuppressLint("ObsoleteSdkInt")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.getUiSettings().setMapToolbarEnabled(false);
        map.setOnMarkerClickListener(this);

        if(PermissionManager.checkAndRequestPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            map.setMyLocationEnabled(true);
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }

    private LocationCallback locationCallback = new LocationCallback(){
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                Location location = locationList.get(locationList.size() - 1);
                Log.i(TAG, "Locations: " + location.getLatitude() + " " + location.getLongitude());
                lastLocation = location;
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                CameraUpdate cameraLocation = CameraUpdateFactory.newLatLngZoom(latLng, 18);
                map.animateCamera(cameraLocation);

                gameManager.update(location);
            }
        }
    };

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PermissionManager.REQUEST_PERMISSION_ID) {
            if (PermissionManager.checkPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                map.setMyLocationEnabled(true);
            }
        }
    }

    @Override
    public void spawnLegend(Legend legend) {
        legends.add(legend);
        Marker legendMark = map.addMarker(new MarkerOptions()
                .position(new LatLng(legend.getLocation().getLatitude(), legend.getLocation().getLongitude())));
        legendMark.setTag(legend);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Legend legend = (Legend) marker.getTag();
        if(legend != null){
            Intent intent = new Intent(getContext(), CatchActivity.class);
            intent.putExtra("LEGEND", legend);
            startActivity(intent);
        }
        return false;
    }
}

