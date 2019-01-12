package com.whisperict.catchthelegend.services;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.maps.model.Marker;
import com.whisperict.catchthelegend.fragments.MapFragment;

import java.util.List;

public class GeofenceTransitionsService extends JobIntentService {

    private static final int JOB_ID = 573;

    private static final String TAG = "GEOFENCE";
    public static final String ACTION = "com.whisperict.catchthelegend.GeofenceTransitionsService";

    /**
     * Convenience method for enqueuing work in to this service.
     */
    public static void enqueueWork(Context context, Intent intent) {
        Log.d(TAG, "enqueueWork");
        enqueueWork(context, GeofenceTransitionsService.class, JOB_ID, intent);
    }

    /**
     * Handles incoming intents.
     *
     * @param intent sent by Location Services. This Intent is provided to Location
     *               Services (inside a PendingIntent) when addGeofences() is called.
     */
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            Log.e(TAG, "GeofencingError: " + geofencingEvent.getErrorCode());
            return;
        }

        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            List<Geofence> triggeredGeofences = geofencingEvent.getTriggeringGeofences();
            for(Geofence geofence : triggeredGeofences){
                Marker marker = MapFragment.markerHashMap.get(geofence.getRequestId());
                if (marker != null){
                    Intent geofenceIntent = new Intent(ACTION);
                    intent.putExtra("ResultCode", -1);
                    intent.putExtra("GeofenceID", geofence.getRequestId());
                    LocalBroadcastManager.getInstance(this).sendBroadcast(geofenceIntent);
                    Log.i(TAG, "Geofence ID transmitted " + geofence.getRequestId());
                }
            }
        }

        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            List<Geofence> triggeredGeofences = geofencingEvent.getTriggeringGeofences();
            for(Geofence geofence : triggeredGeofences){
                Marker marker = MapFragment.markerHashMap.get(geofence.getRequestId());
                if (marker != null){
                    Intent geofenceIntent = new Intent(ACTION);
                    intent.putExtra("ResultCode", 1);
                    intent.putExtra("GeofenceID", geofence.getRequestId());
                    LocalBroadcastManager.getInstance(this).sendBroadcast(geofenceIntent);
                    Log.i(TAG, "Geofence ID transmitted " + geofence.getRequestId());
                }
            }
        }
    }
}
