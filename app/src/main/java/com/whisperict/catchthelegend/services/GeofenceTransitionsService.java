package com.whisperict.catchthelegend.services;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

public class GeofenceTransitionsService extends JobIntentService {

    private static final int JOB_ID = 573;

    private static final String TAG = GeofenceTransitionsService.class.getName();

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

            // Get the geofences that were triggered. A single event can trigger multiple geofences.
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

            // Send notification and log the transition details.
            //sendNotification(geofenceTransitionDetails);
            Log.i("GEOFENCE", "Geofence has been triggered!");
        }
    }
}
