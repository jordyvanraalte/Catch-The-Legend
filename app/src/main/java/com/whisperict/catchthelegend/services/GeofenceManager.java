package com.whisperict.catchthelegend.services;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.whisperict.catchthelegend.activities.MainActivity;
import com.whisperict.catchthelegend.entities.Legend;
import com.whisperict.catchthelegend.managers.map.PermissionManager;

import java.util.ArrayList;
import java.util.List;

public class GeofenceManager {

    private static GeofenceManager instance;
    private static final String TAG = GeofenceManager.class.getName();

    public static GeofenceManager getInstance() {
        if (instance == null)
            instance = new GeofenceManager();

        return instance;
    }

    private Context context;
    private GeofencingClient geofencingClient;
    private PendingIntent pendingIntent;

    private GeofenceManager() {
        geofencingClient = LocationServices.getGeofencingClient(MobileApplication.getInstance().getApplicationContext());
        context = MobileApplication.getInstance().getApplicationContext();
    }

    private PendingIntent getGeofencePendingIntent() {
        if(pendingIntent != null) {
            return pendingIntent;
        }

        Intent intent = new Intent(context, GeofenceBroadcastReceiver.class);

        return pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private GeofencingRequest getGeofencingRequest(List<Geofence> geofences) {
        return new GeofencingRequest.Builder()
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .addGeofences(geofences)
                .build();
    }

    @SuppressWarnings("MissingPermission")
    public void addGeofenceLegends(List<Legend> legends) {
        Log.d(TAG, "addGeofenceWayPoints");

        if(legends.size() == 0) return;

        if (!PermissionManager.checkPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(context, "Insufficient Permissions", Toast.LENGTH_LONG).show();
            return;
        }

        removeGeofences();

        List<Geofence> geofences = new ArrayList<>();

        for(Legend legend : legends) {
            geofences.add(new Geofence.Builder()
                    .setRequestId(Integer.toString(legend.getId()))
                    .setCircularRegion(legend.getLocation().getLatitude(), legend.getLocation().getLongitude(), 100)
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                    .build());
            Log.i("GEOFENCE", "geofence for legend has been made");
        }

        geofencingClient.addGeofences(getGeofencingRequest(geofences), getGeofencePendingIntent());
    }

    public void removeGeofences() {
        Log.d(TAG, "removeGeofences");
        if(pendingIntent != null) {
            geofencingClient.removeGeofences(pendingIntent);
        }
    }


}
