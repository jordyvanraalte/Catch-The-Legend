package com.whisperict.catchthelegend.managers.apis;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.whisperict.catchthelegend.entities.Legend;
import com.whisperict.catchthelegend.managers.apis.legend.LegendApiRequestQueue;
import com.whisperict.catchthelegend.managers.apis.legend.OnLegendApiResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LegendApiManager {

    private static LegendApiManager instance;
    private static final String MAINURL = "http://54.38.214.36:8081/";

    private LegendApiManager(){

    }

    public void getLegends(Context context, final OnLegendApiResponseListener onLegendApiResponseListener){
        JsonObjectRequest request = new JsonObjectRequest(JsonObjectRequest.Method.GET, MAINURL + "legends", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("VOLLEY_TAG", "RECEIVED LEGENDS");
                HandleLegendsResponse(response, onLegendApiResponseListener);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VOLLEY_TAG", "RECEIVED ERROR AT getLegends() METHOD");
            }
        });
        LegendApiRequestQueue.getInstance(context).getRequestQueue().add(request);
    }

    private void HandleLegendsResponse(JSONObject response, OnLegendApiResponseListener onLegendApiResponseListener) {
        try {
            JSONArray array = response.getJSONArray("Legends");
            ArrayList<String> names = new ArrayList<>();
            for(int i = 0; i < array.length() - 1; i++){
                names.add(array.getString(i));
            }
            onLegendApiResponseListener.OnLegendsReceive(names);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getLegend(Context context, final OnLegendApiResponseListener onLegendApiResponseListener, String legendName){
        JsonObjectRequest request = new JsonObjectRequest(JsonObjectRequest.Method.GET, MAINURL + "legends/" + legendName, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                HandleLegendResponse(response, onLegendApiResponseListener);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VOLLEY_TAG", "RECEIVED ERROR AT getLegend() METHOD");
            }
        });
        LegendApiRequestQueue.getInstance(context).getRequestQueue().add(request);
    }

    private void HandleLegendResponse(JSONObject response, OnLegendApiResponseListener onLegendApiResponseListener) {
        try {
            Legend legend = new Legend(response.getString("name"),response.getString("description"),response.getString("rarity"),response.getString("dropRate"));
            onLegendApiResponseListener.OnLegendReceive(legend);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static LegendApiManager getInstance() {
        if(instance == null){
            instance = new LegendApiManager();
        }
        return instance;
    }
}
