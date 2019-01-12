package com.whisperict.catchthelegend.managers.apis;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public interface OnRouteResponseListener {
    void OnRouteResponse(ArrayList<LatLng> locations);
}
