package com.whisperict.catchthelegend.controllers.managers.apis;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public interface OnRouteResponseListener {
    void OnRouteResponse(ArrayList<LatLng> locations);
}
