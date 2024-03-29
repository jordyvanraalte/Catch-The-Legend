package com.whisperict.catchthelegend.views.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.whisperict.catchthelegend.R;
import com.whisperict.catchthelegend.model.entities.Legend;
import com.whisperict.catchthelegend.model.entities.LegendAdapter;
import com.whisperict.catchthelegend.model.entities.Quest;
import com.whisperict.catchthelegend.controllers.managers.SoundManager;
import com.whisperict.catchthelegend.controllers.managers.apis.OnRouteResponseListener;
import com.whisperict.catchthelegend.controllers.managers.game.QuestManager;
import com.whisperict.catchthelegend.controllers.managers.game.QuestStatusListener;
import com.whisperict.catchthelegend.controllers.managers.map.MapManager;
import com.whisperict.catchthelegend.controllers.managers.map.PermissionManager;
import com.whisperict.catchthelegend.controllers.managers.game.GameManager;
import com.whisperict.catchthelegend.controllers.managers.game.GameResponseListener;
import com.whisperict.catchthelegend.controllers.services.GeofenceManager;
import com.whisperict.catchthelegend.controllers.services.GeofenceTransitionsService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MapFragment extends Fragment implements MapManager.OnMapReadyListener, GameResponseListener, GoogleMap.OnMarkerClickListener, OnRouteResponseListener, QuestStatusListener {

    private GoogleMap map;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location lastLocation;
    private GameManager gameManager;
    private QuestManager questManager;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Gson gson;
    private Polyline polyline;
    private BitmapDescriptor icon;

    private static final int REQUEST_PERMISSION_ID = 1;
    private static final String TAG = "mapfragment";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";

    private ArrayList<Legend> legends = new ArrayList<>();
    public static HashMap<String, Marker> markerHashMap = new HashMap<>();

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getContext()));
        gameManager = GameManager.getInstance(getContext(), this);
        gameManager.setGameResponseListener(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = preferences.edit();
        editor.apply();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Legend.class, new LegendAdapter());
        gson = gsonBuilder.create();
        if(preferences.getBoolean("SOUND_BOOL", true)){
            SoundManager.getInstance().getConstantPlayer().start();
        }

    }



    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(Objects.requireNonNull(getContext())).unregisterReceiver(receiver);
        SoundManager.getInstance().getConstantPlayer().pause();
        GeofenceManager.getInstance().removeGeofences();
    }

    @Override
    public void onDestroy() {
        SoundManager.getInstance().getConstantPlayer ().pause();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(GeofenceTransitionsService.ACTION);
        if(preferences.getBoolean("SOUND_BOOL", true)){
            SoundManager.getInstance().getConstantPlayer().start();
        }
        LocalBroadcastManager.getInstance(Objects.requireNonNull(getContext())).registerReceiver(receiver, filter);
        if(PermissionManager.checkAndRequestPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
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

        Log.i(TAG, "TEST");

        if(preferences.getString(LATITUDE, null) != null && preferences.getString(LONGITUDE, null) != null){
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(preferences.getString(LATITUDE, "")), Double.parseDouble(preferences.getString(LONGITUDE, "")) ), 18));
            Log.i(TAG, "moved camera to last location " + preferences.getString(LATITUDE, "") + " " + preferences.getString(LONGITUDE, ""));
        }

        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.getUiSettings().setMapToolbarEnabled(false);
       /* map.getUiSettings().setTiltGesturesEnabled(false);
        map.getUiSettings().setZoomGesturesEnabled(false);
        map.getUiSettings().setScrollGesturesEnabled(false);
        map.getUiSettings().setRotateGesturesEnabled(true);*/
        map.setOnMarkerClickListener(this);

        if(PermissionManager.checkAndRequestPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            map.setMyLocationEnabled(true);
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        }

        if(preferences.getString("LEGENDS", null) != null){
            legends.clear();
            Legend[] saveLegends = gson.fromJson(preferences.getString("LEGENDS", null),Legend[].class);
            respawnLegends(saveLegends);
        }

        questManager = QuestManager.getInstance(getContext());
        if(questManager.getCurrentQuest() != null){
            questManager.getRoute(getContext(), this);
        }

        icon = BitmapDescriptorFactory.fromResource(R.mipmap.questmarkertwee);
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
                questManager.update(location, MapFragment.this, polyline);


                //SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MainActivity.SHARED_PREFS, Context.MODE_PRIVATE);

                editor.putString(LATITUDE, Double.toString(location.getLatitude()));
                editor.putString(LONGITUDE, Double.toString(location.getLongitude()));
                editor.putString("LEGENDS",gson.toJson(legends));

                editor.apply();
            }
        }
    };

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PermissionManager.REQUEST_PERMISSION_ID) {
            if (PermissionManager.checkPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                map.setMyLocationEnabled(true);
                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
            }
        }
    }

    @Override
    public void spawnLegends(ArrayList<Legend> legends) {
        icon = BitmapDescriptorFactory.fromResource(R.mipmap.questmarkertwee);
        for(Legend legend : legends){
            this.legends.add(legend);
            Marker legendMark = map.addMarker(new MarkerOptions().position(new LatLng(legend.getLocation().getLatitude(), legend.getLocation().getLongitude())));
            legendMark.setTag(legend);
            legendMark.setVisible(false);
            legendMark.setIcon(icon);
            markerHashMap.put(legend.getUniqueId(), legendMark);
            //GeofenceManager.getInstance().addGeofenceLegends(legends);
        }
        GeofenceManager.getInstance().addGeofenceLegends(legends);
    }

    public void setGeofence(List<Legend> legends){
        GeofenceManager.getInstance().addGeofenceLegends(legends);
    }

    private void respawnLegends(Legend[] savedLegends){
        icon = BitmapDescriptorFactory.fromResource(R.mipmap.questmarkertwee);
        for(Legend legend : savedLegends){
            legends.add(legend);
            Marker legendMark = map.addMarker(new MarkerOptions().position(new LatLng(legend.getLocation().getLatitude(), legend.getLocation().getLongitude())));
            legendMark.setTag(legend);
            legendMark.setVisible(false);
            legendMark.setIcon(icon);
            markerHashMap.put(legend.getUniqueId(), legendMark);
            //GeofenceManager.getInstance().addGeofenceLegends(legends);
        }
        GeofenceManager.getInstance().addGeofenceLegends(legends);
    }

    @Override
    public void deSpawnLegends() {
        for(Marker marker : markerHashMap.values()) {
            marker.remove();
        }
        markerHashMap.clear();
        GeofenceManager.getInstance().removeGeofences();
        legends.clear();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Legend legend = (Legend) marker.getTag();
        if(legend != null){
            marker.remove();
            legends.remove(legend);
            LegendFragment legendFragment = LegendFragment.newInstance(legend);
            legendFragment.showNow(getFragmentManager(),"LEGEND_FRAGMENT: shown");
            return true;
        }
            return false;
    }

    @Override
    public void onStop() {
        if(questManager != null){
            questManager.setLastLocation(lastLocation);
        }
        super.onStop();
    }

    @Override
    public void OnRouteResponse(ArrayList<LatLng> locations) {
        PolylineOptions polyLineOptions = new PolylineOptions();
        for (int i = 0; i < locations.size(); i++){
            polyLineOptions.add(locations.get(i));
        }
        polyline = map.addPolyline(polyLineOptions);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int resultCode = intent.getIntExtra("ResultCode", Activity.RESULT_CANCELED);
            if (resultCode == -1){
                //icon = BitmapDescriptorFactory.fromResource(R.mipmap.questmarkertwee);
                if(map != null && markerHashMap != null){
                    markerHashMap.get(intent.getStringExtra("GeofenceID")).setVisible(true);
                    //markerHashMap.get(intent.getStringExtra("GeofenceID")).setIcon(icon);
                    Log.i("GEOFENCE", "Marker visible");
                }
            }

            if (resultCode == 1){
                icon = BitmapDescriptorFactory.fromResource(R.mipmap.cardbackground);
                if(map != null && markerHashMap != null){
                    markerHashMap.get(intent.getStringExtra("GeofenceID")).setVisible(false);
                    //markerHashMap.get(intent.getStringExtra("GeofenceID")).setIcon(icon);
                    Log.i("GEOFENCE", "marker invisible");
                }
            }
        }
    };

    @Override
    public void OnQuestFinish(Quest quest) {
        if(polyline != null){
            QuestEndFragment questEndFragment = QuestEndFragment.newInstance(quest);
            questEndFragment.showNow(getFragmentManager(), "QUEST_END_FRAGMENT: SHOWN");
            polyline.remove();
        }
    }
}

