package com.whisperict.catchthelegend.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.whisperict.catchthelegend.R;
import com.whisperict.catchthelegend.managers.MapManager;

import java.util.Objects;

public class MapFragment extends Fragment implements MapManager.OnMapReadyListener {

    private GoogleMap map;
    private static final int REQUEST_PERMISSION_ID = 1;
    private static final String TAG = "map fragment";

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        if (!checkPermission())
            requestPermission();
        else
            map.setMyLocationEnabled(true);

    }

    private boolean checkPermission() {
        return ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MapFragment.REQUEST_PERMISSION_ID);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_ID) {
            if (grantResults.length <= 0) {
                Log.i(TAG, "User interaction was cancelled, empty arrays ");
            } else if (permissions.length == 1 &&
                    permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "Permission granted. ");
                map.setMyLocationEnabled(true);
            } else {
                //TODO Maybe add some sort of message to the user that the permission has been denied, but it need to be activated for the app to function.
                Log.i(TAG, "...");
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

