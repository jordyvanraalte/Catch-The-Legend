package com.whisperict.catchthelegend.controllers.managers.apis;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.maps.android.PolyUtil;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class RouteManager {
    private static RouteManager instance;

    private RouteManager(){

    }

    public void getRoute(ArrayList<Location> locations, Context context, OnRouteResponseListener onRouteResponseListener){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, RouteProvider.getUrlRoute(locations), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    ArrayList<com.google.android.gms.maps.model.LatLng> polyLocations = new ArrayList<>();
                    JSONArray routes = response.getJSONArray("routes");
                    JSONObject route = routes.getJSONObject(0);
                    JSONObject overview = route.getJSONObject("overview_polyline");
                    polyLocations.addAll(PolyUtil.decode(overview.getString("points")));
                    onRouteResponseListener.OnRouteResponse(polyLocations);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response","response");
            }
        });
        RouteRequestQueue.getInstance(context).getRequestQueue().add(request);
    }


    public static RouteManager getInstance(){
        if(instance == null){
            instance = new RouteManager();
        }
        return instance;
    }


}
